package ort.da.DAObligatorio.dtos;

import ort.da.DAObligatorio.modelo.Vehiculo;

public class VehiculoDto {
    
    private String matricula;
    private String categoria;

    public VehiculoDto(Vehiculo v) {
        this.matricula = v.getMatricula();
        this.categoria =v.getCategoria().getNombre();
    }

    public String getMatricula() { return matricula; }

    public String getCategoria() { return categoria; }
}
