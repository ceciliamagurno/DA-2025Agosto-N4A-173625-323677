package ort.da.DAObligatorio.dtos;

import ort.da.DAObligatorio.modelo.peajes.AsignacionDeBonificacion;

public class BonificacionDto {
    public String nombre;
    public String puesto;
    public String tipoRegla;
    public String fechaAsignacion;

    public BonificacionDto(AsignacionDeBonificacion a) {
        if(a != null){
            this.nombre = a.getBonificacion().getNombre();
            this.puesto = a.getPuesto().getNombre();
            this.tipoRegla = a.getBonificacion().getNombre();
            this.fechaAsignacion = a.getFechaAlta().toString();
        }
    }

    public BonificacionDto(){}

    public String getNombre() {
        return nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public String getTipoRegla() {
        return tipoRegla;
    }
    
    public String getFechaAsignacion() {
        return fechaAsignacion;
    }

}
