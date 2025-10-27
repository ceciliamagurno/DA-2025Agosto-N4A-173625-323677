package ort.da.DAObligatorio.modelo;

import java.util.ArrayList;
import java.util.List;

public class Tarifa {
    
    private double monto;
    private List<Puesto> puestos;
    private List<CategoriaVehiculo> categoriasVehiculos;
    

    public Tarifa(double monto) {
        this.monto = monto;
        this.puestos = new ArrayList<>();
        this.categoriasVehiculos = new ArrayList<>();
    }

    public double getMonto() {
        return monto;
    }

    public List<Puesto> getPuestos() {
        return puestos;
    }

    public List<CategoriaVehiculo> getCategoriasVehiculos() {
        return categoriasVehiculos;
    }
}
