package pt.ipleiria.projetopdm;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import pt.ipleiria.projetopdm.modelo.GestorVeiculos;
import pt.ipleiria.projetopdm.modelo.Veiculo;

public class ViewActivity extends AppCompatActivity {

    private TextView txtModel;
    private TextView txtBrand;
    private TextView txtOwner;
    private TextView txtPlate;
    private TextView txtCategory;
    private TextView txtCountry;
    private Button btnCor;
    private ImageView imageVehicle;

    private GestorVeiculos gestorVeiculos;

    /**
     * Variáveis para Toolbar
     */
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        /** Minimizar notification bar do android **/
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gestorVeiculos = GestorVeiculos.getInstance();

        /** Instanciamento das variáveis para o construtor Veiculo e pré-preenchimento dos campos **/
        Veiculo v = (Veiculo) getIntent().getSerializableExtra(MainActivity.VEICULO);
        txtModel = findViewById(R.id.textView_ModelView);
        txtModel.setText(v.getModelo());
        txtBrand = findViewById(R.id.textView_BrandView);
        txtBrand.setText(v.getMarca());
        txtOwner = findViewById(R.id.textView_OwnerView);
        txtOwner.setText(v.getProprietario());
        txtPlate = findViewById(R.id.textView_PlateView);
        txtPlate.setText(v.getMatricula());
        txtCategory = findViewById(R.id.textView_CategoryView);
        txtCategory.setText(v.getCategoria());
        txtCountry = findViewById(R.id.textView_CountryView);
        txtCountry.setText(v.getCountry());
        btnCor = findViewById(R.id.buttonColorView);
        btnCor.setBackgroundColor(v.getCor());


        /** Seleciona imagem do veículo**/
        imageVehicle = findViewById(R.id.imageVehicleEdit);
        if (v.getPathPhoto().length()<3 ) {
            switch (v.getCategoria()) {
                case "Class A":
                    imageVehicle.setImageResource(R.drawable.classe_a);
                    break;
                case "Class B":
                    imageVehicle.setImageResource(R.drawable.classe_b);
                    break;
                case "Class C":
                    imageVehicle.setImageResource(R.drawable.classe_c);
                    break;
                case "Class D":
                    imageVehicle.setImageResource(R.drawable.classe_d);
                    break;
                default:
                    imageVehicle.setImageResource(R.drawable.ic_launcher_no_foreground);
                    break;
            }
        } else {
            try {
                File f = new File(this.getFilesDir() + "/" + v.getPathPhoto());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                imageVehicle.setImageBitmap(b);
            } catch (Exception e) {
                imageVehicle.setImageResource(R.drawable.ic_launcher_no_foreground);
            }
        }


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
    /**-------------------------------------- /Métodos para a Navigation Drawer-------------------------------**/




}
