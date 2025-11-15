package ort.da.DAObligatorio.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ort.da.DAObligatorio.dtos.PropietarioDto;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.servicios.fachada.Fachada;
import ort.da.DAObligatorio.utils.Respuesta;

@RestController
@RequestMapping("/propietarios")
public class ControladorPropietarios {

    private final Fachada f = Fachada.getInstancia();

    @GetMapping("/lista")
    public List<Respuesta> listarPropietarios() {
        List<PropietarioDto> propietariosDto = new ArrayList<>();
        for (Propietario p : f.getPropietarios()) {
            propietariosDto.add(new PropietarioDto(p));
        }
        return Respuesta.lista(new Respuesta("propietarios", propietariosDto));
    }

}



