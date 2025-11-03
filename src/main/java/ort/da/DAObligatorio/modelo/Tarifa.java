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

public void agregarPuesto(Puesto p){
        if(puestos.contains(p) || p == null) return;
        puestos.add(p);
    }

    public void agregarCategoriaVehiculo(CategoriaVehiculo cv){
        if(categoriasVehiculos.contains(cv) || cv == null) return;
        categoriasVehiculos.add(cv);
    }

    public boolean tienePuesto(Puesto p){
        return puestos.contains(p);
    }

    public boolean tieneCategoriaVehiculo(CategoriaVehiculo cv){
        return categoriasVehiculos.contains(cv);
    }



}
