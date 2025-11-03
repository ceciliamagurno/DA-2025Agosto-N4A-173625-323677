package ort.da.DAObligatorio.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.Usuario;
import ort.da.DAObligatorio.servicios.fachada.Fachada;
import ort.da.DAObligatorio.utils.Respuesta;
import org.springframework.web.bind.annotation.PostMapping;
import ort.da.DAObligatorio.modelo.Administrador;
import ort.da.DAObligatorio.modelo.Propietario;

@RestController
@RequestMapping("/login")
public class ControladorLogin {
    @PostMapping("/login")
    public List<Respuesta> login(HttpSession sesionHttp, @RequestParam String cedula, @RequestParam String contrasenia) throws PeajeException {

        Usuario usuarioLogueado  = Fachada.getInstancia().login(cedula, contrasenia);
        if (usuarioLogueado == null) {
            throw new PeajeException("Cédula o contraseña inválida");
        }
        sesionHttp.setAttribute("usuario", usuarioLogueado);

        // Redirigir según tipo de usuario
        if (usuarioLogueado instanceof Administrador) {
            return Respuesta.lista(new Respuesta("loginExitoso", "admin.html"));
        } else if (usuarioLogueado instanceof Propietario) {
            return Respuesta.lista(new Respuesta("loginExitoso", "menu.html"));
        }
        return null; 
    }

}
 