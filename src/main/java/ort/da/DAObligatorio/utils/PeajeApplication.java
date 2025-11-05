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

        // --- Estados ---
        Estado habilitado = new EstadoHabilitado();
        Estado deshabilitado = new EstadoDeshabilitado();
        Estado suspendido = new EstadoSuspendido();
        Estado penalizado = new EstadoPenalizado();

        // Evitar duplicados si vuelves a cargar
        if (f.getEstados() == null || f.getEstados().stream().noneMatch(e -> "Habilitado".equalsIgnoreCase(e.getNombre())))
            f.agregarEstado(habilitado);
        if (f.getEstados() == null || f.getEstados().stream().noneMatch(e -> "Deshabilitado".equalsIgnoreCase(e.getNombre())))
            f.agregarEstado(deshabilitado);
        if (f.getEstados() == null || f.getEstados().stream().noneMatch(e -> "Suspendido".equalsIgnoreCase(e.getNombre())))
            f.agregarEstado(suspendido);
        if (f.getEstados() == null || f.getEstados().stream().noneMatch(e -> "Penalizado".equalsIgnoreCase(e.getNombre())))
            f.agregarEstado(penalizado);

        // --- Categorías ---
        CategoriaVehiculo catAuto = new CategoriaVehiculo("Automóvil");
        CategoriaVehiculo catCamion = new CategoriaVehiculo("Camión");
        if (f.getCategorias() == null || f.getCategorias().stream().noneMatch(c -> "Automóvil".equalsIgnoreCase(c.getNombre())))
            f.agregarCategoriaVehiculo(catAuto);
        if (f.getCategorias() == null || f.getCategorias().stream().noneMatch(c -> "Camión".equalsIgnoreCase(c.getNombre())))
            f.agregarCategoriaVehiculo(catCamion);

        // --- Puestos ---
        Puesto puestoCentro = new Puesto("Puesto Centro", "Av. Principal 100");
        Puesto puestoNorte = new Puesto("Puesto Norte", "Camino Norte 200");
        if (f.getPuestos() == null || f.getPuestos().stream().noneMatch(p -> "Puesto Centro".equalsIgnoreCase(p.getNombre())))
            f.agregarPuesto(puestoCentro);
        if (f.getPuestos() == null || f.getPuestos().stream().noneMatch(p -> "Puesto Norte".equalsIgnoreCase(p.getNombre())))
            f.agregarPuesto(puestoNorte);

        // --- Tarifas (vincular puesto y categoría) ---
        // Crear Tarifa y asociar puesto/categoría
        Tarifa tarifaAutoP1 = new Tarifa(50.0);
        tarifaAutoP1.getPuestos().add(puestoCentro);
        tarifaAutoP1.getCategoriasVehiculos().add(catAuto);
        if (f.getTarifas() == null || f.getTarifas().stream().noneMatch(t -> t.getMonto() == tarifaAutoP1.getMonto() && t.getCategoriasVehiculos().contains(catAuto)))
            f.agregarTarifa(tarifaAutoP1);

        Tarifa tarifaCamionP1 = new Tarifa(120.0);
        tarifaCamionP1.getPuestos().add(puestoCentro);
        tarifaCamionP1.getCategoriasVehiculos().add(catCamion);
        if (f.getTarifas() == null || f.getTarifas().stream().noneMatch(t -> t.getMonto() == tarifaCamionP1.getMonto() && t.getCategoriasVehiculos().contains(catCamion)))
            f.agregarTarifa(tarifaCamionP1);

        Tarifa tarifaAutoP2 = new Tarifa(40.0);
        tarifaAutoP2.getPuestos().add(puestoNorte);
        tarifaAutoP2.getCategoriasVehiculos().add(catAuto);
        if (f.getTarifas() == null || f.getTarifas().stream().noneMatch(t -> t.getMonto() == tarifaAutoP2.getMonto() && t.getPuestos().contains(puestoNorte)))
            f.agregarTarifa(tarifaAutoP2);

        // --- Bonificaciones ---
        Bonificacion exonerados = new Bonificacion("Exonerados");
        Bonificacion frecuentes = new Bonificacion("Frecuentes");
        Bonificacion trabajadores = new Bonificacion("Trabajadores");
        if (f.getBonificaciones() == null || f.getBonificaciones().stream().noneMatch(b -> "Exonerados".equalsIgnoreCase(b.getNombre())))
            f.agregarBonificacion(exonerados);
        if (f.getBonificaciones() == null || f.getBonificaciones().stream().noneMatch(b -> "Frecuentes".equalsIgnoreCase(b.getNombre())))
            f.agregarBonificacion(frecuentes);
        if (f.getBonificaciones() == null || f.getBonificaciones().stream().noneMatch(b -> "Trabajadores".equalsIgnoreCase(b.getNombre())))
            f.agregarBonificacion(trabajadores);

        // --- Administradores ---
        Administrador admin1 = new Administrador("12345678", "admin.123", "Usuario Administrador", null, null);
        Administrador admin2 = new Administrador("87654321", "admin2.123", "Administrador 2", null, null);
        if (f.getAdministradores() == null || f.getAdministradores().stream().noneMatch(a -> "12345678".equals(a.getCedula())))
            f.agregarAdministrador(admin1);
        if (f.getAdministradores() == null || f.getAdministradores().stream().noneMatch(a -> "87654321".equals(a.getCedula())))
            f.agregarAdministrador(admin2);

        // --- Vehículos y Propietarios ---
        Vehiculo v1 = new Vehiculo("ABC123", "ModeloX", "Rojo", catAuto);
        Vehiculo v2 = new Vehiculo("DEF456", "ModeloY", "Azul", catCamion);

        Propietario prop1 = new Propietario(
            "23456789",
            "prop.123",
            "Usuario Propietario",
            2000.0,
            500.0,
            Arrays.asList(v1),
            deshabilitado
        );
        Propietario prop2 = new Propietario(
            "2",
            "prop2.123",
            "Propietario Dos",
            1000.0,
            200.0,
            Arrays.asList(v2),
            habilitado
        );

        try {
            // Fachada/ServicioUsuarios valida matrículas duplicadas y lanza PeajeException si corresponde
            if (f.buscarPropietarioPorCedula(prop1.getCedula()) == null) f.agregarPropietario(prop1);
            if (f.buscarPropietarioPorCedula(prop2.getCedula()) == null) f.agregarPropietario(prop2);
        } catch (PeajeException e) {
            // imprimir para debug, no interrumpir arranque
            e.printStackTrace();
        }

        // DEBUG: resumen rápido
        try {
            int nProps = f.getPropietarios() == null ? 0 : f.getPropietarios().size();
            int nPuestos = f.getPuestos() == null ? 0 : f.getPuestos().size();
            int nTarifas = f.getTarifas() == null ? 0 : f.getTarifas().size();
            int nBon = f.getBonificaciones() == null ? 0 : f.getBonificaciones().size();
            int nCats = f.getCategorias() == null ? 0 : f.getCategorias().size();
            int nEst = f.getEstados() == null ? 0 : f.getEstados().size();
            System.out.println("[DEBUG] Precarga: propietarios=" + nProps + " puestos=" + nPuestos + " tarifas=" + nTarifas + " bonificaciones=" + nBon + " categorias=" + nCats + " estados=" + nEst);
        } catch (Exception e) {
            // si alguno de los getters no existe, mostrar al menos la confirmación de ejecución
            System.out.println("[DEBUG] Precarga ejecutada (no se pudo leer conteos desde Fachada).");
        }
    }
}