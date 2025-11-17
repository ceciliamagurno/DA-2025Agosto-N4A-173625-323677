package ort.da.DAObligatorio.modelo;

import java.time.LocalDateTime;

import ort.da.DAObligatorio.modelo.usuarios.Usuario;

public class Sesion {
   
    private LocalDateTime fechaHoraIngreso;
    private Usuario usuario;

    public Sesion(Usuario usuario) {
        this.usuario = usuario;
        this.fechaHoraIngreso = LocalDateTime.now();
    }

    public LocalDateTime getFechaHoraIngreso() {
        return fechaHoraIngreso;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
