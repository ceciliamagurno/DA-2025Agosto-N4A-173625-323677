package ort.da.DAObligatorio.modelo.bonificaciones;

import ort.da.DAObligatorio.modelo.peajes.Tarifa;

public class SinBonificacion implements ReglaBonificacion {

    @Override
    public double aplicar(Tarifa tarifa, 
                         ort.da.DAObligatorio.modelo.usuarios.Propietario propietario, 
                         ort.da.DAObligatorio.modelo.vehiculos.Vehiculo vehiculo, 
                         ort.da.DAObligatorio.modelo.peajes.Puesto puesto, 
                         java.time.LocalDateTime fechaHora) {
        return tarifa.getMonto();
    }
    
}
