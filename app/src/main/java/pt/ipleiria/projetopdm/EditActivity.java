package pt.ipleiria.projetopdm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;

import pt.ipleiria.projetopdm.modelo.Veiculo;
import yuku.ambilwarna.AmbilWarnaDialog;

public class EditActivity extends AppCompatActivity {


    private EditText editText_Matricula;
    private EditText editText_Owner;
    private EditText editText_Model;
    private Button btnCor;
    private ImageView imageView;

    private TextView textViewSpinnerMaker;

    private TextView textViewSpinnerCountriesDialog;

//    private ImageView imageVehicle;
//

     private int cor;
//    private String pathPhoto;








    /**
     * Variáveis para Toolbar
     */
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;




    public static final String EDIT_CONTACT = "EDITVEHICLE";
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);




        /** Instanciamento das variáveis para o construtor Veiculo e pré-preenchiimento dos campos editáveis**/
        Veiculo v = (Veiculo) getIntent().getSerializableExtra(MainActivity.VEICULO);
        editText_Matricula = findViewById(R.id.editTextPlateEdit);
        editText_Matricula.setText(v.getMatricula());
        editText_Owner = findViewById(R.id.editTextOwnerEdit);
        editText_Owner.setText(v.getProprietario());
        editText_Model= findViewById(R.id.editTextModelEdit);
        editText_Model.setText(v.getModelo());
        btnCor = findViewById(R.id.buttonColorEdit);
        btnCor.setBackgroundColor(v.getCor());
        imageView = findViewById(R.id.imageVehicleEdit);
        textViewSpinnerMaker=findViewById(R.id.textView_MarcaEdit);



        if (v.getPathPhoto().trim().isEmpty()) {
            imageView.setImageResource(R.drawable.ic_launcher_no_foreground);
        } else {
            try {
                File f = new File(this.getFilesDir() + "/" + v.getPathPhoto());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                imageView.setImageBitmap(b);
            } catch (Exception e) {

                imageView.setImageResource(R.drawable.ic_launcher_no_foreground);
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


    public void onClickSpinnerCountriesEdit(View view) {
    }

    public void onClickButtonEdit(View view) {
    }

    public void onClickButtonGalleryEdit(View view) {
    }

    public void onClickButtonPictureEdit(View view) {
    }

    public void onClickBtnColorEdit(View view) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, cor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(EditActivity.this, getString(R.string.onCancel), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                btnCor.setBackgroundColor(color);
                cor=color;
            }
        });
        colorPicker.show();
    }

    public void onClickSpinnerMarcasEdit(View view) {
    }





    /**--------------------------------------Métodos para a Navigation Drawer-------------------------------**/
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

        switch(menuItem.getItemId()) {
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
                Intent intent=Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
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
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
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
