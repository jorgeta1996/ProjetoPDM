package pt.ipleiria.projetopdm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import pt.ipleiria.projetopdm.modelo.Veiculo;
import yuku.ambilwarna.AmbilWarnaDialog;

public class EditActivity extends AppCompatActivity {


    private EditText editText_Matricula;
    private EditText editText_Owner;
    private EditText editText_Model;
    private Button btnCor;
    private ImageView imageView;
    private String category;
    private ArrayList<String> items;
    private int cor;
    private SpinnerDialog spinnerDialog;
    private boolean read;
    private String pathPhoto;
    private TextView textViewSpinnerDialog;
    private TextView textViewSpinnerCountriesDialog;

    /**
     * Variáveis para Toolbar
     */
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;


    private int a=0;
    public static final String EDIT_VEHICLE = "EDITVEHICLE";
    public static final int GALLERY_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 2;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        /** Instanciamento das variáveis para o construtor Veiculo e pré-preenchimento dos campos editáveis**/
        Veiculo v = (Veiculo) getIntent().getSerializableExtra(MainActivity.VEICULO);
        editText_Matricula = findViewById(R.id.editTextPlateEdit);
        editText_Matricula.setText(v.getMatricula());
        editText_Owner = findViewById(R.id.editTextOwnerEdit);
        editText_Owner.setText(v.getProprietario());
        editText_Model = findViewById(R.id.editTextModelEdit);
        editText_Model.setText(v.getModelo());
        btnCor = findViewById(R.id.buttonColorEdit);
        btnCor.setBackgroundColor(v.getCor());
        cor = v.getCor();
        imageView = findViewById(R.id.imageVehicleEdit);


        final Spinner spinnerVehicle = findViewById(R.id.spinnerVehicleEdit);
        spinnerVehicle.setSelection(0);
        textViewSpinnerDialog = findViewById(R.id.SpinnerMarcasEdit);
        textViewSpinnerCountriesDialog = findViewById(R.id.SpinnerCountriesEdit);


        /** Criação do spinner da categoria do veículo **/
        spinnerVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch (position) {
                    case 0:
                        category = "Class A";
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaMotas)));
                        if (!read)
                            imageView.setImageResource(R.drawable.classe_a);
                        pathPhoto = "";
                        break;
                    case 1:
                        category = "Class B";
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaCarros)));
                        if (!read)
                            imageView.setImageResource(R.drawable.classe_b);
                        pathPhoto = "";
                        break;
                    case 2:
                        category = "Class C";
                        //MUDAR LISTA
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaMotas)));
                        if (!read)
                            imageView.setImageResource(R.drawable.classe_c);
                        pathPhoto = "";
                        break;
                    case 3:
                        category = "Class D";
                        //MUDAR LISTA
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaMotas)));
                        if (!read)
                            imageView.setImageResource(R.drawable.classe_d);
                        pathPhoto = "";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



//        if(v.getCategoria()=="Class A"){
//            spinnerVehicle.setSelection(0);
//        }
//        else if(v.getCategoria()=="Class B"){
//            spinnerVehicle.setSelection(1);
//        }
//        else if(v.getCategoria()=="Class C"){
//            spinnerVehicle.setSelection(2);
//        }
//        else if(v.getCategoria()=="Class D"){
//            spinnerVehicle.setSelection(3);
//        }



        if (v.getPathPhoto().trim().isEmpty()) {
//            switch (v.getCategoria()) {
//                case "Class A":
//                    imageView.setImageResource(R.drawable.classe_a);
//                    pathPhoto = "";
//                    break;
//                case "Class B":
//                    imageView.setImageResource(R.drawable.classe_b);
//                    pathPhoto = "";
//                    break;
//                case "Class C":
//                    imageView.setImageResource(R.drawable.classe_c);
//                    pathPhoto = "";
//                    break;
//                case "Class D":
//                    imageView.setImageResource(R.drawable.classe_d);
//                    pathPhoto = "";
//                    break;
//            }

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


    /**
     * Método onClick para criação do spinner das Marcas
     */
    public void onClickSpinnerMarcasEdit(View view) {
        spinnerDialog = new SpinnerDialog(EditActivity.this, items, getString(R.string.textViewSpinnerDialog));
        spinnerDialog.showSpinerDialog();
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                textViewSpinnerDialog.setText(item);
            }
        });
    }

    /**
     * Método onClick para criação do spinner dos Países
     */
    public void onClickSpinnerCountriesEdit(View view) {
        ArrayList<String> countries = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.countries)));
        spinnerDialog = new SpinnerDialog(EditActivity.this, countries, getString(R.string.textViewSpinnerDialog));
        spinnerDialog.showSpinerDialog();
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                textViewSpinnerCountriesDialog.setText(item);
            }
        });
    }

    public void onClickButtonEdit(View view) {
        EditText editTextOwner = findViewById(R.id.editTextOwnerEdit);
        EditText editTextPlate = findViewById(R.id.editTextPlateEdit);
        String owner = editTextOwner.getText().toString();
        String licensePlate = editTextPlate.getText().toString().trim();
        String country = textViewSpinnerCountriesDialog.getText().toString();
        String brand = textViewSpinnerDialog.getText().toString();
        String model = editText_Model.getText().toString();
        int color = cor;
//s

        if (read) {

            File f=new File(this.getFilesDir() + "/" + pathPhoto);
            f.delete();

            pathPhoto = licensePlate + ".jpg";
            saveImage(pathPhoto, ((BitmapDrawable) imageView.getDrawable()).getBitmap());
        }
        /**
         * Construtor do veiculo
         * @param brand Marca do veiculo
         * @param model Modelo do veiculo
         * @param licensePlate Matricula do veiculo
         * @param pathPhoto Caminho para foto do veiculo
         * @param owner Proprietario do veiculo
         * @param color Cor do veiculo
         * @param category Categoria do veiculo
         * @param country País da matricula do veiculo
         */
        Veiculo veiculo = new Veiculo(brand,model,licensePlate,pathPhoto,owner,color,category,country);


        Intent returnIntent = new Intent();
        returnIntent.putExtra(EDIT_VEHICLE, veiculo);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void onClickButtonGalleryEdit(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.txtSelectPhoto)), GALLERY_REQUEST_CODE);
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
                cor = color;
            }
        });
        colorPicker.show();
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
