package ort.da.DAObligatorio.modelo.bonificaciones;

public class Bonificacion {

    private String nombre;
    private ReglaBonificacion regla;

    public Bonificacion(String nombre, ReglaBonificacion regla) {
        this.nombre = nombre;
        this.regla = regla;
    }

    public String getNombre() {
        return nombre;
    }

    public ReglaBonificacion getRegla() {
        return regla;
    }
    
}
