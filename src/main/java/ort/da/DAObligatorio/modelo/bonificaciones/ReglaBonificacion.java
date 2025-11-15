package ort.da.DAObligatorio.modelo.bonificaciones;

import java.time.LocalDateTime;

import ort.da.DAObligatorio.modelo.peajes.Puesto;
import ort.da.DAObligatorio.modelo.peajes.Tarifa;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;

public interface ReglaBonificacion {
    
    double aplicar(Tarifa tarifa,
                    Propietario propietario,
                    Vehiculo vehiculo,
                    Puesto puesto,
                    LocalDateTime fechaHora);
}
