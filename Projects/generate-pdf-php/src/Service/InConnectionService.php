<?php

declare(strict_types=1);

namespace App\Service;

use GuzzleHttp\Client;
use GuzzleHttp\Exception\ClientException;
use GuzzleHttp\Exception\RequestException;

final class InConnectionService{

    private Client $client;

    private array $keys;

    public function __construct()
    {
        $this->keys = array('url' => $_SERVER['URL']);
        $this->init();
    }

    public function init():void{
        $this->client = new Client([
            'base_uri' => $this->getUrlBase(),
            'timeout' => 3.0
        ]);
    }

    public function getUrlBase():string{
        return $this->keys['url'];
    }

    public function post($data , $url , $token)
    {
        try {
            $body = $this->getBody($data , $token);
            $request = $this->client->post($url , $body);
            
            $datas = $request->getBody()->getContents();
            $response = json_decode($datas , true);
            return $response;
        }catch(\GuzzleHttp\Exception\RequestException $e){

            if($e->hasResponse())
            {
                if($e->getResponse()->getStatusCode() == 401)
                {
                    throw new \Exception("Unauthorization" , 401);
                }
                
            }else{
                throw new \Exception($e->getMessage() , 500);
            } 
            
        
        }catch(\GuzzleHttp\Exception\ClientException $e){

            throw new \Exception($e->getMessage() , 500);
        } 
        catch (\Exception $e) {
            //throw $th;
            
            throw new \Exception($e->getMessage() , 500);
        }
    }

    public function getBody($data, $token){
        
        return array_merge([
            'body' => json_encode($data)
            ],
            $this->getHeader($token)
        );
    }

    public function getHeader($token)
    {
        return ['headers' => [
            'Content-Type' => 'application/json',
            'Authorization' => $token
        ]];
    }
}

?>