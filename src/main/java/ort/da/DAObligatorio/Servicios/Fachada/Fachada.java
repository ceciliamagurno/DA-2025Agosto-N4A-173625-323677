package ort.da.DAObligatorio.servicios.fachada;


import ort.da.DAObligatorio.modelo.bonificaciones.Bonificacion;
import ort.da.DAObligatorio.modelo.estados.Estado;
import ort.da.DAObligatorio.modelo.peajes.AsignacionDeBonificacion;
import ort.da.DAObligatorio.modelo.peajes.Notificacion;
import ort.da.DAObligatorio.modelo.peajes.Puesto;
import ort.da.DAObligatorio.modelo.peajes.Tarifa;
import ort.da.DAObligatorio.modelo.peajes.Transito;
import ort.da.DAObligatorio.modelo.usuarios.Administrador;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.usuarios.Usuario;
import ort.da.DAObligatorio.modelo.vehiculos.CategoriaVehiculo;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;
import ort.da.DAObligatorio.observador.Observable;

import java.time.LocalDateTime;
import java.util.List;

import ort.da.DAObligatorio.servicios.*;
import ort.da.DAObligatorio.excepciones.PeajeException;

public class Fachada extends Observable{
    
    private static Fachada instancia;
    //servicios
    private ServicioUsuarios sUsuarios;
    private ServicioVehiculos sVehiculos;
    private ServicioPuestos sPuestos;
    private ServicioTransitos sTransitos;
    private ServicioBonificaciones sBonificaciones;
    private ServicioTarifas sTarifas;
    private ServicioNotificaciones sNotificaciones;    
    private ServicioEstados sEstados;

    
    


