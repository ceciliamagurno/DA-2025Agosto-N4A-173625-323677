package ort.da.DAObligatorio.modelo;

import java.util.Date;

public class AsignacionDeBonificacion {
    
    private Propietario propietario;
    private Bonificacion bonificacion;
    private Puesto puesto;
    private Date fechaAsignacion;

    public AsignacionDeBonificacion(Propietario cedula, Bonificacion bonificacion, Puesto puesto, Date fechaAsignacion) {
        this.propietario = cedula;
        this.bonificacion = bonificacion;
        this.puesto = puesto;
        this.fechaAsignacion = fechaAsignacion;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public Bonificacion getBonificacion() {
        return bonificacion;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    

}
