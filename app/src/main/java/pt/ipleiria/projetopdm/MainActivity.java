package pt.ipleiria.projetopdm;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;

import pt.ipleiria.projetopdm.modelo.GestorVeiculos;
import pt.ipleiria.projetopdm.modelo.Veiculo;
import pt.ipleiria.projetopdm.recycler.RecyclerVehiclesAdapter;

import static pt.ipleiria.projetopdm.Configuration.LIST_USER_URL;


/**
 * Classe principal, onde é mostrada a lista total de veículos e dá acesso a outras classes que descendem desta
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener{



    private TextView mensagem;
    private SensorManager sensorManager;
    private Sensor lightSensor;


    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;


    private GestorVeiculos gestorVeiculos;
    private RecyclerView mRecyclerView;
    private RecyclerVehiclesAdapter mAdapter;

    /**
     * Constantes
     */
    public static final int ADD_VEHICLE_REQUEST_CODE = 1;
    public static final int EDIT_VEHICLE_REQUEST_CODE = 2;
    private static final String ESTADO_GESTOR_VEICULOS = "ESTADO_GESTOR_VEICULOS";
    public static final String VEICULO = "veiculo";
    public static final String VEICULO_POSITION = "veiculo_position";
    public static final String VEICULOS = "veiculos";

    String pathPhoto;
    private int sensorInd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * Toolbar
         */
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        drawerToggle.syncState();
        mDrawer.addDrawerListener(drawerToggle);


        if (savedInstanceState == null) {
            this.gestorVeiculos = gestorVeiculos.getInstance();
        }else {
            this.gestorVeiculos = (GestorVeiculos) savedInstanceState.getSerializable(ESTADO_GESTOR_VEICULOS);
        }

        mRecyclerView = findViewById(R.id.recyclerViewMain);
        sendRequest();


        /**Warning sensor de luminosidade**/
        mensagem = findViewById(R.id.textViewMensagem);
        mensagem.setText("\u26a0\ufe0f" + getString(R.string.mensagemWarningLuz)); // emoji + mensagem de luminosidade
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    /**
     * Pedido para receber veiculos da base de dados
     */
    private void sendRequest() {
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        StringRequest stringRequest = new StringRequest(LIST_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log.e("null","ser image"+response);
                        showJSON(response);

                        loading.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /**
     * Método para converter dados Json
     */
    private void showJSON(String json) {
        JsonParser pj = new JsonParser(json);
        pj.parseJSON();

        if (JsonParser.uMatricula == null && JsonParser.uProprietario == null && JsonParser.uMarca == null && JsonParser.uModelo == null && JsonParser.uCor == null && JsonParser.uImage == null && JsonParser.uCategoria == null && JsonParser.uCountry == null) {
            Toast.makeText(this, "No Users Found", Toast.LENGTH_SHORT).show();
        } else {
            for (int i=0;i<JsonParser.uMatricula.length;i++){
                if (JsonParser.uMatricula[i]!=null) {
                    if (JsonParser.uImage[i].length() > 10) {
                        pathPhoto = JsonParser.uMatricula[i] + ".jpg";
                        byte[] decodedString = Base64.decode(JsonParser.uImage[i], Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        saveImage(pathPhoto, (decodedByte));

                    } else {
                        switch (JsonParser.uCategoria[i]) {
                            case "Class A":
                                pathPhoto = "A";
                                break;
                            case "Class B":
                                pathPhoto = "B";
                                break;
                            case "Class C":
                                pathPhoto = "C";
                                break;
                            case "Class D":
                                pathPhoto = "D";
                                break;
                        }
                    }
                    Veiculo veiculo = new Veiculo(JsonParser.uMarca[i], JsonParser.uModelo[i], JsonParser.uMatricula[i],pathPhoto,JsonParser.uProprietario[i],Integer.parseInt(JsonParser.uCor[i]),JsonParser.uCategoria[i],JsonParser.uCountry[i] );
                    gestorVeiculos.adicionarVeiculo(veiculo);
            }

            }
            mAdapter = new RecyclerVehiclesAdapter(gestorVeiculos,this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter.notifyDataSetChanged();

        }
    }

    public void saveImage(String filename, Bitmap bitmap) {
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_VEHICLE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Veiculo newVeiculo = (Veiculo) data.getSerializableExtra(AddActivity.NEW_VEHICLE);
                    if (!(gestorVeiculos.getVeiculos().contains(newVeiculo)))
                        gestorVeiculos.adicionarVeiculo(newVeiculo);
                    else
                        gestorVeiculos.atualizarVeiculo(gestorVeiculos.getVeiculos().indexOf(newVeiculo), newVeiculo);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case EDIT_VEHICLE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Veiculo editVehicle = (Veiculo) data.getSerializableExtra(EditActivity.EDIT_VEHICLE);
                    gestorVeiculos.atualizarVeiculo(mAdapter.getItemPosition(), editVehicle);
                    mAdapter.notifyDataSetChanged();
                }
        }
    }

    /**
     * Método que controla botões do menu da barra
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()){
            /**
             * Botão de apagar veiculos selecionados (checkboxes)
             */
            case R.id.menuItemDelete:
                if (!mAdapter.getListaVeiculos().isEmpty()) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle(getString(R.string.txtRemove));
                    dialog.setMessage(getString(R.string.txtConfirmRemove));

                    dialog.setPositiveButton(getString(R.string.txtConfirmYes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (int j=0;j<mAdapter.getListaVeiculos().size();j++) {
                                String path = mAdapter.getListaVeiculos().get(j).getPathPhoto();
                                if (path != "") {
                                    File f = new File(getFilesDir() + "/" + path);
                                    f.delete();
                                }
                                gestorVeiculos.removerVeiculo(gestorVeiculos.obterPosVeiculo(mAdapter.getVeiculoByIndex(j)));
                                mAdapter.notifyDataSetChanged();
                            }
                            mAdapter.clearListaVeiculos();
                        }
                    });

                    dialog.setNegativeButton(getString(R.string.txtConfirmNo), null);
                    dialog.show();
                }else{
                    Toast.makeText(this, "Select an item", Toast.LENGTH_SHORT).show();
                }
                break;
            /**
             * Botão de adicionar veiculo
             */
            case R.id.menuItemAdd:
                Intent intentAdd = new Intent(this, AddActivity.class);
                intentAdd.putExtra(VEICULOS,gestorVeiculos);
                startActivityForResult(intentAdd, ADD_VEHICLE_REQUEST_CODE);
                break;
            /**
             * Botão de procurar veiculo
             */
            case R.id.menuItemSearch:
                Intent intentSearch = new Intent(this, SearchActivity.class);
                startActivity(intentSearch);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Método para selecionar ficheiro xml do menu a utilizar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    /** TOOLBAR **/
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    /**
     * Escolha da atividade selecionada na Drawer Navigation
     *
     * @param menuItem
     */
    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_search:
                Intent i2 = new Intent(this, SearchActivity.class);
                startActivity(i2);
                break;
            case R.id.nav_add:
                Intent i3 = new Intent(this, AddActivity.class);
                startActivity(i3);
                break;

            case R.id.nav_feedback:

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"licenseplateeec@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback!");

                try {
                    startActivity(Intent.createChooser(intent,getString(R.string.ChooseEmail)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, getString(R.string.CantSendFeedback), Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.nav_info:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage(R.string.navDrawerInfo);
                dialog.setTitle(R.string.icon_infoTitle);
                dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                dialog.create();
                dialog.show();
                break;
            case R.id.nav_leave:
                finish();


                break;
            default:

        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Métodos para Sensor de luminosidade
     */
    public boolean checkSensorAvailability(int sensorType) {
        boolean isSensor = false;
        if (sensorManager.getDefaultSensor(sensorType) != null) {
            isSensor = true;
        }
        Log.d("checkSensorAvailability", "" + isSensor);
        return isSensor;
    }

    public void lightSensor(View view) {
        if (checkSensorAvailability(Sensor.TYPE_LIGHT)) {
            sensorInd = Sensor.TYPE_LIGHT;
        }
    }

    public void rotationSensor(View view) {
        if (checkSensorAvailability(Sensor.TYPE_GAME_ROTATION_VECTOR)) {
            sensorInd = Sensor.TYPE_GAME_ROTATION_VECTOR;
        }
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {

        //check sensor type matches current sensor type set by button click
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                    float value = event.values[0];
                    if (value < 250) {
                        mensagem.setVisibility(View.VISIBLE);
                    } else {
                        mensagem.setVisibility(View.GONE);
                    }
            }

        }

    @Override
    protected void onStart() {
        super.onStart();
        if (lightSensor != null)
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }


}

