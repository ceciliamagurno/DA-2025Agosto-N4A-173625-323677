package ort.da.DAObligatorio.modelo.usuarios;

import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.modelo.bonificaciones.Bonificacion;
import ort.da.DAObligatorio.modelo.estados.*;
import ort.da.DAObligatorio.modelo.peajes.AsignacionDeBonificacion;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;
import ort.da.DAObligatorio.servicios.RegistroResultadoTransito;
import ort.da.DAObligatorio.servicios.fachada.Fachada;

public class Propietario extends Usuario {
    
   private double saldo;
   private double saldoMinimoAlerta;

   private ArrayList<Vehiculo> vehiculos;
   private ArrayList<AsignacionDeBonificacion> asignacionesBonificacion;

   private Estado estado;


   public Propietario(String cedula, String contrasenia, String nombreCompleto,double saldo, double saldoMinimoAlerta) {
          super( cedula, contrasenia, nombreCompleto);
          this.saldo = saldo;
          this.saldoMinimoAlerta = saldoMinimoAlerta;
          this.estado = new EstadoHabilitado(); //por defecto
          this.vehiculos = new ArrayList<>();
          this.asignacionesBonificacion = new ArrayList<>();
   }

   //cosas para estado
   public Estado getEstado() {
       return estado;
   }

   public void cambiarEstado(Estado nuevoEstado) {
       this.estado = nuevoEstado;
       notificarEvento("ESTADO_CAMBIADO", nuevoEstado!= null ? nuevoEstado.nombre(): null);
   }



   //cosas para saldo

    public double getSaldo() {
         return saldo;
    }

    public void debitar(double monto) {
         this.saldo -= monto;
    }

    public double getSaldoMinimoAlerta() {
         return saldoMinimoAlerta;
    }

    public void setSaldoMinimoAlerta(double saldoMinimoAlerta) {
         this.saldoMinimoAlerta = saldoMinimoAlerta;
    }


    //cosas para vehiculos

    public void agregarVehiculo(Vehiculo v) {
         this.vehiculos.add(v);
    }

    public List<Vehiculo> getVehiculos() {
         return new ArrayList<>(vehiculos);
    }

    //cosas para bonificaciones

    public void agregarAsignacionBonificacion(AsignacionDeBonificacion ab) {
         if(ab != null && !asignacionesBonificacion.contains(ab)){
          asignacionesBonificacion.add(ab);
         }
    }

    public void notificarAsignacionBonificacion(Bonificacion bonificacion) {
         String nombreBonificacion = (bonificacion != null ? bonificacion.getNombre() : null);
         notificarEvento("ASIGNACION_BONIFICACION", nombreBonificacion);
    }
    
    
    
    public List<AsignacionDeBonificacion> getAsignacionesBonificacion() {
         return new ArrayList<AsignacionDeBonificacion>(asignacionesBonificacion);
    }

    public boolean poseeVehiculoConMatricula(String matricula) {
       for(Vehiculo v : vehiculos){
           if(v.getMatricula() != null && v.getMatricula().equalsIgnoreCase(matricula)){
               return true;
           }
       }
       return false;
     }

     public void notificarTransitoRegistrado(RegistroResultadoTransito registro){
           notificarEvento("TRANSITO_REGISTRADO", registro);
           try{
                if(this.saldo < this.saldoMinimoAlerta){
                     notificarEvento("SALDO_MINIMO_ALERTA", null);
                }
           }catch(Exception ex){
                    //ignoro
           }
     }


     private void notificarEvento(String evento, Object object) {
          Object[] ev = new Object[3];
          ev[0] = this;
          ev[1] = evento;
          ev[2] = object;

          Fachada.getInstancia().avisar(ev);
         
     }


}



