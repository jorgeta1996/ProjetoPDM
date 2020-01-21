package pt.ipleiria.projetopdm.modelo;



import androidx.annotation.NonNull;

import java.io.Serializable;

public class Veiculo implements Serializable, Comparable<Veiculo>  {

    /**
     * Variaveis do veiculo
     */
    private String matricula;
    private String modelo;
    private String marca;
    private String pathPhoto;
    private String proprietario;
    private int cor;
    private String categoria;
    private String country;

    public Veiculo (String marca, String modelo, String matricula, String pathPhoto, String proprietario, int cor, String categoria, String country){
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.pathPhoto = pathPhoto;
        this.proprietario = proprietario;
        this.cor = cor;
        this.categoria = categoria;
        this.country = country;
    }

    /**
     * Método que compara a categoria de veiculos para ordená-los posteriormente
     * @param veiculo Veiculo adicionado a comparar com restantes
     * @return
     */
    @Override
    public int compareTo(Veiculo veiculo) {
        return categoria.compareToIgnoreCase(veiculo.getCategoria());
    }

    /**
     * Get's dos campos do construtor
     */
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

    public int getCor() {
        return cor;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCountry() {
        return country;
    }


    /**
     * toString de um veiculo
     * @return String com matricula, marca e modelo
     */
    @NonNull
    @Override
    public String toString() {
        return matricula + "\n" + marca + ">" + modelo;
    }


}
