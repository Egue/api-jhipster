
from fastapi.security import APIKeyHeader
from app.core.config import Settings
from fastapi import Security , HTTPException , status 
from app.core.config  import get_settings

settings = get_settings()
  

x_token_header = APIKeyHeader(name="x-token" , auto_error=False)

def verify_token(x_token : str = Security(x_token_header)):
    if x_token is None or x_token != settings.API_TOKEN:
        raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED,
                            detail="Token invalido o ausente en l"
                            )
    return x_token
