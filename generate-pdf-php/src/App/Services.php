<?php

declare(strict_types=1);

use Slim\Views\Twig;
use Pimple\Container;
use Predis\Client;
use App\Service\GenerateFacturaPDF;

$container['generateFacturaPDF'] = static function (Container $container): GenerateFacturaPDF {
    return new GenerateFacturaPDF($container['view'] , $container['redis']); 
};