package ort.da.DAObligatorio.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ort.da.DAObligatorio.modelo.peajes.AsignacionDeBonificacion;

public class AsignacionBonificacionDto {

    public String nombreBonificacion;
    public String nombrePuesto;
    public String fechaAlta;

    public AsignacionBonificacionDto(AsignacionDeBonificacion a) {
        if (a != null) {
            if (a.getBonificacion() != null) this.nombreBonificacion = a.getBonificacion().getNombre();
            if (a.getPuesto() != null) this.nombrePuesto = a.getPuesto().getNombre();
            LocalDateTime f = a.getFechaAlta();
            if (f != null) {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                this.fechaAlta = f.format(fmt);
            } else {
                this.fechaAlta = null;
            }
        }
    }

    public AsignacionBonificacionDto() {}

    public String getNombreBonificacion() { return nombreBonificacion; }
    public void setNombreBonificacion(String nombreBonificacion) { this.nombreBonificacion = nombreBonificacion; }

    public String getNombrePuesto() { return nombrePuesto; }
    public void setNombrePuesto(String nombrePuesto) { this.nombrePuesto = nombrePuesto; }

    public String getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(String fechaAlta) { this.fechaAlta = fechaAlta; }

}
