package ort.da.DAObligatorio.dtos;

public class VehiculoTableroDto {

    private String matricula;
    private String modelo;
    private String color;
    private String categoria;     
    private int cantidadTransitos;
    private double totalGastado;

    public VehiculoTableroDto() { }

    public VehiculoTableroDto(ort.da.DAObligatorio.modelo.vehiculos.Vehiculo v) {
        this.matricula = v.getMatricula();
        this.modelo   = v.getModelo();
        this.color    = v.getColor();
       
        this.categoria = v.getCategoria() != null ? v.getCategoria().name() : "";
    }

    

    public String getMatricula() { 
        return matricula; 
    }
    public void setMatricula(String matricula) { 
        this.matricula = matricula; 
    }

    public String getModelo() { 
        return modelo; 
    }
    public void setModelo(String modelo) { 
        this.modelo = modelo; 
    }

    public String getColor() { 
        return color; 
    }
    public void setColor(String color) { 
        this.color = color; 
    }

    public String getCategoria() { 
        return categoria; 
    }
    public void setCategoria(String categoria) { 
        this.categoria = categoria;
    }

    public int getCantidadTransitos() { 
        return cantidadTransitos; 
    }
    public void setCantidadTransitos(int cantidadTransitos) { 
        this.cantidadTransitos = cantidadTransitos; 
    }

    public double getTotalGastado() { 
        return totalGastado; 
    }
    public void setTotalGastado(double totalGastado) { 
        this.totalGastado = totalGastado; 
    }
}

