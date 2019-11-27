package pt.ipleiria.projetopdm;

import java.util.ArrayList;

public class GroupInfo {


    private String carPlate;
    private ArrayList<VehicleInfo> list = new ArrayList<VehicleInfo>();

    public String getName() {
        return carPlate;
    }

    public void setName(String carPlate) {
        this.carPlate = carPlate;
    }

    public ArrayList<VehicleInfo> getPlayerName() {
        return list;
    }

    public void setPlayerName(ArrayList<VehicleInfo> playerName) {
        this.list = playerName;
    }

}