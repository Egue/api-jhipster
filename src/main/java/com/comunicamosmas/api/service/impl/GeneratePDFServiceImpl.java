package com.comunicamosmas.api.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.comunicamosmas.api.service.IGeneratePDFService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter; 

@Service
public class GeneratePDFServiceImpl implements IGeneratePDFService {

	@Override
	public void generatePdfFactura(HttpServletResponse response) {
		  
		Document document = new Document(PageSize.LETTER);
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream(new File("pdfs/mi-archivo.pdf")));
			document.open();
			
			String html =this.html();
			
			InputStream stream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
			
			//XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
			
		}catch(DocumentException  | IOException e)
		{
			
		}
		
	}
	
	
	private String html() {
		
		return "<table style=\"margin-top: 50px;\" width=\"100%\" cellspacing=\"0\" border=\"0\">\n"
				+ "      <tr>\n"
				+ "          <td><center> <div style=\"width:100px; height:80px;\"  >&nbsp;</div></center></td>\n"
				+ "          <td><center><strong>\n"
				+ "            \n"
				+ "                        <?php echo $nombredeempresa; ?>\n"
				+ "                        <br>\n"
				+ "                        NIT <?php echo $recibo_nit; ?>  \n"
				+ "                        <br>\n"
				+ "                        <?php echo $direccion_formato_recibo; ?> \n"
				+ "                        <br>\n"
				+ "                        <?php echo str_replace(\"https://\", \"www.\", $web_empresa); ?>\n"
				+ "                        <br>\n"
				+ "                        <?php if($validacion_grupo ==\"A\"){ echo $recibo_regimen.\" | \".$texto_adicion_factura; }else{ echo \"&nbsp;\"; } ?>\n"
				+ "                        <br>\n"
				+ "                        <?php if($validacion_grupo == \"A\"){ echo \"Actividad Económica 6120 - 6110\"; }else{ echo \"&nbsp;\"; }   ?>\n"
				+ "\n"
				+ "          </strong></center></td>\n"
				+ "          <td style=\"vertical-align: bottom;\"><center><?php echo $estado_cuenta_grupo; ?><br><strong><?php echo $row_resolucion_activa['prefijo'].$numero_factura; ?></strong></center></td>\n"
				+ "      </tr>\n"
				+ "</table>\n"
				+ "";
	}

}
