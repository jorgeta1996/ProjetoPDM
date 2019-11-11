package pt.ipleiria.projetopdm.modelo;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Veiculo implements Serializable, Comparable<Veiculo>  {

    private String matricula;
    private String modelo;
    private String marca;
    private String pathPhoto;
    private String proprietario;
    private String cor;

    public Veiculo (String matricula,String modelo, String marca, String pathPhoto, String proprietario, String cor){
        this.matricula = matricula;
        this.modelo = modelo;
        this.marca = marca;
        this.pathPhoto = pathPhoto;
        this.proprietario = proprietario;
        this.cor = cor;
    }


    @Override
    public int compareTo(Veiculo veiculo) {
        return matricula.compareToIgnoreCase(veiculo.getMatricula());
    }

    public String getMatricula() {
        return matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public String getPathPhoto() {
        return pathPhoto;
    }

    public String getProprietario() {
        return proprietario;
    }

    public String getCor() {
        return cor;
    }

    @NonNull
    @Override
    public String toString() {
        return ;
    }
}
