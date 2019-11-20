package pt.ipleiria.projetopdm;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class AddActivity extends AppCompatActivity {

    public static final String NEW_VEHICLE = "NEWVEHICLE";
    public static final int GALLERY_REQUEST_CODE_ADD = 1;
    public static final int REQUEST_TAKE_PHOTO = 2;
    private boolean read;

    private SpinnerDialog spinnerDialog;
    private ArrayList<String> items;
    private TextView textViewSpinnerDialog;
    private TextView textViewSpinnerCountriesDialog;
    private ImageView imageVehicle;
    private int flagPicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Spinner spinnerVehicle = findViewById(R.id.spinnerVehicle);
        textViewSpinnerDialog = findViewById(R.id.SpinnerMarcas);
        textViewSpinnerCountriesDialog = findViewById(R.id.SpinnerCountries);
        imageVehicle = findViewById(R.id.imageVehicle);
        flagPicture=0;


        spinnerVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaMotas)));
                        if (flagPicture!=1)
                            imageVehicle.setImageResource(R.drawable.classe_a);
                        break;
                    case 1:
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaCarros)));
                        if (flagPicture!=1)
                            imageVehicle.setImageResource(R.drawable.classe_b);
                        break;
                    case 2:
                        //MUDAR LISTA
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaMotas)));
                        if (flagPicture!=1)
                            imageVehicle.setImageResource(R.drawable.classe_c);
                        break;
                    case 3:
                        //MUDAR LISTA
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaMotas)));
                        if (flagPicture!=1)
                            imageVehicle.setImageResource(R.drawable.classe_d);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
        spinnerDialog = new SpinnerDialog(AddActivity.this, items, "Select items");
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
        spinnerDialog = new SpinnerDialog(AddActivity.this, countries, "Select items");
        spinnerDialog.showSpinerDialog();
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                textViewSpinnerCountriesDialog.setText(item);
            }
        });
    }

    public void onClickButtonPicture(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
        }
    }

    public void onClickButtonGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.txtSelectPhoto)), GALLERY_REQUEST_CODE_ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE_ADD:
                    try {
                        Uri selectedImageUri = data.getData();
                        Glide.with(this)
                                .asBitmap()
                                .load(selectedImageUri)
                                .override(300, 300)
                                .into(imageVehicle);
                        read = true;
                        flagPicture=1;
                    } catch (Exception e) {
                        Log.e("GestorVeiculos", getString(R.string.txtErrorFile), e);
                        read = false;
                    }
                    break;
                case REQUEST_TAKE_PHOTO:
                    try {
                        if (data != null && data.getExtras() != null) {
                            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                            imageVehicle.setImageBitmap(imageBitmap);
                            read = true;
                            flagPicture=1;
                        }
                    } catch (Exception e) {
                        Log.e("GestorVeiculos", getString(R.string.txtErrorFile), e);
                        read = false;
                    }
            }
    }

    public void onClickButtonAdd(View view) {
    }
}
