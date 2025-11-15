package ort.da.DAObligatorio.servicios;


import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.modelo.peajes.Notificacion;
import ort.da.DAObligatorio.modelo.peajes.RegistroNotificacion;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.observador.Observable;
import ort.da.DAObligatorio.observador.Observador;


public class ServicioNotificaciones implements Observador {

    private List<RegistroNotificacion> registros;

    public ServicioNotificaciones() {
        this.registros = new ArrayList<RegistroNotificacion>();
    }


    public void agregarNotificacion(String msj, Propietario propietario) {
        if(msj == null || propietario == null) {
            return;
        }

        Notificacion notificacion = new Notificacion(msj);
        RegistroNotificacion registro = new RegistroNotificacion(propietario, notificacion);
        registros.add(registro);
    }

   public List<Notificacion> obtenerNotificacionesDelPropietario(Propietario propietario){
    ArrayList<Notificacion> resultado = new ArrayList<Notificacion>();
    
    if(propietario == null || propietario.getCedula() == null) {
        return resultado;
    }

    String cedula = propietario.getCedula();

    for(RegistroNotificacion registrio : registros){
        Propietario pReg = registrio.getPropietario();
        if(pReg!= null && cedula.equals( pReg.getCedula())){
            resultado.add( registrio.getNotificacion() );
        }
    }
    return resultado;
   }


   public void borrarNotificacionesDelPropietario(Propietario propietario){
        if(propietario == null || propietario.getCedula() == null) {
            return;
        }

        String cedula = propietario.getCedula();

        for(int i = registros.size() -1; i >=0 ; i--){
            RegistroNotificacion reg = registros.get(i);
            Propietario pReg = reg.getPropietario();
            if(pReg!= null && cedula.equals( pReg.getCedula())){
                registros.remove(i);
            }
        }
   }


   @Override
   public void actualizar(Object evento, Observable origen) {
    try{
            if(evento instanceof Object[]){
                Object[] ev = (Object []) evento;
                
                if(ev.length >= 3 && ev[0] instanceof Propietario && ev[1] instanceof String){
                    Propietario p = (Propietario) ev[0];
                    String tipo = (String) ev[1];
                    Object detalle = ev[2];
                    if("ESTADO_CAMBIADO".equals(tipo)){
                        String msj = "Tu estado fue cambiado a: "+ detalle + ",";
                        agregarNotificacion(msj, p);
                    }else if("ASIGNACION_BONIFICACION".equals(tipo)){
                        String msj = "Se te asignó la bonificación "+ detalle + ",";
                        agregarNotificacion(msj, p);
                    }//AGREGAR TAMBIEN OTROS TIPOS DE NOTIFICACIONES ACA EJEMPLO SALDO BAJO ETC ETC
                }
            }
        }catch(Exception ex){
            //no hacer nada
    }

   }
 

}
