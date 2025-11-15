package ort.da.DAObligatorio.modelo.peajes;

import ort.da.DAObligatorio.modelo.usuarios.Propietario;

public class RegistroNotificacion {

    private Propietario propietario;
    private Notificacion notificacion;
    
    public RegistroNotificacion(Propietario propietario, Notificacion notificacion) {
        this.propietario = propietario;
        this.notificacion = notificacion;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }
}
