package ort.da.DAObligatorio.modelo;

import java.util.ArrayList;
import java.util.List;

public class Propietario {
    
    private String cedula;
    private String password;
    private String nombreCompleto;
    private List<Vehiculo> vehiculos;  
    private List<Bonificacion> bonificaciones;
    private List<Estado> estados;

    public Propietario(String cedula, String password, String nombreCompleto, ArrayList<Vehiculo> vehiculos) {
        this.cedula = cedula;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.vehiculos = new ArrayList<>();
        this.bonificaciones = new ArrayList<>();
        this.estados = new ArrayList<>();
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public List<Bonificacion> getBonificaciones() {
        return bonificaciones;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public String getCedula() {
        return cedula;
    }


    
}
