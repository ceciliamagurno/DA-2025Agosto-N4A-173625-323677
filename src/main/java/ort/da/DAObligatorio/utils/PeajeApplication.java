package ort.da.DAObligatorio.utils;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.bonificaciones.Bonificacion;
import ort.da.DAObligatorio.modelo.bonificaciones.DescuentoProcentaje;
import ort.da.DAObligatorio.modelo.bonificaciones.Exonerado;
import ort.da.DAObligatorio.modelo.bonificaciones.ReglaBonificacion;
import ort.da.DAObligatorio.modelo.estados.Estado;
import ort.da.DAObligatorio.modelo.estados.EstadoDeshabilitado;
import ort.da.DAObligatorio.modelo.peajes.Puesto;
import ort.da.DAObligatorio.modelo.usuarios.Administrador;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.vehiculos.CategoriaVehiculo;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;
import ort.da.DAObligatorio.servicios.fachada.Fachada;

@SpringBootApplication(scanBasePackages = "ort.da.DAObligatorio")
public class PeajeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeajeApplication.class, args);
        try {
            cargarDatosDePrueba();
        } catch (PeajeException e) {
            e.printStackTrace();
        }
		
	}

    private static void cargarDatosDePrueba() throws PeajeException {
        Fachada f = Fachada.getInstancia();

        //Categorias de Vehiculos
        CategoriaVehiculo catAuto = CategoriaVehiculo.AUTOMOVIL;
        CategoriaVehiculo catCamion = CategoriaVehiculo.CAMION;
        CategoriaVehiculo catMoto = CategoriaVehiculo.MOTO;

        f.agregarCategoriaVehiculo(catAuto);
        f.agregarCategoriaVehiculo(catCamion);
        f.agregarCategoriaVehiculo(catMoto);

        //Puestos o peajes
        Puesto centro = new Puesto("Puesto Centro", "Av. Central 123");
        Puesto norte = new Puesto("Puesto Norte", "Av. del norte 123");
        Puesto sur = new Puesto("Puesto Sur", "Av. del sur 123");

        f.agregarPuesto(sur);
        f.agregarPuesto(norte);
        f.agregarPuesto(centro);


        //Tarifas
        f.crearTarifa(sur, catMoto, 120);
        f.crearTarifa(norte, catAuto, 200);
        f.crearTarifa(centro, catCamion, 350);  

        //Bonificaciones nombre + reglas
        ReglaBonificacion reglaExonerados   = new Exonerado();
        ReglaBonificacion reglaFrecuentes   = new DescuentoProcentaje(50); // 50% desc.
        ReglaBonificacion reglaTrabajadores = new DescuentoProcentaje(20); // 20% desc.


        Bonificacion exonerados   = new Bonificacion("Exonerados",   reglaExonerados);
        Bonificacion frecuentes   = new Bonificacion("Frecuentes",   reglaFrecuentes);
        Bonificacion trabajadores = new Bonificacion("Trabajadores", reglaTrabajadores);

        f.agregarBonificacion(exonerados);
        f.agregarBonificacion(frecuentes);
        f.agregarBonificacion(trabajadores);

        //vehiculos
        Vehiculo v1 = new Vehiculo("ABC123", "ModeloX", "Rojo", catAuto);
        Vehiculo v2 = new Vehiculo("DEF456", "ModeloY", "Azul", catCamion);

        f.agregarVehiculo(v1);
        f.agregarVehiculo(v2);

        //Propietarios uno con estado deshabilitado para la prueba

        
        Estado estadoDeshabilitado = new EstadoDeshabilitado();

        Propietario prop1 = new Propietario(
                "23456789",
                "prop.123",
                "Usuario Propietario",
                2000,
                500);

        Propietario prop2 = new Propietario(
                "23456788",
                "prop.1234",
                "Usuario Propietario Des",
                2000,
                500);
        prop2.setEstado(estadoDeshabilitado);

        f.agregarPropietario(prop1);
        f.agregarPropietario(prop2);

        //administrador

        Administrador admin1 = new Administrador(
            "12345678",
            "admin.123",
            "Usuario Administrador");

        f.agregarAdministrador(admin1);
    }
}