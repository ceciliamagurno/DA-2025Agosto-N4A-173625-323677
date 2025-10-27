package ort.da.DAObligatorio.modelo;

import java.util.ArrayList;
import java.util.List;

public class Transito {

    private String id;
    private List<Vehiculo> vehiculos;
    private List<Puesto> puestos;
    private List<Tarifa> tarifas;

    public Transito(String id) {
        this.id = id;
        this.vehiculos = new ArrayList<>();
        this.puestos = new ArrayList<>();
        this.tarifas = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public List<Puesto> getPuestos() {
        return puestos;
    }

    public List<Tarifa> getTarifas() {
        return tarifas;
    }

    
    
}
