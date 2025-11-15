package ort.da.DAObligatorio.modelo.estados;


public abstract class Estado {

    public boolean permiteLogin() {
        return true;
    }
    
    public boolean permiteTransito() {
        return true; 
    }
    
    public boolean aplicaBonificaciones() {
        return true;
    }
   
    public boolean puedeAgregarVehiculo(){
        return true;
    }

    public boolean permiteNotificaciones() {
        return true;
    }

    public String nombre(){
        return getClass().getSimpleName();
    }
}