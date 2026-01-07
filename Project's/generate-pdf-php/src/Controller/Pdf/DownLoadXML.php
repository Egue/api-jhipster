<?php

declare(strict_types=1);

namespace App\Controller\Pdf;

use App\CustomResponse as Response;
use Pimple\Psr11\Container;
use Psr\Http\Message\ServerRequestInterface as Request;

final class DownLoadXML extends Base{

    public function __invoke(Request $request , Response $response): Response{

        try {
            $input =  $request->getParsedBody();
            $token = $request->getHeader('Authorization');
            $stream = $this->getPdfService()->get_xml($input , $token);
            $response = $response->withHeader('Content-Type', 'application/xml')
                                 ->withHeader('Content-Disposition', 'attachment; filename="xml.xml"')
                                 ->withBody(new \Slim\Psr7\Stream($stream));
            return $response;
        } catch (\Exception $e) {
            //throw $th;
            throw new \Exception($e->getMessage() , 500);
        }

    }

}

?>