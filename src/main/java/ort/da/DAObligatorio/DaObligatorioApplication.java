package ort.da.DAObligatorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ort.da.DAObligatorio.modelo.Administrador;
import ort.da.DAObligatorio.modelo.Propietario;
import ort.da.DAObligatorio.modelo.Servicios.Fachada.Fachada;

@SpringBootApplication
public class DaObligatorioApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaObligatorioApplication.class, args);

		cargarDatosDePrueba();
	}

	private static void cargarDatosDePrueba() {
		
		Administrador admin1 = new Administrador("ad1", "pass1", "Admin Uno");// Lógica para cargar datos de prueba en la aplicación
		Administrador admin2 = new Administrador("ad2", "pass2", "Admin Dos");// Lógica para cargar datos de prueba en la aplicación
	
		Fachada.getInstancia().agregarAdministrador(admin1);
		Fachada.getInstancia().agregarAdministrador(admin2);
	
		Propietario ana = new Propietario("a1", "a", "Ana Lopez", null);// Lógica para cargar datos de prueba en la aplicación
		Propietario jose = new Propietario("j1", "j", "Jose Alonso", null);// Lógica para cargar datos de prueba en la aplicación
		Propietario maria = new Propietario("m1", "m", "Maria Gonzales", null);// Lógica para cargar datos de prueba en la aplicación

		Fachada.getInstancia().agregarPropietario(ana);
		Fachada.getInstancia().agregarPropietario(jose);
		Fachada.getInstancia().agregarPropietario(maria);
	}

}
