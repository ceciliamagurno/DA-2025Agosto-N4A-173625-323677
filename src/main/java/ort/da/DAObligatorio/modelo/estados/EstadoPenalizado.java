package ort.da.DAObligatorio.modelo.estados;

public class EstadoPenalizado extends Estado {
   
    @Override
    public boolean aplicaBonificaciones() {
        return false;
    }
}