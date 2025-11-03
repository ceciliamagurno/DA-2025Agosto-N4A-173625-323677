package ort.da.DAObligatorio.modelo;

public class EstadoHabilitado extends Estado {

    public EstadoHabilitado() {
        super("Habilitado");
    }

    @Override
    public boolean permiteTransito() {
        return true;
    }

    
}
