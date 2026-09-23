from datetime import date, timedelta
import pandas as pd
from app.db.connections import get_engine

def get_deudas_by_fecha(fecha_inicio : int , fecha_fin:int )->pd.DataFrame:
    sql = """
    SELECT e.nombre_comercial , d.id_contrato, d.factura , d.facturado_fecha , d.valor_base, d.valor_iva , d.valor_total, d.id_deuda, CASE
     WHEN c.tipo_cliente ='N' THEN CONCAT(c.apellido_paterno , ' ',c.apellido_materno , ' ' , c.nombre_primer , ' ' , c.nombre_segundo)
     WHEN c.tipo_cliente ='J' THEN c.razon_social
     END  as cliente_name
       FROM deudas d
    INNER JOIN empresas e ON e.id_empresa = d.id_empresa
    INNER JOIN clientes c ON c.id_cliente = d.id_cliente
    WHERE d.fac_electronica = 1 AND d.facturado_fecha BETWEEN %(inicio)s AND %(fin)s 
    """
    df_deudas = pd.read_sql(sql , get_engine("controlmas") , params={"inicio" : fecha_inicio , "fin": fecha_fin})
    return df_deudas