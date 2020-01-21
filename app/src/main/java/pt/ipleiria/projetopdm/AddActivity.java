package pt.ipleiria.projetopdm;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import pt.ipleiria.projetopdm.modelo.GestorVeiculos;
import pt.ipleiria.projetopdm.modelo.Veiculo;
import yuku.ambilwarna.AmbilWarnaDialog;


import static pt.ipleiria.projetopdm.Configuration.ADD_USER_URL;
import static pt.ipleiria.projetopdm.Configuration.KEY_ACTION;
import static pt.ipleiria.projetopdm.Configuration.KEY_CATEGORIA;
import static pt.ipleiria.projetopdm.Configuration.KEY_COR;
import static pt.ipleiria.projetopdm.Configuration.KEY_IMAGE;
import static pt.ipleiria.projetopdm.Configuration.KEY_MARCA;
import static pt.ipleiria.projetopdm.Configuration.KEY_MATRICULA;
import static pt.ipleiria.projetopdm.Configuration.KEY_MODELO;
import static pt.ipleiria.projetopdm.Configuration.KEY_PROPRIETARIO;
import static pt.ipleiria.projetopdm.Configuration.KEY_COUNTRY;

public class AddActivity extends AppCompatActivity {

    /**
     * Constantes
     */
    public static final String NEW_VEHICLE = "NEWVEHICLE";
    public static final int GALLERY_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 2;



    /**
     * Variável de verificação
     */
    private boolean read;

    /**
     * Variáveis para um Veículo
     */
    private ArrayList<String> items;
    private TextView textViewSpinnerDialog;
    private TextView textViewSpinnerCountriesDialog;
    private EditText editTextModel;
    private ImageView imageVehicle;
    private String category;
    private Button btnCor;
    private int cor;
    private String pathPhoto;

    private SpinnerDialog spinnerDialog;
    private Bitmap rbitmap;
    private String userImage;
    private GestorVeiculos gestorVeiculos;

    /**
     * Variáveis para Toolbar
     */
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    /**
     * Método onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        /** Minimizar notification bar do android **/
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gestorVeiculos = (GestorVeiculos) getIntent().getSerializableExtra(MainActivity.VEICULOS);

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

        /** Instanciamento das variáveis para o construtor Veiculo **/
        Spinner spinnerVehicle = findViewById(R.id.spinnerVehicle);
        textViewSpinnerDialog = findViewById(R.id.SpinnerMarcas);
        textViewSpinnerCountriesDialog = findViewById(R.id.SpinnerCountries);
        imageVehicle = findViewById(R.id.imageVehicle);
        editTextModel = findViewById(R.id.editTextModel);
        btnCor = findViewById(R.id.buttonColor);
        btnCor.setBackgroundColor(Color.RED);
        cor = Color.RED;

