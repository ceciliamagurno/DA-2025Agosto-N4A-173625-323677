package ort.da.DAObligatorio.modelo;

public class Administrador {
    private String cedula;
    private String password;
    private String nombreCompleto;

    public Administrador(String cedula, String password, String nombreCompleto) {
        this.cedula = cedula;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
    }

    public String getCedula() {
        return cedula;
    }



    public String getPassword() {
        return password;
    }


    public String getNombreCompleto() {
        return nombreCompleto;
    }



    

}
