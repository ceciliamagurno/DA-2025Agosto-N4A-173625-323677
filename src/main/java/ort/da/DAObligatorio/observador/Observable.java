package ort.da.DAObligatorio.observador;

import java.util.ArrayList;


public class Observable {
    private ArrayList<Observador> observadores = new ArrayList<>();
     
    public void agregarObservador(Observador observador){
        if(!observadores.contains(observador)){
            observadores.add(observador);
        }
    }

    public void quitarObservador(Observador obs){
        observadores.remove(obs);
    }

    public void avisar(Object evento){
        ArrayList<Observador> copia = new ArrayList<>(observadores);
        for(Observador obs:copia){
            obs.actualizar(evento, this);
        }
    }

}
