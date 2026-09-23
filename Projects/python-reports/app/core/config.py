import os
from functools import lru_cache

from dotenv import load_dotenv

load_dotenv()

class Settings:
    def __init__(self):
        
        self.DB_CONTROLMAS : str = os.getenv("DB_CONTROLMAS" , "")
        self.DB_API_FACTURACION : str = os.getenv("DB_API_FACTURACION" , "")
        self.API_TOKEN : str = os.getenv("API_TOKEN" , "")
        self.APP_NAME : str = "Reportes INConnection"
        self.ENV: str = os.getenv("ENV" , "development")
        self.CORS = [
            origin.strip()
            for origin in os.getenv("CORS" , "").split(",")
            if origin.strip()
]



@lru_cache
def get_settings() -> Settings:
    return Settings()


