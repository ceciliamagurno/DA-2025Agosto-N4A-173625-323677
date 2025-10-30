package ort.da.DAObligatorio.modelo;

import java.util.Date;

public class Administrador extends Usuario {

    private Date fechaIngreso;
    private Date fechaSalida;
    
    public Administrador(String cedula, 
                        String contrasenia, 
                        String nombreCompleto,
                        Date fechaIngreso,
                        Date fechaSalida) {
        super(cedula, contrasenia, nombreCompleto);
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
    }

    @Override
    public boolean coincideCedula(String cedula) {
        return super.coincideCedula(cedula);
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    public Date getFechaSalida() {
        return fechaSalida;
    }
}
