package ort.da.DAObligatorio.modelo.bonificaciones;

public class Exonerado implements ReglaBonificacion {

    @Override
    public double aplicar(ort.da.DAObligatorio.modelo.peajes.Tarifa tarifa, 
                         ort.da.DAObligatorio.modelo.usuarios.Propietario propietario, 
                         ort.da.DAObligatorio.modelo.vehiculos.Vehiculo vehiculo, 
                         ort.da.DAObligatorio.modelo.peajes.Puesto puesto, 
                         java.time.LocalDateTime fechaHora) {
        return 0;
    }
    
}
