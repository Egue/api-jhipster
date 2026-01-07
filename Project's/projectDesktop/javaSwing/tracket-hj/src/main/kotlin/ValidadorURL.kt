import kotlinx.coroutines.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.table.DefaultTableModel

class ValidadorURL : JFrame() {
    private val tabla: JTable
    private val modeloTabla: DefaultTableModel
    private val selectorArchivo: JFileChooser
    
    // Columnas de la tabla
    private val columnas = arrayOf("URL Original", "Estado de Consulta", "Título", "URL Final")
    
    init {
        inicializarComponentes()
        configurarVentana()
    }
    
    private fun inicializarComponentes() {
        // Crear modelo de tabla
        modeloTabla = object : DefaultTableModel(columnas, 0) {
            override fun isCellEditable(row: Int, column: Int) = false
        }
        
        // Crear tabla
        tabla = JTable(modeloTabla).apply {
            selectionMode = ListSelectionModel.SINGLE_SELECTION
            columnModel.getColumn(0).preferredWidth = 200
            columnModel.getColumn(1).preferredWidth = 150
            columnModel.getColumn(2).preferredWidth = 250
            columnModel.getColumn(3).preferredWidth = 200
        }
        
        // Crear scroll pane para la tabla
        val scrollPane = JScrollPane(tabla)
        
        // Crear menú
        val menuBar = JMenuBar()
        val menuArchivo = JMenu("Archivo")
        
        val itemCargarArchivo = JMenuItem("Cargar archivo...").apply {
            addActionListener { cargarArchivo() }
        }
        
        val itemAgregarURL = JMenuItem("Agregar URL").apply {
            addActionListener { agregarURL() }
        }
        
        menuArchivo.add(itemCargarArchivo)
        menuArchivo.add(itemAgregarURL)
        menuBar.add(menuArchivo)
        
        // Configurar selector de archivos
        selectorArchivo = JFileChooser().apply {
            fileFilter = FileNameExtensionFilter("Archivos de texto (*.txt)", "txt")
        }
        
        // Configurar layout
        jMenuBar = menuBar
        layout = BorderLayout()
        add(scrollPane, BorderLayout.CENTER)
        
        // Panel de botones adicional
        val panelBotones = JPanel(FlowLayout()).apply {
            val btnValidarTodos = JButton("Validar Todas las URLs").apply {
                addActionListener { validarTodasLasURLs() }
            }
            val btnLimpiar = JButton("Limpiar Tabla").apply {
                addActionListener { limpiarTabla() }
            }
            
            add(btnValidarTodos)
            add(btnLimpiar)
        }
        add(panelBotones, BorderLayout.SOUTH)
    }
    
    private fun configurarVentana() {
        title = "Validador de URLs - Kotlin"
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(900, 600)
        setLocationRelativeTo(null)
        
        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun cargarArchivo() {
        val resultado = selectorArchivo.showOpenDialog(this)
        if (resultado == JFileChooser.APPROVE_OPTION) {
            val archivo = selectorArchivo.selectedFile
            try {
                archivo.bufferedReader().use { reader ->
                    reader.lineSequence()
                        .map { it.trim() }
                        .filter { it.isNotEmpty() && esURLValida(it) }
                        .forEach { agregarURLATabla(it) }
                }
                JOptionPane.showMessageDialog(
                    this, 
                    "Archivo cargado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE
                )
            } catch (e: IOException) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Error al leer el archivo: ${e.message}", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }
    }
    
    private fun agregarURL() {
        val url = JOptionPane.showInputDialog(
            this, 
            "Ingrese la URL:", 
            "Agregar URL", 
            JOptionPane.PLAIN_MESSAGE
        )
        
        url?.trim()?.takeIf { it.isNotEmpty() }?.let { urlTrimmed ->
            if (esURLValida(urlTrimmed)) {
                agregarURLATabla(urlTrimmed)
            } else {
                JOptionPane.showMessageDialog(
                    this, 
                    "La URL ingresada no es válida", 
                    "URL Inválida", 
                    JOptionPane.WARNING_MESSAGE
                )
            }
        }
    }
    
    private fun esURLValida(url: String): Boolean {
        return try {
            URL(url)
            true
        } catch (e: Exception) {
            false
        }
    }
    
    private fun agregarURLATabla(url: String) {
        val fila = arrayOf(url, "Pendiente", "", "")
        modeloTabla.addRow(fila)
    }
    
    private fun validarTodasLasURLs() {
        for (i in 0 until modeloTabla.rowCount) {
            if (modeloTabla.getValueAt(i, 1) == "Pendiente") {
                validarURL(i)
            }
        }
    }
    
    private fun validarURL(fila: Int) {
        val urlOriginal = modeloTabla.getValueAt(fila, 0) as String
        
        // Usar corrutinas para operaciones asíncronas
        GlobalScope.launch(Dispatchers.IO) {
            val resultado = try {
                modeloTabla.setValueAt("Validando...", fila, 1)
                
                val url = URL(urlOriginal)
                val conexion = url.openConnection() as HttpURLConnection
                
                conexion.apply {
                    requestMethod = "GET"
                    connectTimeout = 10000
                    readTimeout = 10000
                    instanceFollowRedirects = true
                    setRequestProperty("User-Agent", 
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                }
                
                val codigoRespuesta = conexion.responseCode
                val estado = "$codigoRespuesta - ${conexion.responseMessage}"
                val urlFinal = conexion.url.toString()
                
                val titulo = if (codigoRespuesta in 200..299) {
                    // Leer el contenido para extraer el título
                    val contenido = conexion.inputStream.bufferedReader().use { reader ->
                        reader.lineSequence().take(50).joinToString("\n")
                    }
                    extraerTitulo(contenido).takeIf { it.isNotEmpty() } ?: "Sin título"
                } else {
                    "Error al acceder"
                }
                
                conexion.disconnect()
                
                Triple(estado, titulo, urlFinal)
                
            } catch (e: Exception) {
                Triple("Error: ${e.message}", "Error de conexión", urlOriginal)
            }
            
            // Actualizar UI en el hilo principal
            SwingUtilities.invokeLater {
                modeloTabla.setValueAt(resultado.first, fila, 1)
                modeloTabla.setValueAt(resultado.second, fila, 2)
                modeloTabla.setValueAt(resultado.third, fila, 3)
            }
        }
    }
    
    private fun extraerTitulo(html: String): String {
        val patron = Pattern.compile("<title[^>]*>([^<]+)</title>", Pattern.CASE_INSENSITIVE)
        val matcher = patron.matcher(html)
        return if (matcher.find()) {
            matcher.group(1)?.trim() ?: ""
        } else {
            ""
        }
    }
    
    private fun limpiarTabla() {
        val respuesta = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de que desea limpiar toda la tabla?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION
        )
        if (respuesta == JOptionPane.YES_OPTION) {
            modeloTabla.rowCount = 0
        }
    }
}

fun main() {
    SwingUtilities.invokeLater {
        ValidadorURL().isVisible = true
    }
}