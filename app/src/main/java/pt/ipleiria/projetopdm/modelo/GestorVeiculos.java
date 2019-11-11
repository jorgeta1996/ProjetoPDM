package pt.ipleiria.projetopdm.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;

public class GestorVeiculos implements Serializable {

    private ArrayList<Veiculo> veiculos = new ArrayList<>();

    private static GestorVeiculos INSTANCE = null;

    public static synchronized GestorVeiculos getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GestorVeiculos();
        }
        return INSTANCE;
    }

    private GestorVeiculos() {
    }

    public void adicionarVeiculo(Veiculo veiculo){
        if (!veiculos.contains(veiculo)){
            veiculos.add(veiculo);
            Collections.sort(veiculos);
        }
    }

    public Veiculo obterVeiculo(int pos){
        return veiculos.get(pos);
    }

    public ArrayList<Veiculo> getVeiculos(){
        return veiculos;
    }

    public void removerVeiculo(int pos){
        veiculos.remove(pos);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < veiculos.size(); i++) {
            str.append(veiculos.get(i)).append("\n");
        }
        return str.toString();
    }
}
