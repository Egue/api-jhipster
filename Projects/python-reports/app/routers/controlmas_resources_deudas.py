from fastapi import APIRouter, Depends, Query , HTTPException
from datetime import date

from app.service import controlmas_service_deudas  
from app.core.security import verify_token

router = APIRouter(prefix="/py/v1" , tags=["Controlmas"])
"""
, 
"""
@router.get("/controlmas/deudas",dependencies=[Depends(verify_token)] )
def deudas(
    inicio : date = Query(...),
    fin  : date = Query(...)
):
    df = controlmas_service_deudas.get_deudas_by_fecha(inicio , fin)
    return df.to_dict(orient="records")