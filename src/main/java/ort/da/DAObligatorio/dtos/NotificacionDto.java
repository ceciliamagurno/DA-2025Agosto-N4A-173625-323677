package ort.da.DAObligatorio.dtos;

import ort.da.DAObligatorio.modelo.peajes.Notificacion;

public class NotificacionDto {
    public String fechaHora;
    public String mensaje;

    public NotificacionDto(Notificacion n) {
        if(n != null){
            this.fechaHora = n.getFechaHora().toString();
            this.mensaje = n.getMensaje();
        }
    }
       
    
    public NotificacionDto() {}

    public String getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
