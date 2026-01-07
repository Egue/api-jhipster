<?php
declare(strict_types=1);

namespace App\Controller\Pdf;


//use App\CustomResponse as Response;
use Fig\Http\Message\StatusCodeInterface;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;

final class GenerateFacturaPDFController extends Base{

    public function __invoke(Request $request , Response $response): Response{
        
        try {
            $input =  $request->getParsedBody();
            $token = $request->getHeader('Authorization');
            // Generar el PDF utilizando el servicio
            $pdfContent = $this->getPdfService()->getGeneratePdf($input , $token);
    
            // Preparar la respuesta HTTP con el PDF
            $response = $response
                ->withHeader('Content-Type', 'application/pdf')
                ->withHeader('Content-Disposition', 'inline; filename="factura.pdf"'); // Inline para mostrarlo en el navegador
            $response->getBody()->write($pdfContent);
    
            return $response;
        } catch (\Exception $e) {
            // En caso de error, retorna un código HTTP 500 con un mensaje descriptivo
            $response->getBody()->write(json_encode(['error' => $e->getMessage()]));
            return $response
                ->withHeader('Content-Type', 'application/json')
                ->withStatus(500);
        }
    }
}


?>