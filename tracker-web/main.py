import flet as ft
import requests
from bs4 import BeautifulSoup

def main(page: ft.Page):
    page.title = "Rastreador de URLs"
    page.scroll = "auto"

    file_picker = ft.FilePicker()
    resultados = ft.DataTable(
        columns=[
            ft.DataColumn(ft.Text("URL")),
            ft.DataColumn(ft.Text("Estado")),
        ],
        rows=[]
    )

    def analizar_urls(file_path):
        resultados.rows.clear()
        try:
            with open(file_path, "r") as f:
                urls = [line.strip() for line in f if line.strip()]

            for url in urls:
                try:
                    res = requests.get(url, timeout=5)
                    soup = BeautifulSoup(res.text, "html.parser")
                    title = soup.title.string.strip() if soup.title else "Sin título"

                    if title == "Acceso Restringido":
                        estado = "🔴 Acceso Restringido"
                    else:
                        estado = f"🟢 OK ({title})"

                except Exception as e:
                    estado = f"⚠️ Error: {str(e)}"

                resultados.rows.append(
                    ft.DataRow(
                        cells=[ft.DataCell(ft.Text(url)), ft.DataCell(ft.Text(estado))]
                    )
                )
            page.update()

        except Exception as e:
            page.snack_bar = ft.SnackBar(ft.Text(f"Error: {str(e)}"), bgcolor="red")
            page.snack_bar.open = True
            page.update()

    def on_file_selected(e: ft.FilePickerResultEvent):
        if e.files:
            analizar_urls(e.files[0].path)

    file_picker.on_result = on_file_selected

    cargar_btn = ft.ElevatedButton(
        "Cargar archivo de URLs",
        icon=ft.Icons.UPLOAD_FILE,
        on_click=lambda _: file_picker.pick_files(allow_multiple=False),
    )

    page.overlay.append(file_picker)
    page.add(cargar_btn, resultados)

if __name__ == "__main__":
    ft.app(target=main)

