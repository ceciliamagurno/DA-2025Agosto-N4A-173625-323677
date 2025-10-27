package ort.da.DAObligatorio.modelo;

public class Puesto {
    private String nombre;
    private String direccion;

    public Puesto(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    
}
