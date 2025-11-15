package ort.da.DAObligatorio.modelo.usuarios;

import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.modelo.estados.*;
import ort.da.DAObligatorio.modelo.peajes.AsignacionDeBonificacion;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;

public class Propietario extends Usuario {
    
   private double saldo;
   private double saldoMinimoAlerta;

   private ArrayList<Vehiculo> vehiculos = new ArrayList<>();
   private ArrayList<AsignacionDeBonificacion> asignacionesBonificacion = new ArrayList<>();

   private Estado estado;


   public Propietario(String cedula, String contrasenia, String nombreCompleto,double saldo, double saldoMinimoAlerta) {
          super( cedula, contrasenia, nombreCompleto);
          this.saldo = saldo;
          this.saldoMinimoAlerta = saldoMinimoAlerta;
          this.estado = new EstadoHabilitado(); //por defecto
   }

   //cosas para estado
   public Estado getEstado() {
       return estado;
   }

   public void setEstado(Estado estado) {
       this.estado = estado;
   }

   //cosas para saldo

    public double getSaldo() {
         return saldo;
    }

    public void acreditar(double monto) {
         this.saldo += monto;
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



}



