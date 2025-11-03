package ort.da.DAObligatorio.modelo;

public class EstadoPenalizado extends Estado {
    
    public EstadoPenalizado() {
        super("Penalizado");
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