        /** Criação do spinner da categoria do veículo **/
        spinnerVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        category="Class A";
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaMotas)));
                        if (!read)
                            imageVehicle.setImageResource(R.drawable.classe_a);
                            pathPhoto="";
                        break;
                    case 1:
                        category="Class B";
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaCarros)));
                        if (!read)
                            imageVehicle.setImageResource(R.drawable.classe_b);
                            pathPhoto="";
                        break;
                    case 2:
                        category="Class C";
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaTrucks)));
                        if (!read)
                            imageVehicle.setImageResource(R.drawable.classe_c);
                            pathPhoto="";
                        break;
                    case 3:
                        category="Class D";
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaBus)));
                        if (!read)
                            imageVehicle.setImageResource(R.drawable.classe_d);
                            pathPhoto="";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        /** Persistência de dados **/
        if(savedInstanceState!=null){
            textViewSpinnerDialog.setText(savedInstanceState.getString("mMarca"));
            textViewSpinnerCountriesDialog.setText(savedInstanceState.getString("mCountry"));
            read = savedInstanceState.getBoolean("mRead");
            pathPhoto=savedInstanceState.getString("mFoto");

            try {
                File f=new File(this.getFilesDir() + "/" + pathPhoto);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                imageVehicle.setImageBitmap(b);
            } catch (Exception e) {

            }
        }
    }

    /**
     * Persistência de dados
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mMarca",textViewSpinnerDialog.getText().toString());
        outState.putString("mCountry",textViewSpinnerCountriesDialog.getText().toString());
        outState.putBoolean("mRead",read);
        if (read) {
            pathPhoto = getString(R.string.temporary) + ".jpg";
            saveImage(pathPhoto, ((BitmapDrawable) imageVehicle.getDrawable()).getBitmap());
            outState.putString("mFoto",pathPhoto);
        }
    }

    /**
     * Adicionar menu
     * @param menu Menu para adicionar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    /**
     * Criação de um Dialog para mostrar informação
     * @param item Item para clicar
     */
    public void onClickInfo(MenuItem item) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(R.string.icon_infoText);
        dialog.setTitle(R.string.icon_infoTitle);
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        dialog.create();
        dialog.show();
    }

    /**
     * Método onClick para criação do spinner das Marcas
     */
    public void onClickSpinnerMarcas(View view) {
        spinnerDialog = new SpinnerDialog(AddActivity.this, items, getString(R.string.textViewSpinnerDialog));
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
    public void onClickSpinnerCountries(View view) {
        ArrayList<String> countries = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.countries)));
        spinnerDialog = new SpinnerDialog(AddActivity.this, countries, getString(R.string.textViewSpinnerDialog));
        spinnerDialog.showSpinerDialog();
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                textViewSpinnerCountriesDialog.setText(item);
            }
        });
    }

    /**
     * Método onClick para ligar a câmera e tirar foto
     */
    public void onClickButtonPicture(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    /**
     * Método onClick para aceder à galeria e escolher uma imagem
     */
    public void onClickButtonGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.txtSelectPhoto)), GALLERY_REQUEST_CODE);
    }

    /**
     * Método para executar os intents
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    try {
                        Uri selectedImageUri = data.getData();
                        Glide.with(this)
                                .asBitmap()
                                .load(selectedImageUri)
                                .override(300, 300)
                                .into(imageVehicle);
                        read = true;

                    } catch (Exception e) {
                        Log.e("GestorVeiculos", getString(R.string.txtErrorFile), e);
                        read = false;
                    }
                    break;
                case CAMERA_REQUEST_CODE:
                    try {
                        if (data != null && data.getExtras() != null) {
                            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                            imageVehicle.setImageBitmap(imageBitmap);
                            read = true;
                        }
                    } catch (Exception e) {
                        Log.e("GestorVeiculos", getString(R.string.txtErrorFile), e);
                        read = false;
                    }
            }
        }
    }

    /**
     * Método onClick para adicionar um veículo
     */
    public void onClickButtonAdd(View view) {
        EditText editTextOwner = findViewById(R.id.editTextOwner);
        EditText editTextPlate = findViewById(R.id.editTextPlate);
        final String owner = editTextOwner.getText().toString();
        final String licensePlate = editTextPlate.getText().toString().trim();
        final String country = textViewSpinnerCountriesDialog.getText().toString();
        final String brand = textViewSpinnerDialog.getText().toString();
        final String model = editTextModel.getText().toString();
        final int color = cor;


         /* Prevenção matriculas repetidas */
        for (Veiculo v:gestorVeiculos.getVeiculos()){
            if (v.getMatricula().equalsIgnoreCase(licensePlate)){
                Toast.makeText(this, "License Plate already registered!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        /* Prevenção de campos por preencher */
        if (brand.trim().isEmpty() || model.trim().isEmpty()|| licensePlate.trim().isEmpty()|| owner.trim().isEmpty()|| color==0|| category.trim().isEmpty()|| country.trim().isEmpty()) {
            Toast.makeText(this, R.string.txtFillData, Toast.LENGTH_LONG).show();
            return;
        }

        /* Gravação de imagem localmente*/
        if (read) {
            File f=new File(this.getFilesDir() + "/" + pathPhoto);
            f.delete();

            pathPhoto = licensePlate + ".jpg";
            saveImage(pathPhoto, ((BitmapDrawable) imageVehicle.getDrawable()).getBitmap());
            rbitmap = getResizedBitmap(((BitmapDrawable) imageVehicle.getDrawable()).getBitmap(), 250);//Setting the Bitmap to ImageView
            userImage = getStringImage(rbitmap);
        }else{
            switch (category){
                case "Class A":
                    userImage = "A";
                    break;
                case "Class B":
                    userImage = "B";
                    break;
                case "Class C":
                    userImage = "C";
                    break;
                case "Class D":
                    userImage = "D";
                    break;
                default:
                    userImage = "Default";
            }
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
        final Veiculo veiculo = new Veiculo(brand,model,licensePlate,pathPhoto,owner,color,category,country);


        /**
         * Pedido para adicionar veiculo à base de dados (google spreadsheets)
         * **/
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(AddActivity.this, response, Toast.LENGTH_LONG).show();
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(NEW_VEHICLE, veiculo);
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(AddActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(KEY_ACTION, "insert");
                params.put(KEY_MATRICULA, licensePlate);
                params.put(KEY_PROPRIETARIO, owner);
                params.put(KEY_MARCA, brand);
                params.put(KEY_MODELO, model);
                params.put(KEY_COR, String.valueOf(color));
                params.put(KEY_IMAGE, userImage);
                params.put(KEY_CATEGORIA, category);
                params.put(KEY_COUNTRY, country);


                return params;
            }
        };

        int socketTimeout = 30000;  //30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

    /**
     * Método para alterar o tamanho do bitmap da imagem
     */
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);

    }

    /**
     * Método para transformar o bitmap em String
     * */
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;
    }

    /**
     * Método para gravar imagem localmente
     * **/
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
     * Método onClick do botão da escolha da cor
     * **/
    public void onClickBtnColor(View view) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, cor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(AddActivity.this, getString(R.string.onCancel), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                btnCor.setBackgroundColor(color);
                cor=color;
            }
        });
        colorPicker.show();
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

    /**--------------------------------------  /Métodos para a Navigation Drawer-------------------------------**/
}
