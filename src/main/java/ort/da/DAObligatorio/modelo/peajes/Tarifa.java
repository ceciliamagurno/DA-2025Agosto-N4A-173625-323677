package ort.da.DAObligatorio.modelo.peajes;

import ort.da.DAObligatorio.modelo.vehiculos.CategoriaVehiculo;

public class Tarifa {
    
    private double monto;
    private Puesto puesto;
    private CategoriaVehiculo categoria;
    

    public Tarifa( double monto, Puesto puesto, CategoriaVehiculo categoria) {
        this.monto = monto;
        this.puesto = puesto;
        this.categoria = categoria;
        
    }


    public double getMonto() {
        return monto;
    }


    public Puesto getPuesto() {
        return puesto;
    }


    public CategoriaVehiculo getCategoria() {
        return categoria;
    }

  



}
