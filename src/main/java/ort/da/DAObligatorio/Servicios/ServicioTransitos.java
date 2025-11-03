package ort.da.DAObligatorio.servicios;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.Bonificacion;
import ort.da.DAObligatorio.modelo.Puesto;
import ort.da.DAObligatorio.modelo.Propietario;
import ort.da.DAObligatorio.modelo.Tarifa;
import ort.da.DAObligatorio.modelo.Transito;
import ort.da.DAObligatorio.modelo.Vehiculo;
import ort.da.DAObligatorio.servicios.fachada.Fachada;

public class ServicioTransitos {
    
    private List<Transito> transitos;

    public ServicioTransitos() {
        this.transitos = new ArrayList<>();
    }

    public boolean registrarTransito(String matricula, String nombrePuesto) throws PeajeException{
        Fachada f = Fachada.getInstancia();

        // 1) verificar vehículo
        Vehiculo vehiculo = f.buscarVehiculoPorMatricula(matricula);
        if (vehiculo == null) {
            throw new PeajeException("El vehiculo con matricula " + matricula + " no esta registrado.");
        }

        // 2) verificar puesto
        Puesto puesto = f.buscarPuestoPorNombre(nombrePuesto);
        if (puesto == null) {
            throw new PeajeException("El puesto con nombre " + nombrePuesto + " no esta registrado.");
        }

        // 3) buscar propietario
        Propietario propietario = f.buscarPropietarioPorMatricula(matricula);
        if (propietario == null) {
            throw new PeajeException("No se encontró propietario asociado a la matrícula " + matricula);
        }

        // 4) buscar tarifa para puesto y categoría de vehículo
        Tarifa tarifa = f.buscarTarifa(puesto, vehiculo.getCategoria());
        if (tarifa == null) {
            throw new PeajeException("No existe tarifa para el puesto " + puesto.getNombre() + " y la categoría del vehículo.");
        }

        // 5) calcular monto aplicando bonificaciones conocidas (si existen)
        double montoBase = tarifa.getMonto();
        double montoFinal = montoBase;


        //TOAVIA NO HAY STRATEGY EN BONIFICACIONES
        List<Bonificacion> bns = propietario.getBonificaciones();
        if (bns != null) {
            for (Bonificacion b : bns) {
                String nombre = b.getNombre() == null ? "" : b.getNombre().trim();
                if ("Exonerados".equalsIgnoreCase(nombre)) {
                    montoFinal = 0.0;
                    break; // sin cobrar
                }
                if ("Frecuentes".equalsIgnoreCase(nombre)) {
                    // si ya hubo al menos un tránsito hoy por este puesto con este vehículo, aplicar 50% de descuento
                    int contHoy = contarTransitosMismoDiaPropietarioVehiculoPuesto(propietario, vehiculo, puesto, LocalDate.now());
                    if (contHoy >= 1) {
                        montoFinal = montoFinal * 0.5;
                    }
                }
                if ("Trabajadores".equalsIgnoreCase(nombre)) {
                    // 80% descuento en día de semana
                    DayOfWeek dw = LocalDate.now().getDayOfWeek();
                    if (dw != DayOfWeek.SATURDAY && dw != DayOfWeek.SUNDAY) {
                        montoFinal = montoFinal * 0.2; // paga 20%
                    }
                }
            }
        }

        // 6) intentar descontar del saldo del propietario
        boolean descontado = propietario.descontar(montoFinal);
        if (!descontado) {
            // crear notificación y lanzar excepción de negocio
            f.CrearNotificacion("Saldo insuficiente para tránsito en " + puesto.getNombre(), propietario);
            throw new PeajeException("Saldo insuficiente. Se necesita: " + montoFinal);
        }

        // 7) crear y almacenar el tránsito
        String id = UUID.randomUUID().toString();
        Transito tr = new Transito(id);
        tr.getVehiculos().add(vehiculo);
        tr.getPuestos().add(puesto);
        tr.getTarifas().add(tarifa);
        this.transitos.add(tr);

        // 8) notificar si quedó por debajo del mínimo
        try {
            double saldoActual = propietario.getSaldoActual();
            double minimo = propietario.getSaldoMininoAlerta();
            if (saldoActual < minimo) {
                f.CrearNotificacion("Saldo por debajo del mínimo tras tránsito en " + puesto.getNombre(), propietario);
            }
        } catch (Exception ex) {
            // algunos getters pueden tener nombres distintos; ignorar errores de notificación silenciosamente
        }

        return true;
    }

    private int contarTransitosMismoDiaPropietarioVehiculoPuesto(Propietario propietario, Vehiculo vehiculo, Puesto puesto, LocalDate dia) {
        int cnt = 0;
        for (Transito t : this.transitos) {
            // criterio simple: existe el vehiculo y el puesto en las listas del tránsito y la fecha reciente no se registra en Transito (no hay fecha),
            // por ahora contamos por coincidencia de id/colecciones, si tu Transito tuviera fecha usarla.
            if (t.getVehiculos().stream().anyMatch(v -> v.getMatricula().equalsIgnoreCase(vehiculo.getMatricula()))
                    && t.getPuestos().stream().anyMatch(p -> p.getNombre().equalsIgnoreCase(puesto.getNombre()))) {
                cnt++;
            }
        }
        return cnt;
    }

}
