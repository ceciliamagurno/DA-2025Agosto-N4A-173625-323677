package ort.da.DAObligatorio.modelo.estados;


public class EstadoSuspendido extends Estado {  
    
    @Override 
    public boolean permiteTransito() {
        return false;
    }
}