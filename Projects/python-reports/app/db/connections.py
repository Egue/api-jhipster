from functools import lru_cache
from sqlalchemy import create_engine
from sqlalchemy.engine import Engine

from app.core.config import get_settings

@lru_cache
def get_engines() -> dict[str , Engine]:
    settings = get_settings()
    engines = {}

    if settings.DB_API_FACTURACION:
        engines["facturacion"] = create_engine(settings.DB_API_FACTURACION , pool_pre_ping=True)
    if settings.DB_CONTROLMAS:
        engines["controlmas"] = create_engine(settings.DB_CONTROLMAS , pool_pre_ping=True)

    return engines

def get_engine(nombre: str)-> Engine:
    engines = get_engines()
    if nombre not in engines:
        raise ValueError(f"no hay engine configurado para '{nombre}'. Revisa las variables de entorno.")

    return engines[nombre]
