package ort.da.DAObligatorio.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.modelo.CategoriaVehiculo;
import ort.da.DAObligatorio.modelo.Vehiculo;

public class ServicioVehiculos {
    
    private List<CategoriaVehiculo> categorias;
    private List<Vehiculo> vehiculos;
    
    public ServicioVehiculos() {
        this.categorias = new ArrayList<>();
        this.vehiculos = new ArrayList<>();
    }

    public List<CategoriaVehiculo> obtenerCategorias(){
        return categorias;
    }

    public void agregarCategoriaVehiculo(CategoriaVehiculo categoria) {
        if (categoria == null) return;
        if (categorias.contains(categoria)) return;
        categorias.add(categoria);
    }

    public CategoriaVehiculo buscarCategoriaPorNombre(String nombre){
        if(nombre == null || nombre.isEmpty()) return null;
        for(CategoriaVehiculo c : categorias){
            if(c.getNombre().equals(nombre)){
                return c;
            }
        }
        return null;
    }   

    public Vehiculo buscarVehiculoPorMatricula(String matricula){
        for(Vehiculo v : vehiculos){
            if(v.getMatricula().equals(matricula)){
                return v;
            }
        }
        return null;
    }


}
