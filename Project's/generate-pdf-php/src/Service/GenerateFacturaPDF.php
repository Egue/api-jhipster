<?php

declare(strict_types=1);

namespace App\Service;

use App\Service\InConnectionService;
use App\Service\ApiServerCableTv;
use Slim\Views\Twig;
use Dompdf\Dompdf;
use Dompdf\Options;
use Predis\Client as RedisClient;

final class GenerateFacturaPDF{

    private InConnectionService $inconnectionService;

    private ApiServerCableTv $apiServerCableTv;

    private Twig $twig;

    private RedisClient $redis;

    public function __construct(Twig $twig , RedisClient $redis)
    {
        $this->inconnectionService = new InConnectionService;

        $this->twig = $twig;

        $this->redis = $redis;

        $this->apiServerCableTv = new ApiServerCableTv;
    }

    public function send_post_info_fact($data ,  $token)
    {
        $infoFactura = $this->inconnectionService->post($data , 'facturas/info' , $token);
        
        if(!empty($infoFactura["factura"]["serializable"]))
        {
            try {
                $infoFactura["factura"]["serializable"] = unserialize($infoFactura["factura"]["serializable"]);
            } catch (\Exception $e) {
                 
                $infoFactura["factura"]["serializable"] = null; 
                 
            }
        }

        return $infoFactura;
    }

    public function getDeudas($input , $token)
    {
        $data = json_decode((string) json_encode($input)  ,true);
        
        $keyCache = md5($data["factura"].$data["idCliente"]);

        if($cacheData = $this->get_cache_redis($keyCache))
        {
            return unserialize($cacheData);
        }

        $infoFactura = $this->send_post_info_fact($data , $token);

        $this->save_cache($infoFactura , $keyCache);

        return $infoFactura;
    }

    private function save_cache($data , $key)
    {
        $this->redis->setex($key , 3000 , serialize($data));
    }

    private function get_cache_redis($key)
    {
        if($cacheData = $this->redis->get($key))
        {
            return $cacheData;
        }
        
    }

    public function getGeneratePdf($input , $token):String
    {
       try {
        $data = $this->getDeudas($input , $token);
        $data["giros"] =  $this->converterImageToBase64(__DIR__.'/../Templates/giros.png');
        $data["pse"] = $this->converterImageToBase64(__DIR__.'/../Templates/pse-logo.png'); 
        $data["b"] = $this->converterImageToBase64(__DIR__.'/../Templates/QR.png');
        $data["fondo_a"] = $this->converterImageToBase64(__DIR__.'/../Templates/fondoA.jpg');
        $data["fondo_b"] = $this->converterImageToBase64(__DIR__.'/../Templates/fondoB.jpg');
        $html = $this->twig->fetch('hello.html' , $data);

        $options = new Options();
        $options->set('isHtml5ParserEnabled', true);
        $options->set('isRemoteEnabled' , true);
        $dompdf = new Dompdf($options);
        $dompdf->loadHtml($html);
        $dompdf->setPaper('letter' , 'portrait');
        $dompdf->render();

        return $dompdf->output();

       } catch (\Exception $th) {
        //throw $th;
          throw new \Exception($th->getMessage() , 500);
        } 
       
 
    }

    private function converterImageToBase64(string $imagePath):string{
        $imageData = file_get_contents($imagePath);

        $base64 = base64_encode($imageData);

        $mimeType = mime_content_type($imagePath);

        return "data:$mimeType;base64,$base64";
    }

    public function get_xml($input , $token)
    {
        $data = json_decode((string) json_encode($input)  ,true);
        
        $info = $this->getDeudas($data , $token);

        if(!empty($info["factura"]["serializable"]))
        {
            $url = $info["factura"]["serializable"]["url_xml_AttachedDocument"];

            $filename =  $this->apiServerCableTv->downloadXML($url);

            $filePath = __DIR__.'/../Downloads/'.$filename.'';

            if(file_exists($filePath))
            {
                return fopen($filePath, 'rb');
                
            }else{
                throw new \Exception("No se descargó correctamente" , 404);
            }

            
        }else{
            throw new \Exception("Serializable null" , 400);
        }

    }

}

?>