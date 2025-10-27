package ort.da.DAObligatorio.modelo;

public class Notificacion {
    
    private String mensaje;
    private Propietario propietario;
    
    public Notificacion(String mensaje, Propietario propietario) {
        this.mensaje = mensaje;
        this.propietario = propietario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    
}
