#!/usr/bin/env python3
"""
mikrotik_ppp_update.py

Se conecta a un router MikroTik por la API (puerto 8728/8729),
lee un CSV con columnas: name, profile
y para cada fila:
  - busca el PPPoE secret cuyo 'name' coincida
  - si existe y su 'profile' actual es "bloqueado" (configurable),
    lo actualiza al nuevo profile indicado en el CSV
  - si no existe, o su profile actual no es "bloqueado", lo reporta y lo omite

Requiere:
    pip install RouterOS-api

Uso:
    python3 mikrotik_ppp_update.py \
        --host 192.168.88.1 \
        --user admin \
        --password "miclave" \
        --csv secrets.csv \
        [--port 8728] \
        [--use-ssl] \
        [--old-profile bloqueado] \
        [--dry-run]

Formato esperado del CSV (con encabezado):
    name,profile
    juan.perez,10mb
    maria.lopez,20mb
"""

import argparse
import csv
import sys

try:
    import routeros_api
except ImportError:
    print("Falta la librería RouterOS-api. Instálala con: pip install RouterOS-api")
    sys.exit(1)


def leer_csv(ruta_csv):
    """Lee el CSV y devuelve una lista de dicts {name, profile}."""
    filas = []
    with open(ruta_csv, newline="", encoding="utf-8-sig") as f:
        lector = csv.DictReader(f)
        # normalizamos encabezados a minúsculas por si vienen distintos
        lector.fieldnames = [h.strip().lower() for h in lector.fieldnames]
        for fila in lector:
            name = (fila.get("name") or "").strip()
            profile = (fila.get("profile") or "").strip()
            if not name or not profile:
                print(f"[AVISO] Fila incompleta ignorada: {fila}")
                continue
            filas.append({"name": name, "profile": profile})
    return filas


def conectar(host, user, password, port, use_ssl):
    conexion = routeros_api.RouterOsApiPool(
        host,
        username=user,
        password=password,
        port=port,
        use_ssl=use_ssl,
        ssl_verify=False,
        plaintext_login=True,
    )
    api = conexion.get_api()
    return conexion, api


def procesar(api, filas, old_profile, dry_run):
    secrets = api.get_resource("/ppp/secret")

    actualizados = 0
    omitidos = 0
    no_encontrados = 0

    for fila in filas:
        name = fila["name"]
        nuevo_profile = fila["profile"]

        encontrados = secrets.get(name=name)

        if not encontrados:
            print(f"[NO ENCONTRADO] Secret '{name}' no existe en la MikroTik.")
            no_encontrados += 1
            continue

        secret = encontrados[0]
        profile_actual = secret.get("profile", "")
        secret_id = secret.get("id") or secret.get(".id")

        if profile_actual != old_profile:
            print(
                f"[OMITIDO] '{name}' tiene profile actual '{profile_actual}' "
                f"(se esperaba '{old_profile}'). No se modifica."
            )
            omitidos += 1
            continue

        if dry_run:
            print(
                f"[DRY-RUN] '{name}': se cambiaría profile de "
                f"'{profile_actual}' a '{nuevo_profile}'"
            )
            actualizados += 1
            continue

        try:
            secrets.set(id=secret_id, profile=nuevo_profile)
            print(f"[OK] '{name}': profile cambiado de '{profile_actual}' a '{nuevo_profile}'")
            actualizados += 1
        except Exception as e:
            print(f"[ERROR] No se pudo actualizar '{name}': {e}")

    print("\n--- Resumen ---")
    print(f"Actualizados:     {actualizados}")
    print(f"Omitidos:         {omitidos}")
    print(f"No encontrados:   {no_encontrados}")
    print(f"Total procesados: {len(filas)}")


def main():
    parser = argparse.ArgumentParser(description="Actualiza profiles de PPPoE secrets en MikroTik desde un CSV.")
    parser.add_argument("--host", required=True, help="IP o host de la MikroTik")
    parser.add_argument("--user", required=True, help="Usuario de la API")
    parser.add_argument("--password", required=True, help="Contraseña de la API")
    parser.add_argument("--csv", required=True, help="Ruta al archivo CSV (columnas: name, profile)")
    parser.add_argument("--port", type=int, default=8728, help="Puerto API (default 8728, usa 8729 con --use-ssl)")
    parser.add_argument("--use-ssl", action="store_true", help="Usar API-SSL (puerto 8729 típicamente)")
    parser.add_argument(
        "--old-profile",
        default="bloqueado",
        help="Nombre del profile que se considera 'bloqueado' (default: 'bloqueado')",
    )
    parser.add_argument(
        "--dry-run",
        action="store_true",
        help="Simula los cambios sin aplicarlos realmente en la MikroTik",
    )

    args = parser.parse_args()

    filas = leer_csv(args.csv)
    if not filas:
        print("No se encontraron filas válidas en el CSV. Nada que hacer.")
        return

    print(f"Se leyeron {len(filas)} filas del CSV.")
    print(f"Conectando a {args.host}:{args.port} (SSL={args.use_ssl})...")

    conexion, api = conectar(args.host, args.user, args.password, args.port, args.use_ssl)
    try:
        procesar(api, filas, args.old_profile, args.dry_run)
    finally:
        conexion.disconnect()


if __name__ == "__main__":
    main()