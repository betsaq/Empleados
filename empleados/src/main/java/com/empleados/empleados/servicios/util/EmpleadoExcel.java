package com.empleados.empleados.servicios.util;

import com.empleados.empleados.entidades.empleadoEntidad;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author Betsa
 */
public class EmpleadoExcel {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;

    private List<empleadoEntidad> listaEmpleados;

    public EmpleadoExcel(List<empleadoEntidad> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Empleados");

    }

    private void escribirTitulo() {

        Row fila = hoja.createRow(0);
        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeight(28);
        estilo.setFont(fuente);

        Cell celda = fila.createCell(0);

        celda.setCellValue("REGISTRO DE EMPLEADOS");
        hoja.addMergedRegion(new CellRangeAddress(0, 2, 0, 6));
//        estilo.setAlignment(estilo.ALIGN_RIGHT);
//        fila.setAlignment(fila.ALIGN_CENTER);
        celda.setCellStyle(estilo);

        celda.setAsActiveCell();
    }

    private void escribirCabeceraDeTabla() {
        Row fila = hoja.createRow(3);

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeight(16);
        estilo.setFont(fuente);

        Cell celda = fila.createCell(0);
        celda.setCellValue("Nombre");
        celda.setCellStyle(estilo);

        celda = fila.createCell(1);
        celda.setCellValue("Apellido");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("CUIT");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("Domicilio");
        celda.setCellStyle(estilo);

        celda = fila.createCell(4);
        celda.setCellValue("Email");
        celda.setCellStyle(estilo);

        celda = fila.createCell(5);
        celda.setCellValue("Telefono");
        celda.setCellStyle(estilo);

//        celda = fila.createCell(6);
//        celda.setCellValue("Salario");
//        celda.setCellStyle(estilo);
    }

    private void escribirDatosDeLaTabla() {
        int numeroFilas = 4;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);

        for (empleadoEntidad empleado : listaEmpleados) {
            Row fila = hoja.createRow(numeroFilas++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(empleado.getNombre());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(empleado.getApellido());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(empleado.getDomicilio());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(empleado.getEmail());
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(empleado.getEmail());
            hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(empleado.getTelefono());
            hoja.autoSizeColumn(5);
            celda.setCellStyle(estilo);

//            celda = fila.createCell(6);
//            celda.setCellValue(empleado.getSueldo());
//            hoja.autoSizeColumn(6);
//            celda.setCellStyle(estilo);
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        escribirTitulo();
        escribirCabeceraDeTabla();
        escribirDatosDeLaTabla();

        ServletOutputStream outPutStream = response.getOutputStream();
        libro.write(outPutStream);

        libro.close();
        outPutStream.close();
    }

}
