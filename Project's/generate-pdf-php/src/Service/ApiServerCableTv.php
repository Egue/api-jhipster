<?php

declare(strict_types=1);

namespace App\Service;

use GuzzleHttp\Client;
use GuzzleHttp\Exception\ClientException;
use GuzzleHttp\Exception\RequestException;

final class ApiServerCableTv{

    private Client $client;

    private array $keys;

    public function __construct()
    {
        $this->client = new Client();
    }

    public function downloadXML($url)
    {
        try{
            $filename = basename($url);

            $res = $this->client->get($url , ['sink' => __DIR__.'/../Downloads/'.$filename.'']);

            return $filename;

        }catch(\Exception $e)
        {
            throw new \Exception($e->getMessage() , 500);
        }
    }

}


?>