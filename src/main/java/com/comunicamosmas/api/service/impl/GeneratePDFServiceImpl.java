package com.comunicamosmas.api.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.comunicamosmas.api.domain.Contrato;
import com.comunicamosmas.api.domain.Empresa;
import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.domain.Deuda;
import com.comunicamosmas.api.domain.EmailCampaign;
import com.comunicamosmas.api.service.IApiRestService;
import com.comunicamosmas.api.service.IClienteService;
import com.comunicamosmas.api.service.IContratoComboService;
import com.comunicamosmas.api.service.IContratoService;
import com.comunicamosmas.api.service.IDeudaService;
import com.comunicamosmas.api.service.IEmpresaService;
import com.comunicamosmas.api.service.IGeneratePDFService;
import com.comunicamosmas.api.service.ISystemConfigService;
import com.comunicamosmas.api.service.dto.ContratoInfoFacturaDTO;
import com.comunicamosmas.api.service.dto.EmailCampaignDetalleDTO;
import com.comunicamosmas.api.service.dto.FacturaElectronicaResponseDTO;
import com.comunicamosmas.api.service.dto.RespuestaGeneracionPDFFactura;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;
import com.fasterxml.jackson.core.JsonParseException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.comunicamosmas.api.service.dto.DeudasForFacturaDTO;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class GeneratePDFServiceImpl implements IGeneratePDFService {

	@Autowired
	IContratoService contratoService;

	@Autowired
	IEmpresaService empresaService;

	@Autowired
	IClienteService clienteService;

	@Autowired
	IDeudaService deudasService;

	@Autowired
	IApiRestService apiRestService;

	@Autowired
	ISystemConfigService systemService;

	@Autowired
	IContratoComboService comboService;

	@Override
	public RespuestaGeneracionPDFFactura generateFacturaPDF(EmailCampaignDetalleDTO detalle,
			List<DeudasForFacturaDTO> deudas, EmailCampaign campaña) {

		// buscar informacion del contrato
		// Contrato contrato =
		// contratoService.findById(detalle.getIdContrato().longValue());
		// buscar empresa por contrato
		Long idEmpresa = 0L;
		// Long idCliente = 0L;
		Long idContrato = 0L;
		// System.out.print(deudas.toString());
		for (DeudasForFacturaDTO rs : deudas) {
			idEmpresa = rs.getId_empresa().longValue();
			// idCliente = rs.getId_cliente();
			idContrato = rs.getId_contrato().longValue();
		}
		Empresa empresa = empresaService.findById(idEmpresa);
		// System.out.print(empresa.toString());
		// Cliente cliente =
		// clienteService.findById(detalle.getIdCliente().longValue());
		ContratoInfoFacturaDTO clienteDTO = contratoService.contratoFindFactura(idContrato);
		// System.out.print(clienteDTO.toString());
		return this.pdf(empresa, clienteDTO, detalle, deudas, campaña);

	}

	/** pdf */
	private RespuestaGeneracionPDFFactura pdf(Empresa empresa, ContratoInfoFacturaDTO cliente,
			EmailCampaignDetalleDTO detalle,
			List<DeudasForFacturaDTO> deudas, EmailCampaign campaña) {

		// instancia de la respuesta del resultado
		RespuestaGeneracionPDFFactura responseFactura = new RespuestaGeneracionPDFFactura();
		responseFactura.setFactura(detalle.getFactura());
		responseFactura.setCodigoDocumento("01");
		responseFactura.setNameComercial(empresa.getNombreComercial());
		// path para guardar el archivo pdf
		String namePdf = empresa.getNit().toString() +empresa.getId() + detalle.getOrigen()+detalle.getFactura() + ".pdf";

		// String pathPdf = "/home/programador/Documentos/"+namePdf;
		SystemConfig system = systemService.findByOrigen("facturas");
		String pathPdf = system.getComando() +  namePdf;

		responseFactura.setPathPDF(pathPdf);// guardamos el path del pdf
		// buscar datos de la empresa

		// Datos de la factura
		String nombreEmpresa = empresa.getRazonSocial();

		responseFactura.setRazon_social(nombreEmpresa);// guardamo el nombre de la empresa;
		responseFactura.setNit(empresa.getNit() + empresa.getDv().toString());
		responseFactura.setLogoPublico(empresa.getLogoPublico());

		String nitEmpresa = "NIT:" + empresa.getNit() + "-" + empresa.getDv();
		String direccionEmpresa = empresa.getCiudad() + " | " + empresa.getDireccion() + " | " + empresa.getTelefonos();
		String webEmpresa = empresa.getWeb();
		String regimenEmpresa = empresa.getRegimen();
		String actividadEmpresa = "Actividad Económica 6120 - 6110";

		// Datos del cliente
		String identificacionCliente = cliente.getDocumento();
		String nombreCliente = cliente.getNombreCliente();
		responseFactura.setDestinatario(nombreCliente);

		String idCliente = cliente.getIdCliente().toString();
		String cus = ""; // detalle.getIdContrato().toString();
		String direccionCliente = cliente.getDireccion();
		String telefonoCliente = cliente.getCelular();

		// link de fondo
		String fondoA = "http://10.111.39.3/control/archivos/fondo_factura/" + empresa.getFondoFactura();
		//String fondoA = "http://190.121.145.227:9050/control/archivos/fondo_factura/" + empresa.getFondoFactura();
		String fondoB = "http://10.111.39.3/control/archivos/fondo_factura/" + empresa.getFondoFacturaB();
		//String fondoB = "http://190.121.145.227:9050/control/archivos/fondo_factura/" + empresa.getFondoFacturaB();

		// factura
		String fechaEmision = "";
		String prefijoFactura = "";
		String numeroFactura = detalle.getFactura();
		String periodoFacturado = "";
		String fechaLimite = campaña.getFechaLimitePago();
		String fechaCorte = campaña.getFechaCorte();

		Integer mesServicio = 0;

		double baseImpuesto = 0;// suma de valor_base
		double iva = 0;// suma valor_iva
		double totalMes = 0;// suma de valor_total
		double pagoCuenta = 0;// suma de valor parcial

		double saldoAnterior = 0;
		double pagoAnterior = 0;
		double totalPagar = 0;
		String cufe = "";
		String[] web = empresa.getWeb().split("//");

		String webPago = "pagos." + web[1];
		String numeroResolucion = "";
		String fechaAprobado = "";
		String prefijo = "";
		String numeroInicial = "";
		String numeroFinal = "";
		// variables para saldos
		Integer saldoAnteriorFecha = 0;
		// respues de DIAN
		String resultadoFacturaElectronica = "";
		String imagenQr = "";
		// recorrer
		String[] descripcion = new String[10];
		String[] valorServicio = new String[10];
		for (int i = 0; i < 10; i++) {
			if (i < deudas.size()) {

				DeudasForFacturaDTO deuda = deudas.get(i);
				// cus
				cus = deuda.getId_contrato().toString();
				fechaEmision = this.transformFechaCompleta(deuda.getFacturado_fecha());
				saldoAnteriorFecha = deuda.getFacturado_fecha();
				periodoFacturado = this.transformFecha(deuda.getMes_servicio());
				///
				mesServicio = deuda.getMes_servicio();
				// valores factura
				baseImpuesto += deuda.getValor_base();
				iva += deuda.getValor_iva();
				totalMes += deuda.getValor_total();
				pagoCuenta += deuda.getValor_parcial();
				valorServicio[i] = deuda.getValor_base().toString();
				// resolucion
				prefijoFactura = deuda.getPrefijo();
				numeroResolucion = deuda.getNum_resolucion();
				fechaAprobado = deuda.getFecha_resolucion().toString();
				prefijo = deuda.getPrefijo();
				numeroInicial = deuda.getRango_inicio();
				numeroFinal = deuda.getRango_final();
				// respuesta a DIAN
				resultadoFacturaElectronica = deuda.getResultado_factura_electronica();
				// mensualidades / instalacion/ reconexion / otros / materiales
				if (deuda.getInstalacion().equals(1)) {
					descripcion[i] = deuda.getNombre() + " / Instalación / "
							+ this.transformFecha(deuda.getMes_servicio());
				} else if (deuda.getReconexion().equals(1)) {
					descripcion[i] = deuda.getNombre() + " / Reconexión / "
							+ this.transformFecha(deuda.getMes_servicio());
				} else if (deuda.getMateriales().equals(1)) {
					descripcion[i] = deuda.getNombre() + " / Materiales / "
							+ this.transformFecha(deuda.getMes_servicio());
				} else if (deuda.getTraslado().equals(1)) {
					descripcion[i] = deuda.getNombre() + " / Traslado / "
							+ this.transformFecha(deuda.getMes_servicio());
				} else if (deuda.getOtros().equals(1)) {
					descripcion[i] = deuda.getNombre() + " / " + deuda.getConcepto_aux() + " / "
							+ this.transformFecha(deuda.getMes_servicio());
				} else {
					descripcion[i] = deuda.getNombre() + " / Mensualidad / "
							+ this.transformFecha(deuda.getMes_servicio());
				}

			} else {
				descripcion[i] = "";
				valorServicio[i] = "";
			}

		}
		// finalización del bucle para recorrer facturas
		/***/
		responseFactura.setPrefijo(prefijo);
		// saldofactura , empresa ,

		// contrato
		Contrato contrato = contratoService.findById(Long.parseLong(cus));
		List<Integer> consulta = new ArrayList<>();

		if (contrato.getCombo().equals(1)) {
			Optional<List<Object[]>> listCombo = comboService.findByEmpresaAndIdCombo(empresa.getId(),
					contrato.getIdCombo());

			consulta = listCombo.map(resp -> {
				List<Integer> list = new ArrayList<>();
				for (Object[] rs : resp) {
					list.add((Integer) Integer.parseInt(rs[0].toString()));
				}

				return list;

			}).orElse(null);
		} else {

			consulta.add(Integer.parseInt(cus));
		}

		Optional<List<Object[]>> resultSaldo = deudasService.findSalgoAnterior(saldoAnteriorFecha, consulta);

		double searchSaldo = resultSaldo.map(resp -> {
			double sald = 0.0;
			float parcial = 0;
			double total = 0.0;
			for (Object[] rs : resp) {
				parcial += (float) rs[1];
				total += (double) rs[2];
			}

			sald = total - parcial;

			return sald;

		}).orElse(0.0);

		saldoAnterior += searchSaldo;
		// sumammos totalApagar
		// DecimalFormat decimalFormat = new DecimalFormat("#0,00");

		totalPagar = totalMes - pagoCuenta + saldoAnterior;

		// String totalPagarString = decimalFormat.format(totalPagar);
		// System.out.println(totalPagarString+"##########################################hola");
		responseFactura.setValorPagar(Double.toString(totalPagar));

		// deserializar respuesta de DIAN PHP SERIALIZABLE
		if (!resultadoFacturaElectronica.isEmpty()) {
			// si es != de vacio se procede a buscar el cufe
			// FacturaElectronicaResponseDTO resultFactura =
			FacturaElectronicaResponseDTO facturaDIAN = this.desSerializable(detalle.getFactura(), mesServicio,
					empresa.getId());

			cufe = facturaDIAN.getCufe();
			imagenQr = facturaDIAN.getUrl_qr();

			// descagar XML
			String pathXML = this.downloadFileXML(facturaDIAN.getUrl_xml_AttachedDocument(),
					empresa, detalle);
			// imagenQr = "QR.png";
			responseFactura.setPathXML(pathXML);

		} else {

			imagenQr = "QR.png";
		}

		// PdfFont myFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
		// Crear documento PDF
		Document document = new Document(PageSize.LETTER);

		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathPdf));
			document.open();

			Rectangle pageSize = writer.getPageSize();
			// Encabezado de la factura
			Font fontEncabezado = FontFactory.getFont("HELVETICA", 9, Font.BOLD);
			PdfPTable tablaEncabezado = new PdfPTable(3);
			float[] columnWidthsE = { 0.2f, 0.4f, 0.2f };
			tablaEncabezado.setWidths(columnWidthsE);
			tablaEncabezado.setWidthPercentage(100);

			PdfPCell celda4 = new PdfPCell();
			celda4.setBorder(Rectangle.NO_BORDER);
			celda4.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaEncabezado.addCell(celda4);

			PdfPCell celda5 = new PdfPCell(
					new Paragraph("\n\n\n\n\n" + nombreEmpresa + "\n" + nitEmpresa + "\n" + direccionEmpresa + "\n"
							+ webEmpresa + "\n" + regimenEmpresa + "\n" + actividadEmpresa, fontEncabezado));
			celda5.setBorder(Rectangle.NO_BORDER);
			celda5.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaEncabezado.addCell(celda5);
			String tipoDocumento = detalle.getOrigen().equals("A") ? "Factura Electrónica" : "Estado de Cuenta";
			PdfPCell celda6 = new PdfPCell(new Paragraph(
					"\n\n\n\n\n" + tipoDocumento + "\n" + prefijoFactura + numeroFactura, fontEncabezado));
			celda6.setBorder(Rectangle.NO_BORDER);
			celda6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablaEncabezado.addCell(celda6);

			document.add(tablaEncabezado);

			// Agregar un espacio en blanco
			Paragraph space = new Paragraph();
			space.setSpacingBefore(3); // espacio antes del espacio en blanco
			space.setSpacingAfter(3); // espacio después del espacio en blanco
			document.add(space);

			// Agregar imagen de fondo
			Image imagenFondo = Image.getInstance(fondoA);
			imagenFondo.scaleToFit(pageSize.getWidth(), pageSize.getHeight());
			PdfContentByte content = writer.getDirectContentUnder();
			imagenFondo.setAbsolutePosition(0, 0);
			content.addImage(imagenFondo);

			// Información de la factura
			PdfPTable table = new PdfPTable(4);
			float[] columnWidths = { 0.11f, 0.6f, 0.2f, 0.2f };
			table.setWidthPercentage(100);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.setWidths(columnWidths);

			Font fontTable = FontFactory.getFont("HELVETICA", 9, Font.NORMAL);
			Font fontTableBold = FontFactory.getFont("HELVETICA", 9, Font.BOLD);
			table.addCell(createCell("Señor(es): ", PdfPCell.ALIGN_LEFT, Element.RECTANGLE, fontTableBold, 1, 0, 1, 1));
			table.addCell(createCell(nombreCliente + " | " + idCliente + " | " + cus, PdfPCell.ALIGN_LEFT,
					Element.RECTANGLE, fontTable, 0, 0, 1, 1));
			table.addCell(createCell("Fecha de Emisión ", PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTableBold, 1,
					0, 1, 1));
			table.addCell(createCell(fechaEmision, PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTable, 1, 1, 1, 1));
			table.addCell(createCell("NIT/CC: ", PdfPCell.ALIGN_LEFT, Element.RECTANGLE, fontTableBold, 1, 0, 0, 1));
			table.addCell(
					createCell(identificacionCliente, PdfPCell.ALIGN_LEFT, Element.RECTANGLE, fontTable, 0, 0, 0, 1));
			table.addCell(createCell("Periodo Facturado ", PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTableBold, 1,
					0, 0, 1));
			table.addCell(
					createCell(periodoFacturado, PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTable, 1, 1, 0, 1));
			table.addCell(createCell("Teléfonos: ", PdfPCell.ALIGN_LEFT, Element.RECTANGLE, fontTableBold, 1, 0, 0, 1));
			table.addCell(createCell(telefonoCliente, PdfPCell.ALIGN_LEFT, Element.RECTANGLE, fontTable, 0, 0, 0, 1));
			table.addCell(
					createCell("Fecha Limite", PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTableBold, 1, 0, 0, 1));
			table.addCell(createCell(fechaLimite, PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTable, 1, 1, 0, 1));
			table.addCell(createCell("Dirección: ", PdfPCell.ALIGN_LEFT, Element.RECTANGLE, fontTableBold, 1, 0, 0, 1));
			table.addCell(createCell(direccionCliente, PdfPCell.ALIGN_LEFT, Element.RECTANGLE, fontTable, 0, 0, 0, 1));
			table.addCell(
					createCell("Fecha Corte", PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTableBold, 1, 0, 0, 1));
			table.addCell(createCell(fechaCorte, PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTable, 1, 1, 0, 1));

			document.add(table);

			// Agregar un espacio en blanco
			Paragraph spaceE = new Paragraph();
			spaceE.setSpacingBefore(3); // espacio antes del espacio en blanco
			spaceE.setSpacingAfter(3); // espacio después del espacio en blanco
			document.add(spaceE);

			// Detalle de la factura
			PdfPTable detalleTable = new PdfPTable(2);
			float[] columnWidths2 = { 0.91f, 0.2f };
			detalleTable.setWidthPercentage(100);
			detalleTable.getDefaultCell().setBorder(Rectangle.TABLE);
			detalleTable.setWidths(columnWidths2);

			Font fontDetalle = FontFactory.getFont("HELVETICA", 9, Font.NORMAL);
			Font fontDetalleBold = FontFactory.getFont("HELVETICA", 9, Font.BOLD);

			detalleTable.addCell(createCell("DESCRIPCIÓN", PdfPCell.ALIGN_UNDEFINED, Element.ALIGN_CENTER,
					fontDetalleBold, 1, 0, 1, 1));
			detalleTable.addCell(
					createCell("VALOR", PdfPCell.ALIGN_UNDEFINED, Element.ALIGN_CENTER, fontDetalleBold, 1, 1, 1, 1));

			detalleTable.addCell(createCell(descripcion[0], PdfPCell.ALIGN_UNDEFINED, Element.RECTANGLE,
					fontDetalle, 1, 0, 0, 0));
			detalleTable.addCell(createCell(formatCurrency(valorServicio[0]), PdfPCell.ALIGN_UNDEFINED,
					Element.ALIGN_RIGHT, fontDetalle, 1, 1, 0, 0));

			detalleTable.addCell(createCell(descripcion[1], PdfPCell.ALIGN_UNDEFINED, Element.RECTANGLE,
					fontDetalle, 1, 0, 0, 0));
			detalleTable.addCell(createCell(formatCurrency(valorServicio[1]), PdfPCell.ALIGN_UNDEFINED,
					Element.ALIGN_RIGHT, fontDetalle, 1, 1, 0, 0));

			detalleTable.addCell(createCell(descripcion[2], PdfPCell.ALIGN_UNDEFINED, Element.RECTANGLE,
					fontDetalle, 1, 0, 0, 0));
			detalleTable.addCell(createCell(formatCurrency(valorServicio[2]), PdfPCell.ALIGN_UNDEFINED,
					Element.ALIGN_RIGHT, fontDetalle, 1, 1, 0, 0));

			detalleTable.addCell(createCell(descripcion[3], PdfPCell.ALIGN_UNDEFINED, Element.RECTANGLE,
					fontDetalle, 1, 0, 0, 0));
			detalleTable.addCell(createCell(formatCurrency(valorServicio[3]), PdfPCell.ALIGN_UNDEFINED,
					Element.ALIGN_RIGHT, fontDetalle, 1, 1, 0, 0));

			detalleTable.addCell(createCell(descripcion[4], PdfPCell.ALIGN_UNDEFINED, Element.RECTANGLE,
					fontDetalle, 1, 0, 0, 0));
			detalleTable.addCell(createCell(formatCurrency(valorServicio[4]), PdfPCell.ALIGN_UNDEFINED,
					Element.ALIGN_RIGHT, fontDetalle, 1, 1, 0, 0));

			detalleTable.addCell(createCell(descripcion[5], PdfPCell.ALIGN_UNDEFINED, Element.RECTANGLE,
					fontDetalle, 1, 0, 0, 0));
			detalleTable.addCell(createCell(formatCurrency(valorServicio[5]), PdfPCell.ALIGN_UNDEFINED,
					Element.ALIGN_RIGHT, fontDetalle, 1, 1, 0, 0));

			detalleTable.addCell(createCell(descripcion[6], PdfPCell.ALIGN_UNDEFINED, Element.RECTANGLE,
					fontDetalle, 1, 0, 0, 0));
			detalleTable.addCell(createCell(formatCurrency(valorServicio[6]), PdfPCell.ALIGN_UNDEFINED,
					Element.ALIGN_RIGHT, fontDetalle, 1, 1, 0, 0));

			detalleTable.addCell(createCell(descripcion[7], PdfPCell.ALIGN_UNDEFINED, Element.RECTANGLE,
					fontDetalle, 1, 0, 0, 0));
			detalleTable.addCell(createCell(formatCurrency(valorServicio[7]), PdfPCell.ALIGN_UNDEFINED,
					Element.ALIGN_RIGHT, fontDetalle, 1, 1, 0, 0));

			detalleTable.addCell(createCell(descripcion[8], PdfPCell.ALIGN_UNDEFINED, Element.RECTANGLE,
					fontDetalle, 1, 0, 0, 0));
			detalleTable.addCell(createCell(formatCurrency(valorServicio[8]), PdfPCell.ALIGN_UNDEFINED,
					Element.ALIGN_RIGHT, fontDetalle, 1, 1, 0, 0));

			detalleTable.addCell(createCell(descripcion[9], PdfPCell.ALIGN_UNDEFINED, Element.RECTANGLE,
					fontDetalle, 1, 0, 0, 1));
			detalleTable.addCell(createCell(formatCurrency(valorServicio[9]), PdfPCell.ALIGN_UNDEFINED,
					Element.ALIGN_RIGHT, fontDetalle, 1, 1, 0, 1));

			document.add(detalleTable);

			// Total de la factura

			PdfPTable tableTotal = new PdfPTable(2);
			float[] columnWidths3 = { 0.91f, 0.2f };
			tableTotal.setWidths(columnWidths3);
			tableTotal.setWidthPercentage(100);
			tableTotal.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			Font font = FontFactory.getFont("HELVETICA", 9, Font.NORMAL);
			Font fontBold = FontFactory.getFont("HELVETICA", 9, Font.BOLD);
			// primera fila

			tableTotal.addCell(
					createCell("BASE IMPUESTO:", PdfPCell.ALIGN_UNDEFINED, Element.ALIGN_RIGHT, font, 0, 0, 0, 0));
			tableTotal.addCell(
					createCell(formatCurrencyDouble(baseImpuesto), PdfPCell.ALIGN_LEFT, Element.ALIGN_RIGHT, font,
							0, 0, 0, 0));

			// segunda fila
			tableTotal.addCell(createCell("IVA:", PdfPCell.ALIGN_UNDEFINED, Element.ALIGN_RIGHT, font, 0, 0, 0, 0));
			tableTotal.addCell(
					createCell(formatCurrencyDouble(iva), PdfPCell.ALIGN_LEFT, Element.ALIGN_RIGHT, font, 0, 0, 0, 0));

			// tercera fila
			tableTotal.addCell(createCell("TOTAL FACTURADO MES:", PdfPCell.ALIGN_UNDEFINED, Element.ALIGN_RIGHT,
					fontBold, 0, 0, 0, 0));
			tableTotal.addCell(
					createCell(formatCurrencyDouble(totalMes), PdfPCell.ALIGN_LEFT, Element.ALIGN_RIGHT, fontBold,
							0, 0, 0, 0));

			// cuarta fila
			tableTotal.addCell(
					createCell("PAGOS A CUENTAS:", PdfPCell.ALIGN_UNDEFINED, Element.ALIGN_RIGHT, font, 0, 0, 0, 0));
			tableTotal.addCell(
					createCell(formatCurrencyDouble(pagoCuenta), PdfPCell.ALIGN_LEFT, Element.ALIGN_RIGHT, font, 0, 0,
							0, 0));

			// quinta fila
			tableTotal.addCell(
					createCell("SALDO ANTERIOR:", PdfPCell.ALIGN_UNDEFINED, Element.ALIGN_RIGHT, font, 0, 0, 0, 0));
			tableTotal.addCell(
					createCell(formatCurrencyDouble(saldoAnterior), PdfPCell.ALIGN_LEFT, Element.ALIGN_RIGHT, font,
							0, 0, 0, 0));

			// sexta fila
			tableTotal.addCell(
					createCell("PAGO ANTERIOR:", PdfPCell.ALIGN_UNDEFINED, Element.ALIGN_RIGHT, font, 0, 0, 0, 0));
			tableTotal.addCell(
					createCell(formatCurrencyDouble(pagoAnterior), PdfPCell.ALIGN_LEFT, Element.ALIGN_RIGHT, font,
							0, 0, 0, 0));

			// séptima fila
			tableTotal.addCell(createCell("TOTAL A PAGAR CON SALDO ANTERIOR:", PdfPCell.ALIGN_UNDEFINED,
					Element.ALIGN_RIGHT, fontBold, 0, 0, 0, 0));
			tableTotal.addCell(createCell(formatCurrencyDouble(totalPagar), PdfPCell.ALIGN_LEFT, Element.ALIGN_RIGHT,
					fontBold, 0, 0, 0, 0));

			document.add(tableTotal);

			// CUFE
			if (detalle.getOrigen().equals("A")) {
				Font fontParagraph1 = FontFactory.getFont("HELVETICA", 9, Font.NORMAL);
				Paragraph paragraph1 = new Paragraph("CUFE: " + cufe, fontParagraph1);
				paragraph1.setAlignment(Element.ALIGN_CENTER);
				paragraph1.setSpacingBefore(3);
				paragraph1.setSpacingAfter(3);

				document.add(paragraph1);
			}

			// PAGOS EN LINEA
			Font fontParagraph2 = FontFactory.getFont("HELVETICA", 9, Font.BOLD);
			Paragraph paragraph2 = new Paragraph(
					"Paga tambien en linea, a través de " + webPago + " sin costo adicional.", fontParagraph2);
			paragraph2.setAlignment(Element.ALIGN_CENTER);
			paragraph2.setSpacingBefore(3);
			paragraph2.setSpacingAfter(3);
			document.add(paragraph2);

			if (detalle.getOrigen().equals("A")) {
				// TEXTO CON RESOLUCIÓN
				Font fontParagraph3 = FontFactory.getFont("HELVETICA", 9, Font.NORMAL);
				PdfPTable tabla = new PdfPTable(2);
				float[] columnWidths4 = { 0.91f, 0.2f };
				tabla.setWidths(columnWidths4);
				tabla.setWidthPercentage(100);

				// Crear la celda para la primera columna
				PdfPCell celda1 = new PdfPCell(new Paragraph(
						"A esta factura de venta aplica las normas relativas a la letra de cambio (artículo 5 ley 1231 de 2008) resolución y/o autorización de facturación electronica No."
								+ numeroResolucion + " aprobado en " + fechaAprobado + " prefijo " + prefijo
								+ " desde el número " + numeroInicial + " al " + numeroFinal
								+ " estimado cliente, en amparo de la ley habeas data 1266/2008, le informamos que en la eventualidad de tener pagos atrasados en el servicio contratado,se generará el reporte negativo en las centrales de riesgo de información financiera. sí su factura tiene saldos anteriores el servicio será cortado el 1° día del siguiente mes la autoridad de inspección, vigilancia y control en materia de protección de los derechos de los usuarios es la superintendencia de industria y comercio: dirección: cra 13 no. 27 - 00 piso 5, bogotá - línea telefónica nacional: (57) 01 8000 910165 - correo electrónico: info@sic.gov.co",
						fontParagraph3));
				celda1.setBorder(Rectangle.NO_BORDER);
				celda1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
				tabla.addCell(celda1);

				// CODIGO QR
				PdfPCell celda2 = new PdfPCell();
				celda2.setBorder(Rectangle.NO_BORDER);
				// Crear objeto Image y añadirlo a la celda
				Image imagen = Image.getInstance(imagenQr);
				celda2.addElement(imagen);
				tabla.addCell(celda2);
				// Agregar la tabla al documento
				document.add(tabla);
			}

			// Agregar un espacio en blanco
			Paragraph space2 = new Paragraph();
			space2.setSpacingBefore(3); // espacio antes del espacio en blanco
			space2.setSpacingAfter(3); // espacio después del espacio en blanco
			document.add(space2);

			// Agregar un espacio en blanco
			Paragraph space3 = new Paragraph();
			space3.setSpacingBefore(3); // espacio antes del espacio en blanco
			space3.setSpacingAfter(3); // espacio después del espacio en blanco
			document.add(space3);

			// TABLA FINAL SERVICIOS PRESTADOS

			PdfPTable table2 = new PdfPTable(3);
			float[] columnWidths5 = { 0.6f, 0.2f, 0.2f };
			table2.setWidthPercentage(98);
			table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);

			table2.setWidths(columnWidths5);
			Font fontTable2 = FontFactory.getFont("HELVETICA", 9, Font.NORMAL);
			Font fontTable2Bold = FontFactory.getFont("HELVETICA", 9, Font.BOLD);
			table2.addCell(createCell(nombreCliente + " | " + idCliente + " | " + cus + " | " + identificacionCliente,
					PdfPCell.ALIGN_LEFT, Element.RECTANGLE, fontTable2, 1, 0, 1, 0));
			table2.addCell(createCell("", PdfPCell.UNDEFINED, Element.ALIGN_CENTER, fontTable2, 1, 0, 1, 0));
			table2.addCell(createCell("", PdfPCell.UNDEFINED, Element.ALIGN_CENTER, fontTable2, 1, 1, 1, 0));
			table2.addCell(
					createCell(direccionCliente, PdfPCell.ALIGN_LEFT, Element.RECTANGLE, fontTable2, 1, 0, 0, 0));
			table2.addCell(createCell("Servicios Prestados: ", PdfPCell.ALIGN_LEFT, Element.RECTANGLE, fontTable2, 1, 0,
					0, 0));
			table2.addCell(createCell(formatCurrencyDouble(totalPagar), PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER,
					fontTable2, 1, 1, 0, 0));
			table2.addCell(createCell(telefonoCliente, PdfPCell.ALIGN_LEFT, Element.RECTANGLE, fontTable2, 1, 0, 0, 0));
			table2.addCell(createCell("", PdfPCell.UNDEFINED, Element.ALIGN_CENTER, fontTable2, 1, 0, 0, 0));
			table2.addCell(createCell("", PdfPCell.UNDEFINED, Element.ALIGN_CENTER, fontTable2, 1, 1, 0, 0));
			table2.addCell(createCell("", PdfPCell.UNDEFINED, Element.ALIGN_CENTER, fontTable2, 0, 0, 1, 0));
			table2.addCell(
					createCell("Total a Pagar", PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER, fontTable2Bold, 1, 0, 1, 1));
			table2.addCell(createCell(formatCurrencyDouble(totalPagar), PdfPCell.ALIGN_LEFT, Element.ALIGN_CENTER,
					fontTable2Bold, 1, 1, 1, 1));

			document.add(table2);

			if (detalle.getOrigen().equals("A")) {
				// Configurar la segunda página
				document.setPageSize(PageSize.LETTER);
				document.newPage();
				PdfContentByte content2 = writer.getDirectContentUnder();
				Image image = Image.getInstance(fondoB);
				image.scaleToFit(pageSize.getWidth(), pageSize.getHeight());
				image.setAbsolutePosition(0, 0);
				content2.addImage(image);
				Paragraph contenidoSegundaPagina = new Paragraph(" ");
				document.add(contenidoSegundaPagina);
			}
			document.close();

			return responseFactura;

		} catch (DocumentException | IOException e) {

			throw new ExceptionNullSql(new Date(), "Error generado PDF", e.getMessage());

		}
	}

	private static PdfPCell createCell(String content, float borderWidth, int align, Font font, int blet, int brg,
			int btp, int bbt) {
		PdfPCell cell = new PdfPCell(new Phrase(content, font));
		// cell.setBorderWidth(borderWidth);
		// cell.setCellEvent(new RoundedBorder());
		cell.setHorizontalAlignment(align);
		cell.setVerticalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setPaddingTop(3f);
		cell.setPaddingBottom(3f);
		// cell.setBorderWidth(0);
		cell.setBorderWidthLeft(blet);
		cell.setBorderWidthRight(brg);
		cell.setBorderWidthTop(btp);
		cell.setBorderWidthBottom(bbt);
		// cell.disableBorderSide(0);
		return cell;
	}

	/**
	 * formatear variable string para que sea un $ valro,00
	 */
	private String formatCurrency(String amount) {
		if (!amount.isEmpty()) {
			Double valor = Double.parseDouble(amount);
			Locale locale = new Locale("es", "CO"); // Define la localización a Colombia
			NumberFormat currencyFormatter = DecimalFormat.getCurrencyInstance(locale);
			return currencyFormatter.format(valor);
		} else {
			return null;
		}

	}

	/**
	 * formatear variable double para que sea un $ valor , 00
	 */
	private String formatCurrencyDouble(double amount) {
		Locale locale = new Locale("es", "CO"); // Define la localización a Colombia
		NumberFormat currencyFormatter = DecimalFormat.getCurrencyInstance(locale);
		return currencyFormatter.format(amount);
	}

	/**
	 * @param fecha 202303 pasar a Marzo-2023
	 */
	private String transformFecha(Integer fecha) {
		String convert = fecha.toString();
		String fechaSalida = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		try {
			Date fechaDate = dateFormat.parse(convert);
			SimpleDateFormat dateformatSalid = new SimpleDateFormat("MMMM-yyyy");
			fechaSalida = dateformatSalid.format(fechaDate);
		} catch (ParseException e) {

			throw new ExceptionNullSql(new Date(), "Transformando Fecha Integer", e.getMessage());
		}

		return fechaSalida;
	}

	/**
	 * @param fecha 20230309 pasar a dias/mes/año
	 */
	private String transformFechaCompleta(Integer fecha) {
		String convert = fecha.toString();
		String fechaSalida = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			Date fechaDate = dateFormat.parse(convert);
			SimpleDateFormat dateformatSalid = new SimpleDateFormat("dd-MM-yyyy");
			fechaSalida = dateformatSalid.format(fechaDate);
		} catch (ParseException e) {
			throw new ExceptionNullSql(new Date(), "Transformando Fecha Integer", e.getMessage());
		}
		return fechaSalida;
	}

	/***
	 * representación serializada de un arreglo asociativo o un mapa en PHP.
	 * Para mapearlo en Java, necesitarás utilizar una biblioteca de procesamiento
	 * de JSON,
	 * como Gson o Jackson, para deserializar el JSON y convertirlo a un objeto
	 * Java.
	 */
	private FacturaElectronicaResponseDTO desSerializable(String factura, Integer mesServicio, Long idEmpresa) {
		return apiRestService.unSerializablePHP(factura, mesServicio, idEmpresa);
	}

	/**
	 * DESCARGAR XML
	 * Recuerda manejar las excepciones adecuadamente en tu código para lidiar con
	 * posibles errores de conexión o permisos de escritura en la ubicación de
	 * destino.
	 */
	private String downloadFileXML(String url, Empresa nameEmpresa, EmailCampaignDetalleDTO factura) {
		String armandoName = nameEmpresa.getNit().toString() + nameEmpresa.getId()+ factura.getOrigen()+ factura.getFactura() + ".xml";
		SystemConfig system = systemService.findByOrigen("facturas");
		// String savePath = "/home/programador/Documentos/"+armandoName;
		String savePath = system.getComando() + armandoName;
		try {
			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<Resource> response = restTemplate.exchange(url, HttpMethod.GET, null, Resource.class);

			Resource resource = response.getBody();

			File file = new File(savePath);

			FileOutputStream outputStream = new FileOutputStream(file);

			FileCopyUtils.copy(resource.getInputStream(), outputStream);

			return savePath;

		} catch (IOException e) {

			throw new ExceptionNullSql(new Date(), "Error descargando archivo : " + e.getMessage(), e.getMessage());
		}

	}
}
