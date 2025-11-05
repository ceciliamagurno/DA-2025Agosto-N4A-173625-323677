package ort.da.DAObligatorio.servicios.fachada;

import ort.da.DAObligatorio.modelo.*;

import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.servicios.*;
//import ort.da.DAObligatorio.controladores.ControladorLogin;
import ort.da.DAObligatorio.excepciones.PeajeException;

public class Fachada {
    
    private static Fachada instancia;
    //servicios
    private ServicioUsuarios sUsuarios;
    private ServicioVehiculos sVehiculos;
    private ServicioPuestos sPuestos;
    private ServicioTransitos sTransitos;
    private ServicioBonificaciones sBonificaciones;
    private ServicioTarifas sTarifas;
    private ServicioNotificaciones sNotificaciones;    
    // lISTAS PARA LA PRECARGA ESTAS LISTAS DEBEN ESTAR EN LOS SERVICIOS QUE AUN FALTAN REFACTORIZAR DESPUES
    private List<Puesto> puestos;
    private List<CategoriaVehiculo> categorias;
    private List <Tarifa> tarifas;
    private List<Bonificacion> bonificaciones;
    private List<Estado> estados;
    
    


    private Fachada() {
        this.sUsuarios = new ServicioUsuarios();
        this.sVehiculos = new ServicioVehiculos();
        this.sPuestos = new ServicioPuestos();
        this.sTransitos = new ServicioTransitos();
        this.sBonificaciones = new ServicioBonificaciones();
        this.sTarifas = new ServicioTarifas();
        this.sNotificaciones = new ServicioNotificaciones();
        
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

 // Exponer servicios si algún código los necesita directamente
    public ServicioUsuarios getServicioUsuarios() { return sUsuarios; }
    public ServicioVehiculos getServicioVehiculos() { return sVehiculos; }
    public ServicioPuestos getServicioPuestos() { return sPuestos; }
    public ServicioTransitos getServicioTransitos() { return sTransitos; }
    public ServicioBonificaciones getServicioBonificaciones() { return sBonificaciones; }
    public ServicioTarifas getServicioTarifas() { return sTarifas; }
    public ServicioNotificaciones getServicioNotificaciones() { return sNotificaciones; }

    public void agregarPropietario(Propietario propietario) throws PeajeException {
        sUsuarios.agregarPropietario(propietario);
    }

    public void agregarAdministrador(Administrador administrador) {
        sUsuarios.agregarAdministrador(administrador);
    }

    public Propietario buscarPropietarioPorMatricula(String matricula) {
        return sUsuarios.buscarPropietarioPorMatricula(matricula);
    }

    
    public List<CategoriaVehiculo> obtenerCategorias() {
        return sVehiculos.obtenerCategorias();
    }

    public CategoriaVehiculo buscarCategoriaPorNombre(String nombre) {
        return sVehiculos.buscarCategoriaPorNombre(nombre);
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula) {
        return sVehiculos.buscarVehiculoPorMatricula(matricula);
    }

    public List<Propietario> listaPropietarios() {
        return sUsuarios.listaPropietarios();
    }

    public boolean registrarTransito(String matricula, String nombrePuesto) throws PeajeException {
        return sTransitos.registrarTransito(matricula, nombrePuesto);
    }

    public void agregarTarigfa(Tarifa t) {
        sTarifas.agregarTarigfa(t);
    }

    public List<Tarifa> obtenerTarifas() {
        return sTarifas.obtenerTarifas();
    }

    public List<Puesto> obtenerPuestos() {
        return sPuestos.obtenerPuestos();
    }

    public Puesto buscarPuestoPorNombre(String nombre) {
        return sPuestos.buscarPuestoPorNombre(nombre);
    }

    public void CrearNotificacion(String msj, Propietario p) {
        sNotificaciones.CrearNotificacion(msj, p);
    }

    public List<Notificacion> obtenerNotificaciones() {
        return sNotificaciones.obtenerNotificaciones();
    }

    public List<Bonificacion> obtenerBonificaciones() {
        return sBonificaciones.obtenerBonificaciones();
    }

    public void asignarBonificacionAPropietario(Propietario cedula, Bonificacion b, Puesto p) throws PeajeException {
        sBonificaciones.asignarBonificacionAPropietario(cedula, b, p);
    }

    public Tarifa buscarTarifa(Puesto p, CategoriaVehiculo cv) {
        return sTarifas.buscarTarifa(p, cv);
    }

    public void crearTarifa(Puesto p, CategoriaVehiculo cv, double monto) {
        sTarifas.crearTarifa(p, cv, monto);
    }

    

    public Usuario login(String cedula, String contrasenia) {
        return sUsuarios.login(cedula, contrasenia);
    }

    public Propietario buscarPropietarioPorCedula(String cedula) {
        return sUsuarios.buscarPropietarioPorCedula(cedula);
    }

    public void asignarBonificacionPropietarios(String cedula, Bonificacion b) throws PeajeException {
        sUsuarios.asignarBonificacionPropietarios(cedula, b);
    }

    public void cambiarEstadoPropietario(String cedula, Estado e) throws PeajeException {
        sUsuarios.cambiarEstadoPropietario(cedula, e);
    }

    public Administrador buscarAdministradorCedula(String cedula) {
        return sUsuarios.buscarAdministradorCedula(cedula);
    }

    public List<Propietario> getPropietarios() {
        return sUsuarios.listaPropietarios();
    }

    public List<Administrador> getAdministradores() {
        return sUsuarios.listaAdministradores();
    }

    // public ControladorLogin getServicioUsuarios() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'getServicioUsuarios'");
    // }

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
