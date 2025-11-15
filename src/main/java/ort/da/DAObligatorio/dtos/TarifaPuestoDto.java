package ort.da.DAObligatorio.dtos;

public class TarifaPuestoDto {

    private String categoria;
    private double monto;

    public TarifaPuestoDto() {}

    public TarifaPuestoDto(String categoria, double monto) {
        this.categoria = categoria;
        this.monto = monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}