package ort.da.DAObligatorio.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ort.da.DAObligatorio.dtos.ResultadoTransitoDto;
import ort.da.DAObligatorio.dtos.TarifaPuestoDto;
import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.peajes.Puesto;
import ort.da.DAObligatorio.modelo.peajes.Tarifa;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.vehiculos.CategoriaVehiculo;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;
import ort.da.DAObligatorio.servicios.RegistroResultadoTransito;
import ort.da.DAObligatorio.servicios.fachada.Fachada;
import ort.da.DAObligatorio.utils.Respuesta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/transitos")
public class ControladorTransitos {

    private final Fachada f = Fachada.getInstancia();

    //listar puestos
    @GetMapping("/puestos")
    public List<Respuesta> obtenerPuestos() {
        List<Puesto> puestos = f.getPuestos();
        return Respuesta.lista(new Respuesta("puestos", puestos));
    }
    
    //listar tarifas de un puesto
    @GetMapping("/tarifas")
    public List<Respuesta> obtenerTarifasDePuesto(@RequestParam String nombrePuesto) {
        
        Puesto puesto = f.buscarPuestoPorNombre(nombrePuesto);
        if(puesto == null){
            return Respuesta.lista(new Respuesta("error", "Puesto no encontrado"));
        } 

        List<Tarifa> tarifas = f.obtenerTarifas();
        List<TarifaPuestoDto> resultado = new ArrayList<TarifaPuestoDto>();

        if(tarifas != null){
            for (Tarifa t : tarifas){
                if(t!= null && t.getPuesto() != null && 
                t.getPuesto().getNombre() != null && t.getPuesto().getNombre().equalsIgnoreCase(nombrePuesto)){
                    
                    TarifaPuestoDto dto = new TarifaPuestoDto();
                    
                    CategoriaVehiculo cat = t.getCategoria();
                    String nombreCat= (cat ==null) ? null : cat.name(); 
                    
                    dto.setCategoria(nombreCat);
                    dto.setMonto(t.getMonto());                 
                    
                    resultado.add(dto);
                }
            }
        }
        return Respuesta.lista(new Respuesta("Tarifas", resultado));
    }


    @PostMapping("path")
    public List<Respuesta> emularTransito(@RequestParam String nombrePuesto,
                                        @RequestParam String matricula,
                                        @RequestParam (required = false) String fechaHoraString) {
        LocalDateTime fechaHora;

        if(fechaHoraString == null || fechaHoraString.trim().isEmpty()){
            fechaHora = LocalDateTime.now();
        } else{
            try{
                fechaHora = LocalDateTime.parse(fechaHoraString.trim());
            }catch(Exception ex){
                return Respuesta.lista(
                    new Respuesta("mensaje", "Formato de fecha/hota icorrecta.")
                );
            }
        }

        try{
            RegistroResultadoTransito res =f.registrarTransito(matricula, nombrePuesto, fechaHora);

            ResultadoTransitoDto dto = new ResultadoTransitoDto();

            Propietario prop = res.getPropietario();
            if(prop != null){
            dto.setNombrePropietario(prop.getNombreCompleto());
            if(prop.getEstado() != null){
                dto.setEstadoPropietario(prop.getEstado().nombre());
            }
            dto.setSaldoPropietario(prop.getSaldo());
            }
            
            dto.setMontoCobrado(res.getMontoFinal());
            dto.setBonificacionAplicada(res.getBonificacionAplicada());

            Vehiculo veh = res.getVehiculo();
            if(veh != null && veh.getCategoria() != null){
                dto.setCategoriaVehiculo(veh.getCategoria().name());
                
            }

            return Respuesta.lista(new Respuesta("resultado", dto)); 
        } catch (PeajeException ex) {
            return Respuesta.lista(new Respuesta("mensaje", ex.getMessage()));
        }
   
        
    }
    

}
