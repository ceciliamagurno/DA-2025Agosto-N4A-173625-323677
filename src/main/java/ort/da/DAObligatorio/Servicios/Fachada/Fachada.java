package ort.da.DAObligatorio.Servicios.Fachada;

import ort.da.DAObligatorio.modelo.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.Servicios.ServicioUsuarios;
import ort.da.DAObligatorio.excepciones.PeajeException;

public class Fachada {
    
    private static Fachada instancia;
    private ServicioUsuarios sUsuarios;
    // lISTAS PARA LA PRECARGA ESTAS LISTAS DEBEN ESTAR EN LOS SERVICIOS QUE AUN FALTAN REFACTORIZAR DESPUES
    private List<Puesto> puestos;
    private List<CategoriaVehiculo> categorias;
    private List <Tarifa> tarifas;
    private List<Bonificacion> bonificaciones;
    private List<Estado> estados;
    


    private Fachada() {
        // Inicialización de servicios y datos
        sUsuarios = new ServicioUsuarios();
        this.puestos = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.tarifas = new ArrayList<>();
        this.bonificaciones = new ArrayList<>();
        this.estados = new ArrayList<>();
    }

    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    public void agregarPropietario(Propietario propietario) throws PeajeException {
        sUsuarios.agregarPropietario(propietario);
    }

    public void agregarAdministrador(Administrador administrador) {
        sUsuarios.agregarAdministrador(administrador);
    }

    public Propietario buscarPropietarioPorMatricula(String matricula) {
        return sUsuarios.buscarPropietarioPorMatricula(matricula);
    }

    //UTILES PARA LA PRECARGA DE DATOS DESPUES PASASR A LOS SERVICIOS CORRESPONDIENTES
    public void agregarPuesto(Puesto p) { if (p != null) puestos.add(p); }
    public void agregarCategoriaVehiculo(CategoriaVehiculo c) { if (c != null) categorias.add(c); }
    public void agregarTarifa(Tarifa t) { if (t != null) tarifas.add(t); }
    public void agregarBonificacion(Bonificacion b) { if (b != null) bonificaciones.add(b); }
    public void agregarEstado(Estado e) { if (e != null) estados.add(e); }

    public List<Puesto> getPuestos() { return new ArrayList<>(puestos); }
    public List<CategoriaVehiculo> getCategorias() { return new ArrayList<>(categorias); }
    public List<Tarifa> getTarifas() { return new ArrayList<>(tarifas); }
    public List<Bonificacion> getBonificaciones() { return new ArrayList<>(bonificaciones); }
    public List<Estado> getEstados() { return new ArrayList<>(estados); }


    // búsqueda simple: tarifa por puesto y categoría (devuelve la primera que coincida)
    public Tarifa buscarTarifaParaPuestoYCategoria(Puesto puesto, CategoriaVehiculo categoria) {
        if (puesto == null || categoria == null) return null;
        for (Tarifa t : tarifas) {
            if (t.getPuestos().contains(puesto) && t.getCategoriasVehiculos().contains(categoria)) {
                return t;
            }
        }
        return null;
    }


}
