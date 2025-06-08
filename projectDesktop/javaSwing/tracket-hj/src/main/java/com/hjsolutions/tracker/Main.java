package com.hjsolutions.tracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JFileChooser selectorArchivo;

    private final String[] columnas = {"URL Original", "Estado de Consulta", "Título", "URL Final"};
    
    public Main() {
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        // Crear modelo de tabla
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla de solo lectura
            }
        };
        
        // Crear tabla
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(250);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(200);
        
        // Crear scroll pane para la tabla
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        // Crear menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        
        JMenuItem itemCargarArchivo = new JMenuItem("Cargar archivo...");
        JMenuItem itemAgregarURL = new JMenuItem("Agregar URL");
        
        // Configurar acciones del menú
        itemCargarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cargarArchivo();
            }
        });
        
        itemAgregarURL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // agregarURL();
            }
        });
        
        menuArchivo.add(itemCargarArchivo);
        menuArchivo.add(itemAgregarURL);
        menuBar.add(menuArchivo);
        
        // Configurar selector de archivos
        selectorArchivo = new JFileChooser();
        selectorArchivo.setFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));
        
        // Configurar layout
        setJMenuBar(menuBar);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones adicional
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnValidarTodos = new JButton("Validar Todas las URLs");
        JButton btnLimpiar = new JButton("Limpiar Tabla");
        
        btnValidarTodos.addActionListener(e -> validarTodasLasURLs());
        btnLimpiar.addActionListener(e -> limpiarTabla());
        
        panelBotones.add(btnValidarTodos);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void configurarVentana() {
        setTitle("Validador de URLs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cargarArchivo() {
        int resultado = selectorArchivo.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selectorArchivo.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    linea = linea.trim();
                    if (!linea.isEmpty() && esURLValida(linea)) {
                        agregarURLATabla(linea);
                    }
                }
                JOptionPane.showMessageDialog(this, "Archivo cargado exitosamente", 
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + e.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void agregarURL() {
        String url = JOptionPane.showInputDialog(this, "Ingrese la URL:", "Agregar URL", 
                                                JOptionPane.PLAIN_MESSAGE);
        if (url != null && !url.trim().isEmpty()) {
            url = url.trim();
            if (esURLValida(url)) {
                agregarURLATabla(url);
            } else {
                JOptionPane.showMessageDialog(this, "La URL ingresada no es válida", 
                                            "URL Inválida", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private boolean esURLValida(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void agregarURLATabla(String url) {
        Object[] fila = {url, "Pendiente", "", ""};
        modeloTabla.addRow(fila);
    }
    
    private void validarTodasLasURLs() {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if ("Pendiente".equals(modeloTabla.getValueAt(i, 1))) {
                validarURL(i);
            }
        }
    }
    
    private void validarURL(int fila) {
        String urlOriginal = (String) modeloTabla.getValueAt(fila, 0);
        
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            private String estado;
            private String titulo;
            private String urlFinal;
            
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    modeloTabla.setValueAt("Validando...", fila, 1);
                    
                    URL url = new URL(urlOriginal);
                    HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                    conexion.setRequestMethod("GET");
                    conexion.setConnectTimeout(10000);
                    conexion.setReadTimeout(10000);
                    conexion.setInstanceFollowRedirects(true);
                    conexion.setRequestProperty("User-Agent", 
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
                    
                    int codigoRespuesta = conexion.getResponseCode();
                    estado = codigoRespuesta + " - " + conexion.getResponseMessage();
                    urlFinal = conexion.getURL().toString();
                    
                    if (codigoRespuesta >= 200 && codigoRespuesta < 300) {
                        // Leer el contenido para extraer el título
                        BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conexion.getInputStream(), "UTF-8"));
                        StringBuilder contenido = new StringBuilder();
                        String linea;
                        int lineasLeidas = 0;
                        
                        while ((linea = reader.readLine()) != null && lineasLeidas < 50) {
                            contenido.append(linea).append("\n");
                            lineasLeidas++;
                        }
                        reader.close();
                        
                        titulo = extraerTitulo(contenido.toString());
                        if (titulo.isEmpty()) {
                            titulo = "Sin título";
                        }
                    } else {
                        titulo = "Error al acceder";
                    }
                    
                    conexion.disconnect();
                    
                } catch (Exception e) {
                    estado = "Error: " + e.getMessage();
                    titulo = "Error de conexión";
                    urlFinal = urlOriginal;
                }
                return null;
            }
            
            @Override
            protected void done() {
                modeloTabla.setValueAt(estado, fila, 1);
                modeloTabla.setValueAt(titulo, fila, 2);
                modeloTabla.setValueAt(urlFinal, fila, 3);
            }
        };
        
        worker.execute();
    }
    
    private String extraerTitulo(String html) {
        Pattern patron = Pattern.compile("<title[^>]*>([^<]+)</title>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = patron.matcher(html);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }
    
    private void limpiarTabla() {
        int respuesta = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de que desea limpiar toda la tabla?", 
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            modeloTabla.setRowCount(0);
        }
    }
    public 
    static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}