package com.empleados.empleados.controladores;

import com.empleados.empleados.entidades.empleadoEntidad;
import com.empleados.empleados.excepciones.MiException;
import com.empleados.empleados.servicios.empleadoServicio;
import com.empleados.empleados.servicios.util.EmpleadoExcel;
import com.empleados.empleados.servicios.util.EmpleadoPDF;
import com.lowagie.text.DocumentException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/empleado")
public class empleadoControlador {

    @Autowired
    private empleadoServicio empleadoServicio;

    @GetMapping("/crear")
    public String cargar() {
        return "empleado_crear.html";
    }

    @PostMapping("/creado")
    public String llenarProvincia(@RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String cuit,
            @RequestParam String domicilio,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam double sueldo,
            ModelMap modelo) {
        try {
            empleadoServicio.crearEmpleado(nombre, apellido, cuit, domicilio, email, telefono, sueldo);
            modelo.put("Exito", "El empleado fue cargado correctamente");
            modelo.addAttribute("empleado," + " empleadoServicio.listarEmpleado()");
            return "redirect:../empleado/listar";
        } catch (MiException e) {

            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("cuit", cuit);
            modelo.put("domicilio", domicilio);
            modelo.put("email", email);
            modelo.put("telefono", telefono);
            modelo.put("sueldo", sueldo);
            return "empleado_crear.html";
        }
    }

    @GetMapping("/listar")
    public String listarEmpleados(ModelMap modelo) {
        List<empleadoEntidad> empleado = empleadoServicio.listarEmpleado();
        if (!empleado.isEmpty()) {
            modelo.addAttribute("empleado", empleado);
        }
        return "empleado_listar.html";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap modelo) {

        modelo.put("empleado", empleadoServicio.getOne(id));

        return "empleado_editar.html";
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable String id, @RequestParam String nombre, String apellido, String cuit, String domicilio, String email, String telefono, double sueldo, ModelMap modelo) throws MiException {

        try {
            empleadoServicio.crearEmpleado(nombre, apellido, cuit, domicilio, email, telefono, sueldo);
            modelo.put("exito", "exito");
            return "redirect:../listar";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            modelo.put("empleado", empleadoServicio.getOne(id));
            return "empleado_editar.html";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String borrar(@PathVariable String id, ModelMap modelo) throws MiException {
        empleadoServicio.eliminarEmpleado(id);
        return "redirect:/empleado/listar";
    }

    @GetMapping("/exportarPDF")
    public void exportarEmpleadoPDF(HttpServletResponse response) throws DocumentException, IOException {

        response.setContentType("application/pdf");       //tipo de contenido que devuelve

        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");  //muestra fecha en la que se descarga
        String fechaActual = dateFormatter.format(new Date());
        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Empleados_" + fechaActual + ".pdf";   //nombre del archivo generado

        response.setHeader(cabecera, valor);

        List<empleadoEntidad> ListaEmpleados = empleadoServicio.findAll();

        EmpleadoPDF exporter = new EmpleadoPDF(ListaEmpleados);
        exporter.exportar(response);
//        empleadoServicio.exportar(response);
    }

    @GetMapping("/exportarExcel")

    public void exportarListadoDeEmpleadosEnExcel(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Empleados_" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<empleadoEntidad> empleados = empleadoServicio.findAll();

        EmpleadoExcel exporter = new EmpleadoExcel(empleados);
        exporter.exportar(response);
    }
}
