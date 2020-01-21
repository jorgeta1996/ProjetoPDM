package pt.ipleiria.projetopdm;

/**
 * Classe com configurações para a base de dados Gooogle Spreadsheets
 */

public class Configuration {

    public static final String APP_SCRIPT_WEB_APP_URL = "https://script.google.com/macros/s/AKfycbxgD3YN9pZDByMeObMSmqi6zl7AtU8_NNCL4HkC9JXQ-ep_ZEk/exec";
    public static final String ADD_USER_URL = APP_SCRIPT_WEB_APP_URL;
    public static final String LIST_USER_URL = APP_SCRIPT_WEB_APP_URL+"?action=readAll";
    public static final String DEL_USER_URL = APP_SCRIPT_WEB_APP_URL;
    public static final String EDIT_USER_URL = APP_SCRIPT_WEB_APP_URL;

    public static final String KEY_MATRICULA = "uMatricula";
    public static final String KEY_PROPRIETARIO = "uProprietario";
    public static final String KEY_MARCA = "uMarca";
    public static final String KEY_MODELO= "uModelo";
    public static final String KEY_COR = "uCor";
    public static final String KEY_CATEGORIA = "uCategoria";
    public static final String KEY_IMAGE = "uFoto";
    public static final String KEY_COUNTRY = "uCountry";

    public static final String KEY_POSITION = "uPosition";

    public  static final String KEY_ACTION = "action";

    public static final String KEY_USERS = "records";
}
