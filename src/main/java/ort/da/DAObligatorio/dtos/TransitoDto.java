package ort.da.DAObligatorio.dtos;


import java.time.format.DateTimeFormatter;

import ort.da.DAObligatorio.modelo.peajes.Transito;

public class TransitoDto {
    public String fechaHora;
    public String puesto;
    public String matricula;
    public String bonificacion;
    public double montoFinal;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    public TransitoDto(Transito transito){
        this.fechaHora = transito.getFechaHora().format(FORMATTER);
        this.puesto = transito.getPuesto().getNombre();
        this.matricula = transito.getMatricula();
        this.bonificacion = transito.getBonificacionAplicada();
        this.montoFinal = transito.getMontoCobrado();
    }

    public TransitoDto(){}

    
    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(String bonificacion) {
        this.bonificacion = bonificacion;
    }

    public double getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(double montoFinal) {
        this.montoFinal = montoFinal;
    }


    
}
