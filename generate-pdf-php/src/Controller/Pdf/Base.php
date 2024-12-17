<?php

declare(strict_types=1);

namespace App\Controller\Pdf;

use App\Service\GenerateFacturaPDF;
use Pimple\Psr11\Container;

abstract class Base{
    protected Container $container;

    public function __construct(Container $container){

        $this->container = $container;
    }

    protected function getPdfService()
    {
        return $this->container->get('generateFacturaPDF');
    }
}

?>