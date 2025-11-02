package ort.da.DAObligatorio.utils;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ort.da.DAObligatorio.modelo.*;
import ort.da.DAObligatorio.servicios.fachada.Fachada;
import ort.da.DAObligatorio.excepciones.PeajeException;

@SpringBootApplication(scanBasePackages = "ort.da.DAObligatorio")
public class PeajeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeajeApplication.class, args);

		cargarDatosDePrueba();
	}

	private static void cargarDatosDePrueba() {
			Fachada f = Fachada.getInstancia();



		Administrador admin1 = new Administrador("12345678", "admin.123", "Usuario Administrador", null, null);
        Administrador admin2 = new Administrador("ad2", "pass2", "Admin Dos", null, null);
        f.agregarAdministrador(admin1);
        f.agregarAdministrador(admin2);

		// Estados
        Estado habilitado = new Estado("Habilitado");
        Estado deshabilitado = new Estado("Deshabilitado");
        Estado suspendido = new Estado("Suspendido");
        Estado penalizado = new Estado("Penalizado");
        f.agregarEstado(habilitado);
        f.agregarEstado(deshabilitado);
        f.agregarEstado(suspendido);
        f.agregarEstado(penalizado);

		 // Categorías
        CategoriaVehiculo catAuto = new CategoriaVehiculo("Automóvil");
        CategoriaVehiculo catCamion = new CategoriaVehiculo("Camión");
        f.agregarCategoriaVehiculo(catAuto);
        f.agregarCategoriaVehiculo(catCamion);

        // Puestos
        Puesto puestoCentro = new Puesto("Puesto Centro", "Av. Principal 100");
        Puesto puestoNorte = new Puesto("Puesto Norte", "Camino Norte 200");
        f.agregarPuesto(puestoCentro);
        f.agregarPuesto(puestoNorte);

        // Tarifas (vincular puesto y categoría)
        Tarifa tarifaAutoP1 = new Tarifa(50.0);
        tarifaAutoP1.getPuestos().add(puestoCentro);
        tarifaAutoP1.getCategoriasVehiculos().add(catAuto);
        f.agregarTarifa(tarifaAutoP1);

        Tarifa tarifaCamionP1 = new Tarifa(120.0);
        tarifaCamionP1.getPuestos().add(puestoCentro);
        tarifaCamionP1.getCategoriasVehiculos().add(catCamion);
        f.agregarTarifa(tarifaCamionP1);

        Tarifa tarifaAutoP2 = new Tarifa(40.0);
        tarifaAutoP2.getPuestos().add(puestoNorte);
        tarifaAutoP2.getCategoriasVehiculos().add(catAuto);
        f.agregarTarifa(tarifaAutoP2);

        // Bonificaciones
        Bonificacion exonerados = new Bonificacion("Exonerados");
        Bonificacion frecuentes = new Bonificacion("Frecuentes");
        Bonificacion trabajadores = new Bonificacion("Trabajadores");
        f.agregarBonificacion(exonerados);
        f.agregarBonificacion(frecuentes);
        f.agregarBonificacion(trabajadores);
	
		Vehiculo v1 = new Vehiculo("ABC123", "ModeloX", "Rojo", catAuto);
        Vehiculo v2 = new Vehiculo("DEF456", "ModeloY", "Azul", catCamion);

		Propietario prop1 = new Propietario(
								"23456789", 
								"prop.123", 
								"Usuario Propietario", 
								2000.0, 
								500.0,
								Arrays.asList(v1),
								habilitado
		);
        Propietario prop2 = new Propietario(
								"p2", 
								"p2pass", 
								"Propietario Dos", 
								1000.0, 
								200.0, 
								Arrays.asList(v2),
								habilitado);

        try {
            f.agregarPropietario(prop1);
            f.agregarPropietario(prop2);
        } catch (PeajeException e) {
            // Durante la precarga puede ocurrir una colisión de matrículas; la atrapamos para permitir arranque
            e.printStackTrace();
        }

	}

	

}
