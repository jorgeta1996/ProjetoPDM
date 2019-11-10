package pt.ipleiria.projetopdm.modelo;

import java.io.Serializable;

public class Veiculo implements Serializable, Comparable<Veiculo>  {

    private String matricula;
    private String modelo;
    private String marca;
    //ADICIONAR MAIS ATRIBUTOS

    public Veiculo (String matricula,String modelo, String marca){
        this.matricula = matricula;
        this.modelo = modelo;
        this.marca = marca;
    }


    @Override
    public int compareTo(Veiculo veiculo) {
        //POR FAZER
        return 0;
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

    //FAZER TO STRING
}
