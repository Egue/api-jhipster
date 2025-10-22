import os
import PyInstaller.__main__

def build_exe():
    # Configurar opciones de PyInstaller
    PyInstaller.__main__.run([
        'main.py',                          # Script principal
        '--name=URL_Tracker',               # Nombre del ejecutable
        '--onefile',                        # Crear un solo archivo
        '--windowed',                       # Sin consola
        '--icon=assets/icon.ico',           # Ícono de la aplicación
        '--add-data=assets;assets',         # Incluir carpeta de assets
        '--hidden-import=flet',
        '--hidden-import=requests',
        '--hidden-import=bs4',
        '--hidden-import=asyncio',
        '--clean',                          # Limpiar cache
        '--noconsole',                      # Sin consola
    ])

if __name__ == "__main__":
    build_exe()
