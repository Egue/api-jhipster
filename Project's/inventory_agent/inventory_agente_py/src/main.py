from fastapi import FastAPI
from src.routes.app_routes import app_routes
import uvicorn

app = FastAPI()

for route in app_routes:
    app.include_router(route , prefix="/api")
    
def start():
    uvicorn.run("src.main:app" , host="0.0.0.0" , port=8089, reload=True)