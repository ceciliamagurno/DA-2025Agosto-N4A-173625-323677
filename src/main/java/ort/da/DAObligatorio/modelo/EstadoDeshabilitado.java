package ort.da.DAObligatorio.modelo;

public class EstadoDeshabilitado extends Estado {

    public EstadoDeshabilitado() {
        super("Deshabilitado");
    }

    @Override
    public boolean permiteTransito() {
        return false; 
    }

    @Override
    public boolean puedeAgregarVehiculo(){
        return false;
    }
}