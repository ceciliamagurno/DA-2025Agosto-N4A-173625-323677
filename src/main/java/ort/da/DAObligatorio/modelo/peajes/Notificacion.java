package ort.da.DAObligatorio.modelo.peajes;

import java.time.LocalDateTime;

public class Notificacion {
    
    private LocalDateTime fechaHora = LocalDateTime.now();
    private String mensaje;

    public Notificacion(String mensaje){
        this.mensaje=mensaje;
        this.fechaHora = LocalDateTime.now();
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getMensaje() {
        return mensaje;
    }
}
