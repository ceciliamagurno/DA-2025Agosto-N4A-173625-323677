package ort.da.DAObligatorio.modelo;

import java.util.ArrayList;
import java.util.List;

public class Propietario extends Usuario {
    
    
    private double saldoActual;
    private double saldoMinimoAlerta;
    private List<Vehiculo> vehiculos;  
    private List<Bonificacion> bonificaciones;
    private Estado estado;

    //un constructor con lista de vehiculos y estado
    public Propietario(String cedula, 
                        String contrasenia,     
                        String nombreCompleto, 
                        double saldoActual, 
                        double saldoMinimoAlerta, 
                        List<Vehiculo> vehiculos, 
                        Estado estado) {
        super(cedula, contrasenia, nombreCompleto);
                this.saldoActual = saldoActual;
        this.saldoMinimoAlerta = saldoMinimoAlerta;
        this.vehiculos = (vehiculos == null) ? new ArrayList<>() : new ArrayList<>(vehiculos);
        this.bonificaciones = new ArrayList<>();
        this.estado = estado;
    }
    
    //otro constructor sin lista de vehiculos y estado
    public Propietario(String cedula, 
                        String contrasenia,     
                        String nombreCompleto, 
                        double saldoActual, 
                        double saldoMinimoAlerta) {
        super(cedula, contrasenia, nombreCompleto);
        this.saldoActual = saldoActual;
        this.saldoMinimoAlerta = saldoMinimoAlerta; 
        this.vehiculos = new ArrayList<>();
        this.bonificaciones = new ArrayList<>();
        this.estado = null;
    }



    public String getNombreCompleto() {
        return super.toString();
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public double getSaldoMininoAlerta() {
        return saldoMinimoAlerta;
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

    public Estado getEstado() {
        return estado;
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

    @Override
    public boolean coincideCedula(String cedula) {
        return  super.coincideCedula(cedula); 
    }







}



