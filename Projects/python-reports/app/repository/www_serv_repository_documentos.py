from datetime import date, timedelta
import pandas as pd
from app.db.connections import get_engine

def get_documentos_by_fecha(fecha_inicio : date , fecha_fin:date , type: int)->pd.DataFrame:
    query = """
        SELECT * FROM documentos WHERE marca >= %(inicio)s  AND marca < %(fin)s AND tipo = %(type)s
    """
    df_documents = pd.read_sql(
        query , 
        get_engine("facturacion"),
        params={"inicio": fecha_inicio, "fin": fecha_fin + timedelta(days=1) , "type" : type}
    )

    return df_documents

def get_documentos_between_error(inicio : date , fin: date)-> pd.DataFrame:
    query = """
    SELECT d.id_documento , d.numero_documento , d.solicitud , d.marca , d.tipo , e.razon_social  , e.res_fecha_i  , e.res_fecha_f , e.res_prefijo FROM documentos d
    INNER JOIN empresas e on e.id_empresa = d.id_empresa
    WHERE d.marca >= %(first)s AND d.marca < %(last)s AND d.key_dian != 'Procesado Correctamente'
"""
    df_documentos = pd.read_sql(
        query,
        get_engine("facturacion"),
        params={"first": inicio, "last": fin + timedelta(days=1)}
    )

    return df_documentos