package ort.da.DAObligatorio.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.modelo.Notificacion;
import ort.da.DAObligatorio.modelo.Propietario;

public class ServicioNotificaciones {
    
    private List<Notificacion> notificaciones;

    public ServicioNotificaciones() {
        this.notificaciones = new ArrayList<>();
    }

    public void CrearNotificacion(String msj, Propietario p){
       //hacer validaciones
        Notificacion n = new Notificacion(msj, p );
        notificaciones.add(n);
    }

    public List<Notificacion> obtenerNotificaciones() {
        return notificaciones;
    }

}
