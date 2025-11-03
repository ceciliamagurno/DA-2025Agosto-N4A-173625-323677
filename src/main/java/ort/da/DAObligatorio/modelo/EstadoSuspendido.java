package ort.da.DAObligatorio.modelo;

public class EstadoSuspendido extends Estado {

    public EstadoSuspendido() {
        super("Suspendido");
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
