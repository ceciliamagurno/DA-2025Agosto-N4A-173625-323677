package ort.da.DAObligatorio.modelo;

public abstract class Usuario {

    private String cedula;
    private String contrasenia;
    private String nombreCompleto;

    public Usuario(String cedula, String contrasenia, String nombreCompleto) {
        this.cedula = cedula;
        this.contrasenia = contrasenia;
        this.nombreCompleto = nombreCompleto;
    }

    public boolean verificarContrasenia(String contrasenia) {
        return this.contrasenia.equals(contrasenia);
    }

    public boolean coincideCedula(String cedula){
        if(cedula == null) return false;
        return cedula.equals(this.cedula);
    }

    public String getCedula() {
        return cedula;
    }

    @Override
    public String toString() {
        return nombreCompleto;
    }
}
