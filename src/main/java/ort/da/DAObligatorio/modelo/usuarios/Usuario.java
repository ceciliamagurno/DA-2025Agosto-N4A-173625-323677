package ort.da.DAObligatorio.modelo.usuarios;

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
        return this.contrasenia != null && this.contrasenia.equals(contrasenia);
    }

    public boolean coincideCedula(String cedula){
        return this.cedula != null && this.cedula.equals(cedula);
    }

    public String getCedula() {
        return cedula;
    }

    
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    @Override
    public String toString() {
        return nombreCompleto;
    }
}
