package ort.da.DAObligatorio.dtos;

import java.util.ArrayList;
import java.util.List;


import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;

public class PropietarioDto {
   
    public String cedula;
    public String nombreCompleto;
    public String estado;
    public double saldo;
    public List<String> matriculas;

    public PropietarioDto(Propietario propietario) {
        this.cedula = propietario.getCedula();
        this.nombreCompleto = propietario.getNombreCompleto();
        this.estado = propietario.getEstado().nombre();
        this.saldo = propietario.getSaldo();
        this.matriculas =  new ArrayList<String>();
        for(Vehiculo c :propietario.getVehiculos()){
            this.matriculas.add( c.getMatricula());
        }
    }

    public PropietarioDto(){}

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<String> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<String> matriculas) {
        this.matriculas = matriculas;
    }

    
}
