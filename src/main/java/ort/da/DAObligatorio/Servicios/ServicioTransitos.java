package ort.da.DAObligatorio.servicios;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.bonificaciones.Bonificacion;
import ort.da.DAObligatorio.modelo.estados.Estado;
import ort.da.DAObligatorio.modelo.estados.EstadoDeshabilitado;
import ort.da.DAObligatorio.modelo.estados.EstadoSuspendido;
// import ort.da.DAObligatorio.modelo.bonificaciones.Bonificacion;
// import ort.da.DAObligatorio.modelo.bonificaciones.ReglaBonificacion;
// import ort.da.DAObligatorio.modelo.peajes.AsignacionDeBonificacion;
import ort.da.DAObligatorio.modelo.peajes.Puesto;
import ort.da.DAObligatorio.modelo.peajes.Tarifa;
import ort.da.DAObligatorio.modelo.peajes.Transito;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;


public class ServicioTransitos {
    
    private List<Transito> transitos;
    private ServicioVehiculos sVehiculos;
    private ServicioUsuarios sUsuarios;
    private ServicioPuestos sPuestos;
    private ServicioTarifas sTarifas;
    private ServicioBonificaciones sBonificaciones;

    public ServicioTransitos(ServicioVehiculos sVehiculos,
                             ServicioUsuarios sUsuarios,
                                ServicioPuestos sPuestos,
                             ServicioTarifas sTarifas,
                             ServicioBonificaciones sBonificaciones) {
        this.transitos = new ArrayList<Transito>();
        this.sVehiculos = sVehiculos;
        this.sUsuarios =sUsuarios;
        this.sPuestos = sPuestos;
        this.sTarifas = sTarifas;
        this.sBonificaciones = sBonificaciones;
    }

    public List<Transito> getTransitos() {
        return transitos;
    }

    public void agregarTransito(Transito transito) {
        this.transitos.add(transito);
    }

    public void serServicioBonificaciones(ServicioBonificaciones sBonificaciones) {
        this.sBonificaciones = sBonificaciones;
    }

    public RegistroResultadoTransito registrarTransito(String matricula,
                                                    String nombrePuesto,
                                                    LocalDateTime fechaHora) throws PeajeException {
        
        //vehiculos
        Vehiculo vehiculo = sVehiculos.buscarVehiculoPorMatricula(matricula);
        if(vehiculo == null) {
            throw new PeajeException("No existe el vehículo");
        }

        //puesto
        Puesto puesto = sPuestos.buscarPuestoPorNombre(nombrePuesto);
        if(puesto == null) {
            throw new PeajeException("Puesto: "+ nombrePuesto+" no encontrado");
        }

        //propieatrio
        Propietario propietario = sUsuarios.buscarPropietarioPorMatricula(matricula);
        if(propietario == null) {
            throw new PeajeException("Propietario no encontrado");
        }

        //Estado
        Estado estado = propietario.getEstado();
        if(estado == null){
            throw new PeajeException("No se permiten transitos.");
        }

        if(estado instanceof EstadoDeshabilitado){
            throw new PeajeException("El propietario del vehículo está deshabilitado, no puede realizar tránsitos");
        }

        if(estado instanceof EstadoSuspendido){
            throw new PeajeException("El propietario del vehículo está suspendido, no puede realizar tránsitos");
        }

        //tarifa
        Tarifa tarifa = sTarifas.buscarTarifa(puesto, vehiculo.getCategoria());
        if(tarifa == null) {
            throw new PeajeException("No existe tarifa para el puesto " + nombrePuesto + 
                                    " y la categoria " + vehiculo.getCategoria());
        }

        double montoBase = tarifa.getMonto();
        double montoFinal = montoBase;
        String bonificacionAplicada = null; 
        
       // 3) Obtener la bonificación aplicable, si el estado la permite
        List<Transito> transitosDelPropietario = obtenerTransitosPorPropietario(propietario);

        Bonificacion bonif = null;
        // Si el estado del propietario permite bonificaciones, calcularlas
        if(estado.aplicaBonificaciones()){
            bonif = sBonificaciones.obtenerBonificacionAplicable(
                propietario,
                puesto,
                vehiculo,
                fechaHora,
                transitosDelPropietario
            );

            if (bonif != null) {
            
            montoFinal = sBonificaciones.calcularMontoConBonificacion(
                bonif,
                tarifa,
                propietario,
                vehiculo,
                puesto,
                fechaHora
            );
            bonificacionAplicada = bonif.getNombre();
            }
        }



        //saldo
        if(propietario.getSaldo() < montoFinal) {
            throw new PeajeException("Saldo insuficiente: " + Math.round(propietario.getSaldo()));
        }

        //debitar
        propietario.debitar(montoFinal);

        //Crear y guardar transito
        Transito tr = new Transito(vehiculo.getMatricula(),puesto,fechaHora,montoFinal,bonificacionAplicada);
        agregarTransito(tr);
        
        RegistroResultadoTransito resultado = new RegistroResultadoTransito(propietario,vehiculo,tarifa, montoFinal,bonificacionAplicada);
       
        if(estado.permiteNotificaciones()){
            propietario.notificarTransitoRegistrado(resultado);
        }


        return resultado;
    }

 
    public List<Transito> obtenerTransitosPorPropietario(Propietario propietario) {
        List<Transito> resultado = new ArrayList<Transito>();
        
        if(propietario == null) {
            return resultado;
        }
       

        for(Transito t: transitos){
            if(t !=null && t.getMatricula() != null && propietario.poseeVehiculoConMatricula(t.getMatricula())){
                resultado.add(t);
            }
        }


        return resultado;
    }

}