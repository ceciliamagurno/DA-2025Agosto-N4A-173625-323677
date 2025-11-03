package ort.da.DAObligatorio.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.modelo.Puesto;

public class ServicioPuestos {
    
    private List<Puesto> puestos;

    public ServicioPuestos() {
        this.puestos = new ArrayList<>();
    }

    public void agregarPuesto(Puesto p){
        if(p == null) return;
        puestos.add(p);
    }

    public List<Puesto> obtenerPuestos(){
        return puestos;
    }

    public Puesto buscarPuestoPorNombre(String nombre){
        if(nombre ==null || nombre.isEmpty())return null;
        for(Puesto p: puestos){
            if(p.getNombre().equals(nombre)){
                return p;
            }
        }
        return null;
    }
}
