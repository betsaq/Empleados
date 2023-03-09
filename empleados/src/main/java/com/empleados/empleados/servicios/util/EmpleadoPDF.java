
package com.empleados.empleados.servicios.util;

import com.empleados.empleados.entidades.empleadoEntidad;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Betsa
 */
public class EmpleadoPDF {
    
    List<empleadoEntidad> ListaEmpleados;

    public EmpleadoPDF(List<empleadoEntidad> ListaEmpleados) {
        super();
        this.ListaEmpleados = ListaEmpleados;
    }
    private void cabeceraTabla(PdfPTable tabla) {

        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(Color.GRAY);
        celda.setPadding(5);
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.BLACK);

        celda.setPhrase(new Phrase("Nombre", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Apellido", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Domicilio", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("CUIT", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Telefono", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Email", fuente));
        tabla.addCell(celda);
    }

    private void datosTabla(PdfPTable tabla) {

        for (empleadoEntidad empleado : ListaEmpleados) {
            tabla.addCell(empleado.getNombre());
            tabla.addCell(empleado.getApellido());
            tabla.addCell(empleado.getDomicilio());  
            tabla.addCell(empleado.getCuit());
            tabla.addCell(String.valueOf(empleado.getTelefono()));
            tabla.addCell(empleado.getEmail());
         

//          tabla.addCell(proveedor.getFechaUltimaActualizacion().toString());
        }

    }

    public void exportar(HttpServletResponse response) throws DocumentException, IOException {
        Document documento = new Document(PageSize.A4.rotate());
        documento.setMargins(90, 90, 40, 20);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLACK);
        fuente.setSize(28);

        Paragraph titulo = new Paragraph("LISTA DE EMPLEADOS", fuente);
        documento.add(titulo);

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy '-' hh:mm a");
        Date currentDate = new Date();
        String date = formatter.format(currentDate);
        documento.add(new Paragraph("Fecha Generado: " + date));

        PdfPTable tabla = new PdfPTable(6);
        Font fuente1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente1.setColor(Color.BLACK);
        fuente1.setSize(12);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setSpacingAfter(20);
        tabla.setSpacingBefore(20);
        tabla.setWidths(new float[]{2.8f, 3.0f, 3.8f, 2.1f, 2.3f, 3.0f});

        tabla.setWidthPercentage(110);

        cabeceraTabla(tabla);
        datosTabla(tabla);

        documento.add(tabla);
        documento.close();
    }
    
}
