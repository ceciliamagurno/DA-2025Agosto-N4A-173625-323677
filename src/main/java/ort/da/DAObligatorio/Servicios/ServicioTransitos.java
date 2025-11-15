package ort.da.DAObligatorio.servicios;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.bonificaciones.Bonificacion;
import ort.da.DAObligatorio.modelo.bonificaciones.ReglaBonificacion;
import ort.da.DAObligatorio.modelo.peajes.AsignacionDeBonificacion;
import ort.da.DAObligatorio.modelo.peajes.Puesto;
import ort.da.DAObligatorio.modelo.peajes.Tarifa;
import ort.da.DAObligatorio.modelo.peajes.Transito;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;
import ort.da.DAObligatorio.servicios.fachada.Fachada;

public class ServicioTransitos {
    
    private List<Transito> transitos;

    public ServicioTransitos() {
        this.transitos = new ArrayList<Transito>();
    }

    public List<Transito> getTransitos() {
        return transitos;
    }

    public void agregarTransito(Transito transito) {
        this.transitos.add(transito);
    }

    public RegistroResultadoTransito registrarTransito(String matricula,
                                                    String nombrePuesto,
                                                    LocalDateTime fechaHora) throws PeajeException {
        Fachada f = Fachada.getInstancia();
        //Busco el vehiculo
        Vehiculo vehiculo = f.buscarVehiculoPorMatricula(matricula);
        if(vehiculo == null) {
            throw new PeajeException("Vehiculo no encontrado"); 
        }

        //puesto
        Puesto puesto = f.buscarPuestoPorNombre(nombrePuesto);
        if(puesto == null) {
            throw new PeajeException("Puesto "+ nombrePuesto + " no encontrado");
        }

        //Propietario del vehiculo
        Propietario propietario = f.buscarPropietarioPorMatricula(matricula);
        if(propietario == null) {
            throw new PeajeException("Propietario del vehiculo no encontrado");
        }

        //restriccion por estado del propietario
        try{
            if(propietario.getEstado() == null || !propietario.getEstado().permiteTransito()){
                throw new PeajeException("El propietario en estado " + propietario.getEstado().nombre() + " no puede  transitar");
            }
        }catch (PeajeException e){
            throw new PeajeException("Error al verificar el estado del propietario: " + e.getMessage());
        }

        //tarifa segun puesto y categoria del vehiculo
        Tarifa tarifa = f.buscarTarifa(puesto, vehiculo.getCategoria());
        if(tarifa == null) {
            throw new PeajeException("Tarifa no encontrada para el puesto y categoria del vehiculo");   
        }

        double montoBase = tarifa.getMonto();
        double montoFinal = montoBase;
        String bonificacionAplicada = null;

        // 6) (Más adelante) acá irían las bonificaciones con Strategy.
        // Por ahora no aplicamos nada, dejamos montoFinal = montoBase.

        //validar saldo

        double saldoPropietario = propietario.getSaldo();
        if(saldoPropietario < montoFinal){
            throw new PeajeException("Saldo insuficiente en el propietario");
        }

        //debitar saldo
        propietario.debitar(montoFinal);

        //crear el transito y guardarlo
        Transito tr = new Transito(
            vehiculo.getMatricula(),
            puesto,
            montoFinal,
            bonificacionAplicada,
            fechaHora
        );
        this.transitos.add(tr);

         return new RegistroResultadoTransito(propietario, vehiculo, tarifa, montoFinal, bonificacionAplicada);
    }


    public RegistroResultadoTransito registrarTransito(String matricula,
                                                       String nombrePuesto) throws PeajeException {
        return registrarTransito(matricula, nombrePuesto, LocalDateTime.now());
    }



    public Transito registrarTransito(Vehiculo vehiculo,
                                      Puesto puesto,
                                      Tarifa tarifa,
                                      Propietario propietario,
                                      List<AsignacionDeBonificacion> asignaciones,
                                      LocalDateTime fechaHora)
                                      throws PeajeException {
        //Validaciones
        if(vehiculo == null) {
            throw new PeajeException("Vehiculo nulo");
        }
        if(puesto == null) {
            throw new PeajeException("Puesto nulo");
        }
        if(propietario == null) {
            throw new PeajeException("Propietario nulo");
        }
        if(tarifa == null) {
            throw new PeajeException("Tarifa nula");
        }
        if(fechaHora == null) {
            fechaHora = LocalDateTime.now();
        }
        //si estado del vehiculo es INACTIVO, no se puede registrar el transito
        if(!propietario.getEstado().permiteTransito()){
            throw new PeajeException("El propietario en estado " + propietario.getEstado().nombre() + " no puede  transitar");
        }

        //aplico bonificaciones
        String nombreBonificacionAplicada = "Sin bonificacion";
        double montoFinal = tarifa.getMonto();
        //recorro asignaciones para ver si alguna aplica
        for(AsignacionDeBonificacion aB : asignaciones){
            if(aB.getPuesto() != null &&
            aB.getPuesto().getNombre().equalsIgnoreCase(puesto.getNombre())) {
                
                //si aplica al mismo puesto, aplico la regla
                Bonificacion bon = aB.getBonificacion();
                ReglaBonificacion regla = bon.getRegla();

                double montoConRegla = regla.aplicar(tarifa,
                                                     propietario,
                                                     vehiculo,
                                                     puesto,
                                                     fechaHora);
                if(montoConRegla < montoFinal){
                    montoFinal = montoConRegla;
                    nombreBonificacionAplicada = bon.getNombre();
                }           
            }
        }
        //debito el monto final al propietario
        propietario.debitar(montoFinal);

        Transito transito = new Transito(vehiculo.getMatricula(),
                                         puesto,
                                         montoFinal,
                                         nombreBonificacionAplicada,
                                         fechaHora);
        transitos.add(transito);
        return transito;
    }   
 
    public List<Transito> obtenerTransitosPorPropietario(Propietario propietario) {
        List<Transito> resultado = new ArrayList<Transito>();
        if(propietario == null) {
            return resultado;
        }
        List<Vehiculo> vehiculosPropietario = propietario.getVehiculos();
        if(vehiculosPropietario == null || vehiculosPropietario.isEmpty()) {
            return resultado;
        }

        for(Transito t: transitos){
            String matriculaTransito = t.getMatricula();
            if(matriculaTransito != null){
                for(Vehiculo v : vehiculosPropietario){
                    if(matriculaTransito.equalsIgnoreCase(v.getMatricula())){
                        resultado.add(t);
                        break;
                    }
                }
            }
        }
        return resultado;
    }

}