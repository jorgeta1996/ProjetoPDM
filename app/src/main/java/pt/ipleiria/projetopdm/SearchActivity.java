package pt.ipleiria.projetopdm;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import pt.ipleiria.projetopdm.modelo.GestorVeiculos;


/**
 * Classe que procura um determinado veículo tanto pela matrícula como pelo nome do proprietário
 */
public class SearchActivity extends AppCompatActivity {

    /**
     * Variáveis para pesquisa
     */
    private ListView listView;
    private GestorVeiculos gestorVeiculos;
    private EditText editText;
    private ArrayAdapter adapter;
    private ArrayList<String> matriculas = new ArrayList<>();


    /**
     * Variáveis para Toolbar
     */
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        gestorVeiculos = GestorVeiculos.getInstance();
        /** Minimizar notification bar do android **/
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        editText = findViewById(R.id.editText_searchMatricula);
        listView = findViewById(R.id.listview_searchActivity);

        createListView();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,matriculas);
        listView.setAdapter(adapter);


        /**
         * Atualiza os elementos da listView à medida que os carateres são colocados ou retirados da barra de pesquisa
         */
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(i!=0 || i1!=0 || i2!=0)
                    (SearchActivity.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /**
         * Abre nova atividade ao carregar num dos elementos na listviiew
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick (AdapterView < ? > adapter, View view,int position, long arg){
                                                Intent iView = new Intent(SearchActivity.this, ViewActivity.class);
                                                iView.putExtra(MainActivity.VEICULO,gestorVeiculos.obterVeiculo(position));
                                                startActivity(iView);
                                            }
                                        });

        /** Toolbar **/
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

    }


    /**
     * Método para procurar (deprecated), pois já atualiza automaticamente
     * @param view
     */
    public void onClickProcurar(View view) {
        EditText editText_search = findViewById(R.id.editText_searchMatricula);
        String matriculaToSearch = editText_search.getText().toString();

//        ArrayList<GestorVeiculos> searchedVehicles = gestorVeiculos.procurarmatricula(matriculaToSearch);
//        ArrayAdapter<GestorVeiculos> adapter = new ArrayAdapter<GestorVeiculos>(this, android.R.layout.simple_list_item_1, searchedVehivles);

//        if (searchedVehicles.isEmpty()) {
//            Toast.makeText(this, getString(R.string.VehiclesNotFoundString), Toast.LENGTH_LONG).show();
//        }
    }

    /**
     * --------------------------------------Métodos para a Navigation Drawer-------------------------------
     **/
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
    /**-------------------------------------- / Métodos para a Navigation Drawer-------------------------------**/




    /**
     * Cria listview de cada objeto com a respetiva matrícula e proprietário
     */
    public void createListView(){
        matriculas.clear();
        for (int i=0;i<gestorVeiculos.getVeiculos().size();i++){

            matriculas.add(getResources().getString(R.string.licensePlateString)+": " + gestorVeiculos.obterVeiculo(i).getMatricula() +"\n"+getResources().getString(R.string.textViewOwner)+": " + gestorVeiculos.obterVeiculo(i).getProprietario());
        }
    }
}


