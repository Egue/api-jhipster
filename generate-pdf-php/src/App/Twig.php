<?php

declare(strict_types=1);

use Slim\Views\Twig;
use Slim\Views\TwigMiddleware;


$container['view'] = function () {
    return Twig::create(__DIR__ . '/../Templates', ['cache' => false]);
};

$app->add(TwigMiddleware::createFromContainer($app, 'view'));

?>