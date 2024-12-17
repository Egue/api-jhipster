<?php

declare(strict_types=1);

$app->get('/', 'App\Controller\Home:getHelp');
$app->get('/status', 'App\Controller\Home:getStatus');

$app->post('/factura/pdf' , App\Controller\Pdf\GenerateFacturaPDFController::class);