import flet as ft
import requests
import asyncio
from urllib.parse import urljoin, urlparse
import re
from bs4 import BeautifulSoup
import threading
import time

class URLTracker:
    def __init__(self, page: ft.Page):
        self.page = page
        self.urls = []
        self.urls_data = []
        self.current_page = 0
        self.items_per_page = 10
        self.is_loading = False
        self.is_processing = False
        
        # Configurar la página para usar toda la pantalla
        self.page.title = "URL Tracker"
        self.page.theme_mode = ft.ThemeMode.LIGHT
        self.page.window_width = 1200
        self.page.window_height = 800
        self.page.window_maximized = True
        self.page.padding = 5
        
        # Crear BottomSheet para agregar URLs
        self.url_field = ft.TextField(
            label="URL",
            hint_text="https://ejemplo.com",
            prefix_icon=ft.Icons.LINK, 
            width=400,
            autofocus=True
        )
        
        self.add_url_bottom_sheet = ft.BottomSheet(
            ft.Container(
                ft.Column(
                    [
                        ft.Text("Agregar nueva URL", size=20, weight=ft.FontWeight.BOLD),
                        self.url_field,
                        ft.Text(
                            "Ingrese una URL completa comenzando con http:// o https://",
                            size=12,
                            color=ft.Colors.GREY_600
                        ),
                        ft.Row(
                            [
                                ft.TextButton(
                                    "Cancelar", 
                                    on_click=lambda e: self.page.close(self.add_url_bottom_sheet)
                                ),
                                ft.FilledButton(
                                    "Agregar", 
                                    on_click=self.validate_and_add_url
                                ),
                            ],
                            alignment=ft.MainAxisAlignment.END,
                        ),
                    ],
                    horizontal_alignment=ft.CrossAxisAlignment.STRETCH,
                    spacing=20,
                ),
                padding=30,
            ),
            open=False,
        )
        
        self.page.overlay.append(self.add_url_bottom_sheet)
        
        # Componentes UI
        self.setup_ui()
    
    def setup_ui(self):
        """Configurar la interfaz de usuario con menú de escritorio"""
        # Configurar AppBar como menú principal
        self.page.appbar = ft.AppBar(
            leading=ft.Icon(ft.Icons.TRACK_CHANGES, size=30),
            leading_width=40,
            title=ft.Text("URL Tracker", size=20, weight=ft.FontWeight.BOLD),
            center_title=False,
            bgcolor=ft.Colors.PURPLE_400,
            actions=[
                ft.IconButton(
                    icon=ft.Icons.ADD_LINK,
                    tooltip="Adicionar url",
                    on_click=lambda e: self.show_add_url_dialog(),  # Fix: add lambda
                    icon_color=ft.Colors.WHITE
                ),
                ft.IconButton(
                    icon=ft.Icons.UPLOAD_FILE,
                    tooltip="Cargar archivo de URLs",
                    on_click=lambda _: self.file_picker.pick_files(
                        allowed_extensions=["txt"],
                        dialog_title="Seleccionar archivo de texto con URLs"
                    ),
                    icon_color=ft.Colors.WHITE
                ),
                ft.IconButton(
                    icon=ft.Icons.PLAY_ARROW,
                    tooltip="Ejecutar validación",
                    on_click=self.start_validation,
                    disabled=True,
                    icon_color=ft.Colors.WHITE
                ),
                ft.IconButton(
                    icon=ft.Icons.INFO,
                    tooltip="Información",
                    on_click=lambda e: self.show_info_dialog(e),
                    icon_color=ft.Colors.WHITE
                ),
            ],
        )
        
        # Guardar referencia del botón ejecutar - Corregir índice
        self.execute_btn_ref = self.page.appbar.actions[2]  # Cambiar a índice 2
        
        # File picker
        self.file_picker = ft.FilePicker(
            on_result=self.on_file_picked
        )
        self.page.overlay.append(self.file_picker)
        
        # Loading indicator y estado
        self.status_bar = ft.Container(
            content=ft.Row([
                ft.ProgressRing(visible=False, width=20, height=20),
                ft.Text("", size=14, color=ft.Colors.GREY_600),
            ], alignment=ft.MainAxisAlignment.CENTER, spacing=10),
            bgcolor=ft.Colors.GREY_100,
            padding=10,
            visible=False
        )
        
        # DataTable con scroll horizontal - Tamaño más grande
        self.data_table = ft.DataTable(
            columns=[
                ft.DataColumn(ft.Text("URL Original", weight=ft.FontWeight.BOLD, size=16)),
                ft.DataColumn(ft.Text("Estado", weight=ft.FontWeight.BOLD, size=16)),
                ft.DataColumn(ft.Text("Título", weight=ft.FontWeight.BOLD, size=16)),
                ft.DataColumn(ft.Text("URL Final", weight=ft.FontWeight.BOLD, size=16)),
            ],
            rows=[],
            border=ft.border.all(2, ft.Colors.GREY_300),
            border_radius=10,
            vertical_lines=ft.border.BorderSide(1, ft.Colors.GREY_300),
            horizontal_lines=ft.border.BorderSide(1, ft.Colors.GREY_300),
            column_spacing=30,
            data_row_min_height=80,
            data_row_max_height=150,
        )
        
        # Controles de paginación
        self.page_info = ft.Text("", size=14, weight=ft.FontWeight.BOLD)
        self.prev_btn = ft.IconButton(
            icon=ft.Icons.ARROW_BACK_IOS,
            tooltip="Página anterior",
            on_click=self.prev_page,
            disabled=True
        )
        self.next_btn = ft.IconButton(
            icon=ft.Icons.ARROW_FORWARD_IOS,
            tooltip="Página siguiente", 
            on_click=self.next_page,
            disabled=True
        )

        # DataTable container - Solo visible cuando hay datos
        self.table_container = ft.Container(
            content=ft.Column([
                ft.Row([
                    ft.Container(
                        content=self.data_table,
                        expand=True,
                    )
                ], scroll=ft.ScrollMode.AUTO, expand=True),
                
                # Paginación
                ft.Container(
                    content=ft.Row([
                        self.prev_btn,
                        self.page_info,
                        self.next_btn
                    ], alignment=ft.MainAxisAlignment.CENTER),
                    padding=10,
                    bgcolor=ft.Colors.GREY_50
                ),
            ], spacing=0),
            border=ft.border.all(1, ft.Colors.GREY_400),
            border_radius=10,
            bgcolor=ft.Colors.WHITE,
            expand=True,
            visible=False  # Oculto por defecto
        )
        
        # Vista vacía cuando no hay datos
        self.empty_view = ft.Container(
            content=ft.Column([
                ft.Icon(ft.Icons.CLOUD_UPLOAD, size=100, color=ft.Colors.GREY_400),
                ft.Text(
                    "Carga un archivo de texto con URLs para comenzar",
                    size=18,
                    color=ft.Colors.GREY_600,
                    text_align=ft.TextAlign.CENTER
                ),
                ft.Text(
                    "Utiliza el botón de carga en la barra superior",
                    size=14,
                    color=ft.Colors.GREY_500,
                    text_align=ft.TextAlign.CENTER
                )
            ], 
            horizontal_alignment=ft.CrossAxisAlignment.CENTER,
            spacing=20),
            expand=True,
            alignment=ft.alignment.center
        )
        
        # Progress bar para el procesamiento
        self.progress_container = ft.Container(
            content=ft.Column([
                ft.ProgressBar(
                    value=0,
                    bgcolor=ft.Colors.GREY_300,
                    color=ft.Colors.BLUE_600,
                ),
                ft.Text("", size=12, text_align=ft.TextAlign.CENTER)
            ], spacing=5),
            padding=20,
            visible=False,
            bgcolor=ft.Colors.WHITE,
            border_radius=10,
            margin=10
        )
        
        # Layout principal optimizado para escritorio
        self.page.add(
            ft.Column([
                # Status bar
                self.status_bar,
                
                # Progress container
                self.progress_container,
                
                # Vista principal - Muestra tabla o vista vacía
                ft.Stack([
                    self.empty_view,
                    self.table_container
                ], expand=True)
                
            ], expand=True, spacing=0)
        )
    
    def show_info_dialog(self, e):
        """Mostrar diálogo de información"""
        dialog = ft.AlertDialog(
            modal=True,
            title=ft.Text("URL Tracker - Información"),
            content=ft.Text(
                "Esta aplicación te permite:\n\n"
                "• Cargar un archivo de texto con URLs\n"
                "• Validar cada URL automáticamente\n"
                "• Obtener títulos de las páginas web\n"
                "• Ver URLs finales después de redirecciones\n"
                "• Navegar por los resultados con paginación\n\n"
                "Formato del archivo: Una URL por línea\n"
                "Ejemplo:\nhttps://www.google.com\nhttps://www.github.com",
                size=14
            ),
            actions=[
                ft.TextButton("Cerrar", on_click=lambda _: self.close_dialog(dialog))
            ],
        )
        self.page.dialog = dialog
        dialog.open = True
        self.page.update()
    
    def close_dialog(self, dialog):
        """Cerrar diálogo"""
        self.page.close_dialog()
        self.page.update()
    
    def on_file_picked(self, e: ft.FilePickerResultEvent):
        """Manejar la selección de archivo"""
        if e.files:
            file_path = e.files[0].path
            self.load_urls_from_file(file_path)
    
    def load_urls_from_file(self, file_path: str):
        """Cargar URLs desde archivo de texto"""
        self.show_loading("Cargando archivo...")
        
        def load_file():
            try:
                with open(file_path, 'r', encoding='utf-8') as file:
                    lines = file.readlines()
                
                # Filtrar líneas vacías y limpiar URLs
                self.urls = []
                for line in lines:
                    url = line.strip()
                    if url and (url.startswith('http://') or url.startswith('https://')):
                        self.urls.append(url)
                
                # Inicializar datos
                self.urls_data = []
                for url in self.urls:
                    self.urls_data.append({
                        'original_url': url,
                        'status': 'Pendiente',
                        'title': '',
                        'final_url': ''
                    })
                
                # Actualizar UI en el hilo principal
                self.page.run_thread(self.update_after_load)
                
            except Exception as ex:
                self.page.run_thread(lambda: self.show_error(f"Error al cargar archivo: {str(ex)}"))
        
        # Ejecutar en hilo separado
        threading.Thread(target=load_file, daemon=True).start()
    
    def update_after_load(self):
        """Actualizar UI después de cargar el archivo"""
        self.hide_loading()
        self.current_page = 0
        self.update_table()
        
        # Habilitar botón ejecutar y mostrar tabla
        self.execute_btn_ref.disabled = len(self.urls) == 0
        
        if len(self.urls) > 0:
            self.show_table(True)
            self.show_info_status(f"✅ Cargadas {len(self.urls)} URLs exitosamente")
        else:
            self.show_table(False)
            self.show_error_status("❌ No se encontraron URLs válidas en el archivo")
        
        self.page.update()
    
    def show_table(self, visible: bool):
        """Mostrar u ocultar la tabla de datos"""
        self.table_container.visible = visible
        self.empty_view.visible = not visible
    
    def show_loading(self, message: str):
        """Mostrar indicador de carga"""
        status_row = self.status_bar.content
        status_row.controls[0].visible = True  # ProgressRing
        status_row.controls[1].value = message
        status_row.controls[1].color = ft.Colors.BLUE_600
        self.status_bar.visible = True
        self.is_loading = True
        self.page.update()
    
    def hide_loading(self):
        """Ocultar indicador de carga"""
        status_row = self.status_bar.content
        status_row.controls[0].visible = False  # ProgressRing
        self.status_bar.visible = False
        self.is_loading = False
        self.page.update()
    
    def show_info_status(self, message: str):
        """Mostrar mensaje de información en status bar"""
        status_row = self.status_bar.content
        status_row.controls[0].visible = False
        status_row.controls[1].value = message
        status_row.controls[1].color = ft.Colors.GREEN_600
        self.status_bar.visible = True
        self.page.update()
        
        # Ocultar después de 4 segundos
        def hide_message():
            time.sleep(4)
            self.page.run_thread(lambda: setattr(self.status_bar, 'visible', False) or self.page.update())
        threading.Thread(target=hide_message, daemon=True).start()
    
    def show_error_status(self, message: str):
        """Mostrar mensaje de error en status bar"""
        status_row = self.status_bar.content
        status_row.controls[0].visible = False
        status_row.controls[1].value = message
        status_row.controls[1].color = ft.Colors.RED_600
        self.status_bar.visible = True
        self.page.update()
    
    def start_validation(self, e):
        """Iniciar la validación de URLs"""
        if self.is_processing:
            return
        
        self.is_processing = True
        self.execute_btn_ref.disabled = True
        
        # Mostrar progress container
        progress_col = self.progress_container.content
        progress_col.controls[0].value = 0  # ProgressBar
        progress_col.controls[1].value = "Iniciando validación..."  # Text
        self.progress_container.visible = True
        
        self.page.update()
        
        # Ejecutar validación en hilo separado
        threading.Thread(target=self.validate_urls, daemon=True).start()
    
    def validate_urls(self):
        """Validar todas las URLs"""
        total_urls = len(self.urls_data)
        
        for i, url_data in enumerate(self.urls_data):
            try:
                # Actualizar progreso
                progress = (i + 1) / total_urls
                progress_text = f"Procesando {i + 1} de {total_urls}: {url_data['original_url'][:50]}..."
                
                self.page.run_thread(lambda: self.update_progress(progress, progress_text))
                
                # Validar URL
                result = self.validate_single_url(url_data['original_url'])
                
                # Actualizar datos
                url_data.update(result)
                
                # Actualizar tabla cada 5 URLs o en la última
                if (i + 1) % 5 == 0 or i == total_urls - 1:
                    self.page.run_thread(self.update_table)
                
                # Pequeña pausa para no saturar
                time.sleep(0.1)
                
            except Exception as ex:
                url_data['status'] = 'Error'
                url_data['title'] = f'Error: {str(ex)}'
        
        # Finalizar procesamiento
        self.page.run_thread(self.finish_validation)
    
    def validate_single_url(self, url: str) -> dict:
        """Validar una sola URL y obtener su título"""
        try:
            # Configurar headers para parecer un navegador real
            headers = {
                'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
            }
            
            # Realizar request con timeout
            response = requests.get(url, headers=headers, timeout=10, allow_redirects=True)
            
            if response.status_code == 200:
                # Obtener URL final después de redirecciones
                final_url = response.url
                
                # Extraer título de la página
                soup = BeautifulSoup(response.content, 'html.parser')
                title_tag = soup.find('title')
                title = title_tag.get_text(strip=True) if title_tag else 'Sin título'
                
                return {
                    'status': f'✅ {response.status_code}',
                    'title': title[:100] + '...' if len(title) > 100 else title,
                    'final_url': final_url
                }
            else:
                return {
                    'status': f'❌ {response.status_code}',
                    'title': f'Error HTTP {response.status_code}',
                    'final_url': response.url
                }
                
        except requests.exceptions.Timeout:
            return {
                'status': '⏱️ Timeout',
                'title': 'Tiempo de espera agotado',
                'final_url': url
            }
        except requests.exceptions.ConnectionError:
            return {
                'status': '🔌 Sin conexión',
                'title': 'Error de conexión',
                'final_url': url
            }
        except Exception as ex:
            return {
                'status': '❌ Error',
                'title': f'Error: {str(ex)[:50]}',
                'final_url': url
            }
    
    def update_progress(self, progress: float, text: str):
        """Actualizar barra de progreso"""
        progress_col = self.progress_container.content
        progress_col.controls[0].value = progress  # ProgressBar
        progress_col.controls[1].value = text  # Text
        self.page.update()
    
    def finish_validation(self):
        """Finalizar el proceso de validación"""
        self.is_processing = False
        self.execute_btn_ref.disabled = False
        
        # Ocultar progress container
        self.progress_container.visible = False
        
        self.update_table()
        self.show_info_status("✅ Validación completada exitosamente")
    
    def update_table(self):
        """Actualizar la tabla con paginación"""
        if not self.urls_data:
            self.data_table.rows = []
            self.page_info.value = ""
            self.prev_btn.disabled = True
            self.next_btn.disabled = True
            self.show_table(False)
            self.page.update()
            return
        
        # Mostrar tabla si hay datos
        self.show_table(True)
        
        # Calcular índices para paginación
        start_idx = self.current_page * self.items_per_page
        end_idx = min(start_idx + self.items_per_page, len(self.urls_data))
        
        # Crear filas para la página actual - Celdas más grandes
        rows = []
        for i in range(start_idx, end_idx):
            data = self.urls_data[i]
            
            # Crear celdas con texto más grande y mejor espaciado
            original_url_text = data['original_url']
            title_text = data['title']
            final_url_text = data['final_url']
            
            rows.append(
                ft.DataRow(
                    cells=[
                        ft.DataCell(
                            ft.Container(
                                content=ft.Text(
                                    original_url_text,
                                    size=14,
                                    overflow=ft.TextOverflow.ELLIPSIS,
                                    max_lines=3
                                ),
                                width=400,
                                padding=10,
                                alignment=ft.alignment.center_left
                            )
                        ),
                        ft.DataCell(
                            ft.Container(
                                content=ft.Text(
                                    data['status'],
                                    size=14,
                                    weight=ft.FontWeight.BOLD
                                ),
                                width=150,
                                padding=10,
                                alignment=ft.alignment.center
                            )
                        ),
                        ft.DataCell(
                            ft.Container(
                                content=ft.Text(
                                    title_text,
                                    size=14,
                                    overflow=ft.TextOverflow.ELLIPSIS,
                                    max_lines=4
                                ),
                                width=500,
                                padding=10,
                                alignment=ft.alignment.center_left
                            )
                        ),
                        ft.DataCell(
                            ft.Container(
                                content=ft.Text(
                                    final_url_text,
                                    size=14,
                                    overflow=ft.TextOverflow.ELLIPSIS,
                                    max_lines=3
                                ),
                                width=400,
                                padding=10,
                                alignment=ft.alignment.center_left
                            )
                        ),
                    ]
                )
            )
        
        self.data_table.rows = rows
        
        # Actualizar información de paginación
        total_pages = (len(self.urls_data) - 1) // self.items_per_page + 1
        self.page_info.value = f"Página {self.current_page + 1} de {total_pages} | {len(self.urls_data)} URLs total"
        
        # Actualizar botones de navegación
        self.prev_btn.disabled = self.current_page == 0
        self.next_btn.disabled = self.current_page >= total_pages - 1
        
        self.page.update()
    
    def prev_page(self, e):
        """Ir a la página anterior"""
        if self.current_page > 0:
            self.current_page -= 1
            self.update_table()
    
    def next_page(self, e):
        """Ir a la página siguiente"""
        total_pages = (len(self.urls_data) - 1) // self.items_per_page + 1
        if self.current_page < total_pages - 1:
            self.current_page += 1
            self.update_table()
    
    def validate_and_add_url(self, e):
        """Validar y agregar URL desde el BottomSheet"""
        try:
            url = self.url_field.value.strip()
            if not url:
                self.url_field.error_text = "La URL es requerida"
                self.page.update()
                return
            
            if not (url.startswith('http://') or url.startswith('https://')):
                self.url_field.error_text = "La URL debe comenzar con http:// o https://"
                self.page.update()
                return

            # Agregar la URL a las listas
            self.urls.append(url)
            self.urls_data.append({
                'original_url': url,
                'status': 'Pendiente',
                'title': '',
                'final_url': ''
            })

            self.execute_btn_ref.disabled = False
            self.show_table(True)
            self.update_table()
            self.show_info_status("✅ URL agregada exitosamente")
            
            # Limpiar campo y cerrar
            self.url_field.value = ""
            self.url_field.error_text = None
            self.page.close(self.add_url_bottom_sheet)

        except Exception as ex:
            self.show_error_status(f"Error al agregar URL: {str(ex)}")
            print(f"Error en validate_and_add_url: {str(ex)}")

    def show_add_url_dialog(self, e=None):
        """Mostrar BottomSheet para agregar URL"""
        try:
            self.page.open(self.add_url_bottom_sheet)
        except Exception as ex:
            self.show_error_status(f"Error al mostrar diálogo: {str(ex)}")
            print(f"Error en show_add_url_dialog: {str(ex)}")

def main(page: ft.Page):
    """Función principal de la aplicación"""
    app = URLTracker(page)

if __name__ == "__main__":
    ft.app(target=main)