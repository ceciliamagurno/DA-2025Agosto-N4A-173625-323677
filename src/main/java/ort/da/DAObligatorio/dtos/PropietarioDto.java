package ort.da.DAObligatorio.dtos;

import java.util.List;

import ort.da.DAObligatorio.modelo.Bonificacion;
import ort.da.DAObligatorio.modelo.Estado;
import ort.da.DAObligatorio.modelo.Propietario;

public class PropietarioDto {
   
    private String nombreCompleto;  
    private Estado estado;
    private double saldoActual;
    private List<Bonificacion> bonificacionesDto;
    private List<VehiculoDto> vehiculosDto;
    private List<TransitoDto> transaccionesDto;
    private List<NotificacionDto> notificacionesDto;
    
    public PropietarioDto(Propietario p) {
        this.nombreCompleto = p.getNombreCompleto();
        this.estado = p.getEstado();
        this.saldoActual = p.getSaldoActual();
        this.bonificacionesDto = p.getBonificaciones();
        this.vehiculosDto = p.getVehiculos().stream()
                                    .map(v -> new VehiculoDto(v))
                                    .toList();
        this.transaccionesDto = null; // Implementar si es necesario
        this.notificacionesDto = null; // Implementar si es necesario
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public Estado getEstado() {
        return estado;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public List<Bonificacion> getBonificacionesDto() {
        return bonificacionesDto;
    }

    public List<VehiculoDto> getVehiculosDto() {
        return vehiculosDto;
    }

    public List<TransitoDto> getTransaccionesDto() {
        return transaccionesDto;
    }

    public List<NotificacionDto> getNotificacionesDto() {
        return notificacionesDto;
    }

    

    

}
