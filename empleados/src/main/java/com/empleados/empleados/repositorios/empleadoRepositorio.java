
package com.empleados.empleados.repositorios;

import com.empleados.empleados.entidades.empleadoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Betsa
 */
@Repository
public interface  empleadoRepositorio extends JpaRepository<empleadoEntidad, String>{
    @Query("SELECT e FROM empleadoEntidad e WHERE e.nombre LIKE %:nombre%")
    public empleadoEntidad buscarPorNombre(@Param(value = "nombre") String nombre); 
}





   
