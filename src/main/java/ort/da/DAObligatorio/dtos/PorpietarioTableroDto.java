package ort.da.DAObligatorio.dtos;

import java.util.ArrayList;
import java.util.List;

public class PorpietarioTableroDto {
    private String nombreCompleto;
    private String estado;
    private double saldoActual;
    private double saldoMinimoAlerta;
    private int cantidadVehiculos;
    private int cantidadTransitos;

    private List<String> matriculas = new ArrayList<String>();
    private List<TransitoDto> transitos = new ArrayList<TransitoDto>();
    private List<BonificacionDto> bonificaciones = new ArrayList<BonificacionDto>();
    private List<NotificacionDto> notificaciones = new ArrayList<NotificacionDto>();




    
    public PorpietarioTableroDto(String nombreCompleto, String estado, double saldoActual, double saldoMinimoAlerta,
            int cantidadVehiculos, int cantidadTransitos, List<String> matriculas, List<TransitoDto> transitos,
            List<BonificacionDto> bonificaciones, List<NotificacionDto> notificaciones) {
        this.nombreCompleto = nombreCompleto;
        this.estado = estado;
        this.saldoActual = saldoActual;
        this.saldoMinimoAlerta = saldoMinimoAlerta;
        this.cantidadVehiculos = cantidadVehiculos;
        this.cantidadTransitos = cantidadTransitos;
        this.matriculas = matriculas;
        this.transitos = transitos;
        this.bonificaciones = bonificaciones;
        this.notificaciones = notificaciones;
    }

    public PorpietarioTableroDto() {}
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public double getSaldoMinimoAlerta() {
        return saldoMinimoAlerta;
    }

    public void setSaldoMinimoAlerta(double saldoMinimoAlerta) {
        this.saldoMinimoAlerta = saldoMinimoAlerta;
    }

    public int getCantidadVehiculos() {
        return cantidadVehiculos;
    }

    public void setCantidadVehiculos(int cantidadVehiculos) {
        this.cantidadVehiculos = cantidadVehiculos;
    }

    public int getCantidadTransitos() {
        return cantidadTransitos;
    }

    public void setCantidadTransitos(int cantidadTransitos) {
        this.cantidadTransitos = cantidadTransitos;
    }

    public List<String> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<String> matriculas) {
        this.matriculas = matriculas;
    }

    public List<TransitoDto> getTransitos() {
        return transitos;
    }

    public void setTransitos(List<TransitoDto> transitos) {
        this.transitos = transitos;
    }

    public List<BonificacionDto> getBonificaciones() {
        return bonificaciones;
    }

    public void setBonificaciones(List<BonificacionDto> bonificaciones) {
        this.bonificaciones = bonificaciones;
    }

    public List<NotificacionDto> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<NotificacionDto> notificaciones) {
        this.notificaciones = notificaciones;
    }
}
