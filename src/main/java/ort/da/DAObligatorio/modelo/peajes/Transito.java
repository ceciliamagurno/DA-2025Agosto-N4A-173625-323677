package ort.da.DAObligatorio.modelo.peajes;

import java.time.LocalDateTime;



public class Transito {

    private LocalDateTime fechaHora;
    private String matricula;
    private Puesto puesto;
    private double  montoCobrado;
    private String bonificacionAplicada;

    public Transito(String matricula,
                    Puesto puesto,
                    LocalDateTime fechaHora,
                    double montoFinal,
                    String bonificacionAplicada) {
        this.matricula = matricula;
        this.puesto = puesto;
        this.fechaHora = fechaHora;
        this.montoCobrado = montoFinal;
        this.bonificacionAplicada = bonificacionAplicada;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getMatricula() {
        return matricula;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public double getMontoCobrado() {
        return montoCobrado;
    }

    public String getBonificacionAplicada() {
        return bonificacionAplicada;
    }

   
   
}
