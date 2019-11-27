package pt.ipleiria.projetopdm;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import pt.ipleiria.projetopdm.modelo.Veiculo;
import yuku.ambilwarna.AmbilWarnaDialog;

public class AddActivity extends AppCompatActivity {

    public static final String NEW_VEHICLE = "NEWVEHICLE";
    public static final int GALLERY_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 2;
    private boolean read;

    private SpinnerDialog spinnerDialog;
    private ArrayList<String> items;
    private TextView textViewSpinnerDialog;
    private TextView textViewSpinnerCountriesDialog;
    private EditText editTextModel;
    private ImageView imageVehicle;
    private String category;
    private Button btnCor;
    private int cor;
    private String pathPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Spinner spinnerVehicle = findViewById(R.id.spinnerVehicle);
        textViewSpinnerDialog = findViewById(R.id.SpinnerMarcas);
        textViewSpinnerCountriesDialog = findViewById(R.id.SpinnerCountries);
        imageVehicle = findViewById(R.id.imageVehicle);
        editTextModel = findViewById(R.id.editTextModel);
        btnCor = findViewById(R.id.buttonColor);
        btnCor.setBackgroundColor(Color.RED);
        cor = Color.RED;


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
                        //MUDAR LISTA
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaMotas)));
                        if (!read)
                            imageVehicle.setImageResource(R.drawable.classe_c);
                            pathPhoto="";
                        break;
                    case 3:
                        category="Class D";
                        //MUDAR LISTA
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaMotas)));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

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

    public void onClickButtonPicture(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    public void onClickButtonGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.txtSelectPhoto)), GALLERY_REQUEST_CODE);
    }

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

    public void onClickButtonAdd(View view) {
        EditText editTextOwner = findViewById(R.id.editTextOwner);
        EditText editTextPlate = findViewById(R.id.editTextPlate);

        String owner = editTextOwner.getText().toString();
        String licensePlate = editTextPlate.getText().toString().trim();
        String country = textViewSpinnerCountriesDialog.getText().toString();
        String brand = textViewSpinnerDialog.getText().toString();
        String model = editTextModel.getText().toString();
        int color = cor;

        if (brand.trim().isEmpty() || model.trim().isEmpty()|| licensePlate.trim().isEmpty()|| owner.trim().isEmpty()|| color==0|| category.trim().isEmpty()|| country.trim().isEmpty()) {
            Toast.makeText(this, R.string.txtFillData, Toast.LENGTH_LONG).show();
            return;
        }

        if (read) {

            File f=new File(this.getFilesDir() + "/" + pathPhoto);
            f.delete();

            pathPhoto = licensePlate + ".jpg";
            saveImage(pathPhoto, ((BitmapDrawable) imageVehicle.getDrawable()).getBitmap());
        }
        Veiculo veiculo = new Veiculo(brand,model,licensePlate,pathPhoto,owner,color,category,country);


        Intent returnIntent = new Intent();
        returnIntent.putExtra(NEW_VEHICLE, veiculo);
        setResult(RESULT_OK, returnIntent);
        finish();
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
}