    private Fachada() {
        this.sUsuarios = new ServicioUsuarios();
        this.sVehiculos = new ServicioVehiculos();
        this.sPuestos = new ServicioPuestos();
        this.sTransitos = new ServicioTransitos();
        this.sBonificaciones = new ServicioBonificaciones();
        this.sTarifas = new ServicioTarifas();
        this.sNotificaciones = new ServicioNotificaciones();
        this.sEstados = new ServicioEstados();
    
        this.agregarObservador(sNotificaciones);
    }

    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }



    //Servicio Usuarios 
    public Usuario login(String cedula, String contrasenia) {
        return sUsuarios.login(cedula, contrasenia);
    }

    public void agregarAdministrador(Administrador admin) {
        sUsuarios.agregarAdministrador(admin);
    }

    public void agregarPropietario(Propietario prop) {
        sUsuarios.agregarPropietario(prop);
    }

    public Propietario buscarPropietarioPorCedula(String cedula) {
        return sUsuarios.buscarPropietarioPorCedula(cedula);
    }

    public Administrador buscarAdministradorPorCedula(String cedula) {
        return sUsuarios.buscarAdministradorPorCedula(cedula);
    }

    public List<Propietario> listarPropietarios() {
        return sUsuarios.listarPropietarios();
    }

    public List<Administrador> listarAdministradores() {
        return sUsuarios.listarAdministradores();
    }

    public Propietario buscarPropietarioPorMatricula(String matricula) {
        return sUsuarios.buscarPropietarioPorMatricula(matricula);
    }
    





    //Servicio Estados
    public List<Estado> getEstadosDisponibles() {
        return sEstados.obtenerEstadosDisponibles();
    }

     public void cambiarEstadoPropietario(String cedula, String nombreEstado) throws PeajeException {
        if (cedula == null || nombreEstado == null) {
            throw new PeajeException("Debe indicar cédula y estado.");
        }

        Estado nuevoEstado = sEstados.obtenerEstadoPorNombre(nombreEstado);
        if (nuevoEstado == null) {
            throw new PeajeException("Estado inválido: " + nombreEstado);
        }

        // Reutilizamos el helper que trabaja con Estado ya resuelto
        cambiarEstadoPropietario(cedula, nuevoEstado);
    }

    private void cambiarEstadoPropietario(String cedula, Estado e) throws PeajeException {
        sUsuarios.cambiarEstadoPropietario(cedula, e);

        Propietario p = sUsuarios.buscarPropietarioPorCedula(cedula);
        if (p == null){
            return;
        }
        //armar evento para avisar a los observadores
        Object[] ev = new Object[3];
            ev[0] = p;
            ev[1] = "ESTADO_CAMBIADO";
            ev[2] = e.nombre();
            
            this.avisar(ev);
        
    }






    //Servicio Bonificaciones
    public List<Bonificacion> obtenerBonificaciones() {
        return sBonificaciones.obtenerBonificaciones();
    }

    public Bonificacion buscarBonificacion(String nombre) {
        return sBonificaciones.buscarBonificacionPorNombre(nombre);
    }

    public void agregarBonificacion(Bonificacion bn) {
        sBonificaciones.agregarBonificacion(bn);
    }

    public void asignarBonificacionAPropietario(Propietario propietario, Bonificacion b, Puesto p)
            throws PeajeException {
        sBonificaciones.asignarBonificacionAPropietario(propietario, b, p);

        //armo evento para avisar a los observadores
        Object[] ev = new Object[3];
        ev[0] = propietario;
        ev[1] = "ASIGNACION_BONIFICACION";
        ev[2] = b.getNombre();
    }

    public List<AsignacionDeBonificacion> obtenerAsignacionesPorPropietario(Propietario propietario) {
        return sBonificaciones.obtenerAsignacionesPorPropietario(propietario);
    }






    //Servicio Transitos

    public void agregarTransito(Transito transito){
        sTransitos.agregarTransito(transito);
    }

    public List<Transito> obtenerTransitos() {
        return sTransitos.getTransitos();
    }

    public Transito registrarTransito(Vehiculo vehiculo, Puesto puesto, Tarifa tarifa, Propietario propietario,
            List<AsignacionDeBonificacion> asignaciones, LocalDateTime fechaHora) throws PeajeException {
        return sTransitos.registrarTransito(vehiculo, puesto, tarifa, propietario, asignaciones, fechaHora);
    }

    public List<Transito> obtenerTransitosPorPropietario(Propietario p) {
        return sTransitos.obtenerTransitosPorPropietario(p);
    }   

    public RegistroResultadoTransito registrarTransito(
        String matricula,
        String nombrePuesto,
        LocalDateTime fechaHora) throws PeajeException {
        return sTransitos.registrarTransito(matricula, nombrePuesto, fechaHora);
    }

    public RegistroResultadoTransito registrarTransito(
        String matricula,
        String nombrePuesto) throws PeajeException {
        return sTransitos.registrarTransito(matricula, nombrePuesto, LocalDateTime.now());
    }



    //Servicio Vehiculos
    public List<CategoriaVehiculo> obtenerCategorias() {
        return sVehiculos.obtenerCategorias();
    }

    public void agregarCategoriaVehiculo(CategoriaVehiculo categoria) {
        sVehiculos.agregarCategoriaVehiculo(categoria);
    }

    public CategoriaVehiculo buscarCategoriaPorNombre(String nombre) {
        return sVehiculos.buscarCategoriaPorNombre(nombre);
    }

    public boolean agregarVehiculo(Vehiculo vehiculo) {
        return sVehiculos.agregarVehiculo(vehiculo);
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula) {
        return sVehiculos.buscarVehiculoPorMatricula(matricula);
    }





    //Servicio puestos

    public void agregarPuesto(Puesto p) {
        sPuestos.agregarPuesto(p);
    }

    public List<Puesto> getPuestos() {
        return sPuestos.obtenerPuestos();
    }

    public Puesto buscarPuestoPorNombre(String nombre) {
        return sPuestos.buscarPuestoPorNombre(nombre);
    }






    //Servicio Tarifas

    public void agregarTarifa(Tarifa t) {
        sTarifas.agregarTarifa(t);
    }

    public List<Tarifa> obtenerTarifas() {
        return sTarifas.obtenerTarifas();
    }

    public Tarifa buscarTarifa(Puesto p, CategoriaVehiculo cv) {
        return sTarifas.buscarTarifa(p, cv);
    }

    public void crearTarifa(Puesto p, CategoriaVehiculo cv, double monto) throws PeajeException {
        sTarifas.crearTarifa(p, cv, monto);
    }







    //Servicio Notificaciones
    public void agregarNotificacion(String msj, Propietario propietario) {
        if (msj == null || propietario == null) {
            return;
        }
        sNotificaciones.agregarNotificacion(msj, propietario);
    }

    public List<Notificacion> obtenerNotificacionesDelPropietario(Propietario propietario) {
        return sNotificaciones.obtenerNotificacionesDelPropietario(propietario);
    }

    public void borrarNotificacionesDelPropietario(Propietario propietario) {
        sNotificaciones.borrarNotificacionesDelPropietario(propietario);
    }

  public void enviarNotificacion(Propietario propietario, String mensaje) {
        if (propietario == null || mensaje == null) {
            return;
        }
        sNotificaciones.agregarNotificacion(mensaje, propietario);
    }


    

}
