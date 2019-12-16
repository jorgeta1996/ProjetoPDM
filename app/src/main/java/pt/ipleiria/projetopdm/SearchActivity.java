package pt.ipleiria.projetopdm;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import pt.ipleiria.projetopdm.modelo.GestorVeiculos;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listViewV;
    private GestorVeiculos gestorVeiculos;


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


//        Intent i = getIntent();
//        listViewV =  findViewById(R.id.listview_searchActivity);
//        listViewV.setOnItemClickListener(this);









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


    public void onClickProcurar(View view) {
        EditText editText_search = findViewById(R.id.editText_searchMatricula);
        String matriculaToSearch = editText_search.getText().toString();

//        ArrayList<GestorVeiculos> searchedVehicles = gestorVeiculos.procurarmatricula(matriculaToSearch);
//        ArrayAdapter<GestorVeiculos> adapter = new ArrayAdapter<GestorVeiculos>(this, android.R.layout.simple_list_item_1, searchedVehivles);

//        if (searchedVehicles.isEmpty()) {
//            Toast.makeText(this, getString(R.string.VehiclesNotFoundString), Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
            case R.id.nav_home:
                Intent i1 = new Intent(this, MainActivity.class);
                startActivity(i1);
                break;
            case R.id.nav_search:
//                Intent i2 = new Intent(this, MainActivity.class);
//                startActivity(i2);
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
//                Intent i5 = new Intent(this, MainActivity.class);
//                startActivity(i5);
                Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Min SDK 15
                startActivity(intent);
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
    /**-------------------------------------- /Métodos para a Navigation Drawer-------------------------------**/


}


