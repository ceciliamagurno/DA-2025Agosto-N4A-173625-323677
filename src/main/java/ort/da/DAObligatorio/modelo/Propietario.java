package ort.da.DAObligatorio.modelo;

import java.util.ArrayList;
import java.util.List;

public class Propietario {
    
    private String cedula;
    private String password;
    private String nombreCompleto;
    private double saldoActual;
    private double saldoMininoAlerta;
    private List<Vehiculo> vehiculos;  
    private List<Bonificacion> bonificaciones;
    private Estado estado;

    //un constructor con lista de vehiculos y estado
    public Propietario(String cedula, String password, String nombreCompleto, 
                            double saldoActual, 
                            double saldoMinimoAlerta, 
                            List<Vehiculo> vehiculos, 
                            Estado estado) {
        this.cedula = cedula;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.saldoActual = saldoActual;
        this.saldoMininoAlerta = saldoMinimoAlerta;
        this.vehiculos = (vehiculos == null) ? new ArrayList<>() : new ArrayList<>(vehiculos);
        this.bonificaciones = new ArrayList<>();
        this.estado = estado;
    }
    
    //otro constructor sin lista de vehiculos y estado
    public Propietario(String cedula, String password, 
                            String nombreCompleto, 
                            double saldoActual, 
                            double saldoMinimoAlerta) {
        this.cedula = cedula;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.saldoActual = saldoActual;
        this.saldoMininoAlerta = saldoMinimoAlerta; 
        this.vehiculos = new ArrayList<>();
        this.bonificaciones = new ArrayList<>();
        this.estado = null;
    }


    public String getCedula() {
        return cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public double getSaldoMininoAlerta() {
        return saldoMininoAlerta;
    }   

    public List<Vehiculo> getVehiculos() {
        return new ArrayList<>(vehiculos);
    }

    public boolean tieneVehiculos() {
        return !vehiculos.isEmpty();
    }
    
    public void agregarVehiculo(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
    }

    public List<Bonificacion> getBonificaciones() {
        return bonificaciones;
    }

    public void agregarBonificacion(Bonificacion bonificacion) {
        this.bonificaciones.add(bonificacion);
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setEstado(String descripcion) {
        this.estado = new Estado(descripcion);
    }

    public boolean descontar(double monto) {
        if(monto <= 0) return false;
        if (saldoActual >= monto) {
            saldoActual -= monto;
            return true;
        }
        return false;
    }

    public void despositar(double monto){
        if(monto > 0) saldoActual += monto;
    }

    public boolean tieneVehiculoConMatricula(String matricula){
        if(matricula == null) return false;
        for(Vehiculo v : this.vehiculos){
            if(v !=null && matricula.equalsIgnoreCase(v.getMatricula())){
                return true;
            }
        }
        return false;
    }







}



