package ort.da.DAObligatorio.servicios;

import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;
import ort.da.DAObligatorio.modelo.peajes.Tarifa;

public class RegistroResultadoTransito {

    private Propietario propietario;
    private Vehiculo vehiculo;
    private Tarifa tarifa;
    private double montoFinal;
    private String bonificacionAplicada;

    public RegistroResultadoTransito(Propietario propietario,
                                     Vehiculo vehiculo,
                                     Tarifa tarifa,
                                     double montoFinal,
                                     String bonificacionAplicada) {
        this.propietario = propietario;
        this.vehiculo = vehiculo;
        this.tarifa = tarifa;
        this.montoFinal = montoFinal;
        this.bonificacionAplicada = bonificacionAplicada;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public double getMontoFinal() {
        return montoFinal;
    }

    public String getBonificacionAplicada() {
        return bonificacionAplicada;
    }
}
