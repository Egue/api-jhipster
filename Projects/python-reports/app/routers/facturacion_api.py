from fastapi import APIRouter, Depends, Query , HTTPException
from datetime import date
from app.service import www_serv_documentos_service

from app.core import security

router = APIRouter(prefix="/py/v1" , tags=["Facturacion"])

@router.get("/documentos/between" ,dependencies=[Depends(security.verify_token)])
def documentos(
    inicio : date = Query(...),
    fin : date  = Query(...),
    type : int = Query(...)
):
    df = www_serv_documentos_service.get_documentos_service_betwenn(inicio , fin , type)
    return df.to_dict(orient="records")

@router.get("/documentos/error", dependencies=[Depends(security.verify_token)])
def documents_error(
    inicio : date = Query(...),
    fin : date = Query(...)
):
    df = www_serv_documentos_service.get_documentos_error(inicio , fin)
    return df.to_dict(orient="records")