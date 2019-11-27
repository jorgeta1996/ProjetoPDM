package pt.ipleiria.projetopdm.modelo;

import android.widget.ImageView;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Veiculo implements Serializable, Comparable<Veiculo>  {

    private String matricula;
    private String modelo;
    private String marca;
    private ImageView photo;
    private String proprietario;
    private int cor;
    private String categoria;
    private String country;

    public Veiculo (String marca,String modelo, String matricula, ImageView photo, String proprietario, int cor, String categoria, String country){
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.photo = photo;
        this.proprietario = proprietario;
        this.cor = cor;
        this.categoria = categoria;
        this.country = country;
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

    public ImageView getPhoto() {
        return photo;
    }

    public String getProprietario() {
        return proprietario;
    }

    public int getCor() {
        return cor;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCountry() {
        return country;
    }

    @NonNull
    @Override
    public String toString() {
        return matricula + "\n" + marca + ">" + modelo;
    }


}
