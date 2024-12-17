<?php

declare(strict_types=1); 

use Predis\Client; 

$container['redis'] = function(){
    return new Client([
        'scheme' => 'tcp',
        'host' => $_SERVER['REDIS_HOST'],
        'port' => $_SERVER['REDIS_PORT'] 
    ]);
};
?>