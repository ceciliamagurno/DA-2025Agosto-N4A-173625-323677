package ort.da.DAObligatorio.servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.modelo.vehiculos.CategoriaVehiculo;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;

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
        if (categoria == null && !categorias.contains(categoria)){
            categorias.add(categoria);
        }
    }

    public CategoriaVehiculo buscarCategoriaPorNombre(String nombre){
        if(nombre == null || nombre.isEmpty()) return null;
        String buscado = nombre.trim().toUpperCase();
        
        for(CategoriaVehiculo c : CategoriaVehiculo.values()){
            String nombreCat = c.name();
            if(nombreCat != null && nombreCat.trim().toUpperCase().equals(buscado)){
                return c;
            }
        }
        return null;
    }   


    public boolean agregarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) return false;   

        String mat = vehiculo.getMatricula();
        if(mat == null || mat.isEmpty()) return false;

        if(buscarVehiculoPorMatricula(mat)!= null) return false;

        vehiculos.add(vehiculo);
        return true;
    }


    public Vehiculo buscarVehiculoPorMatricula(String matricula){
        if (matricula == null || matricula.trim().isEmpty()) return null;

        String matIngresada = matricula.trim().toUpperCase();
        
        for(Vehiculo v : vehiculos){
            String matVehiculo = v.getMatricula();
            if(matVehiculo !=null && matVehiculo.trim().toUpperCase().equals(matIngresada)){
                return v;
            }
        }
        return null;
    }


}
