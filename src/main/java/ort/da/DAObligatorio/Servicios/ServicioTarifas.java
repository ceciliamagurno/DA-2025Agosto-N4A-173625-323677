package ort.da.DAObligatorio.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.modelo.CategoriaVehiculo;
import ort.da.DAObligatorio.modelo.Puesto;
import ort.da.DAObligatorio.modelo.Tarifa;

public class ServicioTarifas {
    
    private List<Tarifa> tarifas;

    public ServicioTarifas() {
        this.tarifas = new ArrayList<>();
    }

    public void agregarTarigfa(Tarifa t){
        if(tarifas.contains(t) || t == null) return;
        tarifas.add(t);
    }

    public List<Tarifa> obtenerTarifas(){
        return tarifas;
    }

    public Tarifa buscarTarifa(Puesto p, CategoriaVehiculo cv){
        for(Tarifa t : tarifas){
            if(t.tienePuesto(p) && t.tieneCategoriaVehiculo(cv)){
                return t;
            }
        }
        return null;
    }

    public void crearTarifa(Puesto p, CategoriaVehiculo cv, double monto){
        Tarifa t = buscarTarifa(p, cv);
        if(t != null) return;

        Tarifa nuevaTarifa = new Tarifa(monto);
        nuevaTarifa.agregarPuesto(p);
        nuevaTarifa.agregarCategoriaVehiculo(cv);
        agregarTarigfa(nuevaTarifa);
    }



}
