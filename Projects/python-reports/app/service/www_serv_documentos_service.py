import pandas as pd
from datetime import date

from app.repository import www_serv_repository_documentos

def get_documentos_service_betwenn(inicio : date , fin :date , type : int)->pd.DataFrame:
    return www_serv_repository_documentos.get_documentos_by_fecha(inicio, fin , type)

def get_documentos_error(inicio : date , fin : date)->pd.DataFrame:
    return www_serv_repository_documentos.get_documentos_between_error(inicio , fin)