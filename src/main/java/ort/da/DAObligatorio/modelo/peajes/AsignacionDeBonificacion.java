package ort.da.DAObligatorio.modelo.peajes;

import java.time.LocalDateTime;


import ort.da.DAObligatorio.modelo.bonificaciones.Bonificacion;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;


public class AsignacionDeBonificacion {
    
    private Propietario propietario;
    private Bonificacion bonificacion;
    private Puesto puesto;
    private LocalDateTime fechaAlta;

    public AsignacionDeBonificacion(Propietario propietario, 
                                    Bonificacion bonificacion, 
                                    Puesto puesto,
                                    LocalDateTime fechaAlta) {
        this.propietario = propietario;
        this.bonificacion = bonificacion;
        this.puesto = puesto;
        this.fechaAlta = fechaAlta;
        
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

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

}
