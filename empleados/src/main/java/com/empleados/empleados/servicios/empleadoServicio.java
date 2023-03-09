package com.empleados.empleados.servicios;

import com.empleados.empleados.entidades.empleadoEntidad;
import com.empleados.empleados.excepciones.MiException;
import com.empleados.empleados.repositorios.empleadoRepositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.apache.commons.math3.stat.inference.TestUtils.g;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class empleadoServicio {

    @Autowired
    private empleadoRepositorio empleadoRepositorio;

    

    @Transactional
    public void crearEmpleado(String nombre, String apellido, String cuit, String domicilio, String email, String telefono, double sueldo) throws MiException {
      validar(nombre, apellido, cuit, domicilio, email, telefono);

        empleadoEntidad empleado = new empleadoEntidad();
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setCuit(cuit);
        empleado.setDomicilio(domicilio);
        empleado.setEmail(email);
        empleado.setTelefono(telefono);
        empleado.setSueldo(sueldo);

        empleadoRepositorio.save(empleado);
    }

    @Transactional(readOnly = true)
    public List<empleadoEntidad> listarEmpleado() {
        List<empleadoEntidad> empleado = new ArrayList();
        empleado = empleadoRepositorio.findAll();
        return empleado;
    }

    @Transactional
    public void modificarEmpleado(String id, String nombre, String apellido, String cuit, String domicilio, String email, String telefono, double sueldo) throws MiException {
        validar(nombre, apellido, cuit, domicilio, email, telefono);
        Optional<empleadoEntidad> respuesta = empleadoRepositorio.findById(id);
        empleadoEntidad empleado = null;
        if (respuesta.isPresent()) {

            empleado = respuesta.get();
            empleado.setNombre(nombre);
            empleado.setApellido(apellido);
            empleado.setCuit(cuit);
            empleado.setDomicilio(domicilio);
            empleado.setEmail(email);
            empleado.setTelefono(telefono);
            empleado.setSueldo(sueldo);

            empleadoRepositorio.save(empleado);
        }
    }

    @Transactional
    public void eliminarEmpleado(String id) {
        Optional<empleadoEntidad> respuesta = empleadoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            empleadoEntidad empleado = respuesta.get();
            empleadoRepositorio.delete(empleado);
        }
    }

    public empleadoEntidad getOne(String id) {
        return empleadoRepositorio.getOne(id);
    }

    private void validar(String nombre, String apellido, String cuit, String domicilio, String email, String telefono) throws MiException {
       if (nombre.isEmpty() || nombre == null || nombre.length() < 4 || nombre.matches("^([A-Z]{1}[a-záéíóú]+[ ]?){1,3}$") == false) {
            throw new MiException("Ingrese un NOMBRE VÁLIDO con al menos de 4 letras");
        }

        if (apellido.isEmpty() || apellido == null || apellido.length() < 4 || apellido.matches("^([A-Z]{1}[a-záéíóú]+[ ]?){1,3}$") == false) {
            throw new MiException("Ingrese un APELLIDO VÁLIDO con al menos 4 letras");
        }

        if (email.isEmpty() || email == null || email.matches("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$") == false) {
            throw new MiException("Ingrese un MAIL VÁLIDO");
        }

        if (cuit.isEmpty() || cuit == null || cuit.matches("^(20|23|27|30|33)([0-9]{9}|-[0-9]{8}-[0-9]{1})$")== false ) {
//            /"^(20|23|27|30|33)([0-9]{9}|-[0-9]{8}-[0-9]{1})$"\g
            throw new MiException("El cuit no puede ser nulo, recuerda ingresarlo con guiones");
        }

        if (domicilio.isEmpty() || domicilio == null) {
            throw new MiException("El domicilio no puede ser nulo");
        }
        if (telefono.isEmpty() || telefono == null) {
            throw new MiException("El telefono no puede ser nulo");
        }
//        if (sueldo.isEmpty() || sueldo == null) {
//            throw new MiException("El sueldo no puede ser nulo");
//        }
  
    }
    @Transactional(readOnly = true)
    public List<empleadoEntidad> findAll() {
        return (List<empleadoEntidad>) empleadoRepositorio.findAll();
    }

  

}
