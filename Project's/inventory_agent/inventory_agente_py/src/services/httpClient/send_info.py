from src.services.agente.agente_services import get_system_info
import requests

def send_info_to_remote():
    payload = get_system_info()
    
    try:
        response = requests.post("http://131.221.41.20:8061/api/kt/inventory" , json=payload , timeout=5)
        response.raise_for_status()
        return {"status" : "success" , "response": response.text}
    except requests.RequestException as e:
        return {"status" : "error" , "message":str(e)}