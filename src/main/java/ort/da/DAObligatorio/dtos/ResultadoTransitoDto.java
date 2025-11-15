package ort.da.DAObligatorio.dtos;

public class ResultadoTransitoDto {

    private String nombrePropietario;
    private String estadoPropietario;
    private double saldoPropietario;

    private double montoCobrado;
    private String bonificacionAplicada;
    private String categoriaVehiculo;

    public ResultadoTransitoDto() {}

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public String getEstadoPropietario() {
        return estadoPropietario;
    }

    public void setEstadoPropietario(String estadoPropietario) {
        this.estadoPropietario = estadoPropietario;
    }

    public double getSaldoPropietario() {
        return saldoPropietario;
    }

    public void setSaldoPropietario(double saldoPropietario) {
        this.saldoPropietario = saldoPropietario;
    }

    public double getMontoCobrado() {
        return montoCobrado;
    }

    public void setMontoCobrado(double montoCobrado) {
        this.montoCobrado = montoCobrado;
    }

    public String getBonificacionAplicada() {
        return bonificacionAplicada;
    }

    public void setBonificacionAplicada(String bonificacionAplicada) {
        this.bonificacionAplicada = bonificacionAplicada;
    }

    public String getCategoriaVehiculo() {
        return categoriaVehiculo;
    }

    public void setCategoriaVehiculo(String categoriaVehiculo) {
        this.categoriaVehiculo = categoriaVehiculo;
    }
}
