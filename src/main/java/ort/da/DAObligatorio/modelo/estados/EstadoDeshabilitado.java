package ort.da.DAObligatorio.modelo.estados;


public class EstadoDeshabilitado extends Estado {

    @Override
    public boolean permiteLogin() {
        return false;
    }

    @Override 
    public boolean permiteTransito() {
        return false;
    }
}