package pt.ipleiria.projetopdm;


import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import pt.ipleiria.projetopdm.modelo.GestorVeiculos;
import pt.ipleiria.projetopdm.modelo.Veiculo;
import pt.ipleiria.projetopdm.recycler.RecyclerVehiclesAdapter;



public class MainActivity extends AppCompatActivity implements SensorEventListener{



    private TextView mensagem;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private int sensorInd;



    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;


    private GestorVeiculos gestorVeiculos;
    private RecyclerView mRecyclerView;
    private RecyclerVehiclesAdapter mAdapter;

    public static final int ADD_VEHICLE_REQUEST_CODE = 1;
    public static final int EDIT_VEHICLE_REQUEST_CODE = 2;
    private static final String ESTADO_GESTOR_VEICULOS = "ESTADO_GESTOR_VEICULOS";
    public static final String VEICULO = "veiculo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//            this.gestorVeiculos.lerFicheiro(this);
        } else {
            this.gestorVeiculos = (GestorVeiculos) savedInstanceState.getSerializable(ESTADO_GESTOR_VEICULOS);
        }

        mRecyclerView = findViewById(R.id.recyclerViewMain);
        mAdapter = new RecyclerVehiclesAdapter(gestorVeiculos, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        /**Warning sensor de luminosidade**/
        mensagem = findViewById(R.id.textViewMensagem);
        mensagem.setText("\u26a0\ufe0f" + getString(R.string.mensagemWarningLuz)); // emoji + mensagem de luminosidade
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ESTADO_GESTOR_VEICULOS, gestorVeiculos);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()){
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

            case R.id.menuItemShare:
                //POR FAZER
                break;
            case R.id.menuItemAdd:
                Intent intentAdd = new Intent(this, AddActivity.class);
                startActivityForResult(intentAdd, ADD_VEHICLE_REQUEST_CODE);
                break;
            case R.id.menuItemSearch:
                Intent intentSearch = new Intent(this, SearchActivity.class);
                startActivity(intentSearch);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
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
            case R.id.nav_home:
                Intent i1 = new Intent(this, MainActivity.class);
                startActivity(i1);
                break;
            case R.id.nav_search:
                Intent i2 = new Intent(this, SearchActivity.class);
                startActivity(i2);
                break;
            case R.id.nav_add:
                Intent i3 = new Intent(this, AddActivity.class);
                startActivity(i3);
                break;
            case R.id.nav_share:
//                Intent i4 = new Intent(this, MainActivity.class);
//                startActivity(i4);
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
//                Intent i6 = new Intent(this, MainActivity.class);
//                startActivity(i6);
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

