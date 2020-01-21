package pt.ipleiria.projetopdm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static pt.ipleiria.projetopdm.Configuration.KEY_CATEGORIA;
import static pt.ipleiria.projetopdm.Configuration.KEY_COR;
import static pt.ipleiria.projetopdm.Configuration.KEY_IMAGE;
import static pt.ipleiria.projetopdm.Configuration.KEY_MARCA;
import static pt.ipleiria.projetopdm.Configuration.KEY_MATRICULA;
import static pt.ipleiria.projetopdm.Configuration.KEY_MODELO;
import static pt.ipleiria.projetopdm.Configuration.KEY_PROPRIETARIO;
import static pt.ipleiria.projetopdm.Configuration.KEY_COUNTRY;
import static pt.ipleiria.projetopdm.Configuration.KEY_USERS;

public class JsonParser {
    public static String[] uMatricula;
    public static String[] uProprietario;
    public static String[] uMarca;
    public static String[] uModelo;
    public static String[] uCor;
    public static String[] uCategoria;
    public static String[] uImage;
    public static String[] uCountry;

    private JSONArray users = null;

    private String json;

    public JsonParser(String json) {
        this.json = json;
    }

    protected void parseJSON() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(KEY_USERS);

            uMatricula = new String[users.length()];
            uProprietario = new String[users.length()];
            uMarca = new String[users.length()];
            uModelo = new String[users.length()];
            uCor = new String[users.length()];
            uCategoria = new String[users.length()];
            uCountry = new String[users.length()];

            uImage = new String[users.length()];

            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);
                uMatricula[i] = jo.getString(KEY_MATRICULA);
                uProprietario[i] = jo.getString(KEY_PROPRIETARIO);
                uMarca[i] = jo.getString(KEY_MARCA);
                uModelo[i] = jo.getString(KEY_MODELO);
                uCor[i] = jo.getString(KEY_COR);
                uCategoria[i] = jo.getString(KEY_CATEGORIA);
                uImage[i] = jo.getString(KEY_IMAGE);
                uCountry[i] = jo.getString(KEY_COUNTRY);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            uMatricula = null;
            uProprietario = null;
            uMarca = null;
            uModelo = null;
            uCor = null;
            uCategoria = null;
            uCountry = null;
            uImage = null;
        }
    }
}
