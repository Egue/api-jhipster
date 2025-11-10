from fastapi import APIRouter
from src.services.httpClient.send_info import send_info_to_remote
agente_router = APIRouter()

@agente_router.get("/inventory")
def inventory():
    return send_info_to_remote()