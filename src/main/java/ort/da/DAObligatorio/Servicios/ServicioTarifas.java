package ort.da.DAObligatorio.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.peajes.Puesto;
import ort.da.DAObligatorio.modelo.peajes.Tarifa;
import ort.da.DAObligatorio.modelo.vehiculos.CategoriaVehiculo;

public class ServicioTarifas {
    
    private List<Tarifa> tarifas;

    public ServicioTarifas() {
        this.tarifas = new ArrayList<>();
    }

    public void agregarTarifa(Tarifa t){
        if( t == null) return;

        //busco duplicados por puesto y categoria
        Tarifa existente = buscarTarifa(t.getPuesto(), t.getCategoria());
        if(existente != null) return;
        tarifas.add(t);
    }

    public List<Tarifa> obtenerTarifas(){
        return new ArrayList<Tarifa>(tarifas);
    }

    public Tarifa buscarTarifa(Puesto p, CategoriaVehiculo cv){
        if(p == null || cv == null) return null;
        
        for(Tarifa t : tarifas){
            Puesto tp= t.getPuesto();
            CategoriaVehiculo tcv = t.getCategoria();

            if(tp !=null && tcv != null && tp.getNombre() != null
            && tp.getNombre().equalsIgnoreCase(p.getNombre()) && tcv == cv){
                return t;
            }
        }
        return null;
    }

    public void crearTarifa(Puesto p, CategoriaVehiculo cv, double monto) throws PeajeException{
        if(p == null || cv == null) {
            throw new PeajeException("Puesto o categoría de vehículo no validos para crear tarifa.");
        }

        Tarifa existente = buscarTarifa(p, cv);
        if(existente != null) return;

        Tarifa nueva = new Tarifa(monto, p, cv);
        agregarTarifa(nueva);
    }
}
