from fastapi import FastAPI

from app.routers import facturacion_api
from app.routers import controlmas_resources_deudas
from app.core.config import get_settings
from fastapi.middleware.cors import CORSMiddleware

settings = get_settings() 
app = FastAPI(title=settings.APP_NAME,
              description="Reportes Py",
              version = "v1.0.0")
app.add_middleware(
    CORSMiddleware,
    allow_origins= settings.CORS,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)
app.include_router(facturacion_api.router)
app.include_router(controlmas_resources_deudas.router)