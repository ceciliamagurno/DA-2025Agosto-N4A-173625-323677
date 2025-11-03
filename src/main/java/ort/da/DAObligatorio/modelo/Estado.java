package ort.da.DAObligatorio.modelo;

public abstract class Estado {

    private String nombre;

    public Estado(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
// Por defecto permito que el auto transite y agregue vehiculo para el propietario
    public boolean permiteTransito() {
        return true; 
    }
    
    public boolean puedeAgregarVehiculo(){
        return true;
    }

    @Override
    public String toString(){
        return nombre;
    }
}