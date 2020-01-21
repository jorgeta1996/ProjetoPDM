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

    /**
     * Método para adicionar veiculo à lista de veiculos
     * @param veiculo Veiculo a adicionar
     */
    public void adicionarVeiculo(Veiculo veiculo){
        if (!veiculos.contains(veiculo)){
            veiculos.add(veiculo);
            Collections.sort(veiculos);
        }
    }

    /**
     * Método para atualizar o veiculo da lista de veiculos
     * @param pos Posição do veiculo na lista
     * @param veiculo Veiculo a atualizar
     */
    public void atualizarVeiculo(int pos, Veiculo veiculo) {
        if (!veiculos.contains(veiculo) || veiculo.getMatricula().equalsIgnoreCase(veiculos.get(pos).getMatricula())) {
            veiculos.set(pos, veiculo);
            Collections.sort(veiculos);
        }
    }

    /**
     * Método para obter veiculo através da posição
     * @param pos Posição do veiculo na lista
     * @return Veiculo na posição
     */
    public Veiculo obterVeiculo(int pos){
        return veiculos.get(pos);
    }

    /**
     * Método para receber lista de veiculos
     * @return lista de veiculos
     */
    public ArrayList<Veiculo> getVeiculos(){
        return veiculos;
    }

    /**
     * Método para remover veiculo na posição dada
     * @param pos Posição do veiculo na lista
     */
    public void removerVeiculo(int pos){
        veiculos.remove(pos);
    }

    /**
     * Método para obter posição do veiculo na lista
     * @param veiculo Veiculo a procurar
     * @return Posição do veiculo na lista
     */
    public int obterPosVeiculo(Veiculo veiculo){
        return veiculos.indexOf(veiculo);
    }

    /**
     * Método toString
     * @return String com todos os toStrings dos veiculos existentes
     */
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
