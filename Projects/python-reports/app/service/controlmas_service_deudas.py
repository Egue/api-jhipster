import pandas as pd
from datetime import date

from app.repository import controlmas_repository_deudas

def get_deudas_by_fecha(inicio : date , fin :date)->pd.DataFrame:
    inicio = int(inicio.strftime("%Y%m%d"))
    fin = int(fin.strftime("%Y%m%d"))
    df_deudas = controlmas_repository_deudas.get_deudas_by_fecha(inicio, fin)
    agrupado = df_deudas.groupby(["nombre_comercial","id_contrato" , "cliente_name" ,"factura" , "facturado_fecha"]).agg(
        valor_base=("valor_base" , "sum"),
        valor_iva = ("valor_iva" , "sum"),
        total_monto=("valor_total" , "sum"),

        cantidad_items=("id_deuda" , "count")
    ).reset_index()

    return agrupado