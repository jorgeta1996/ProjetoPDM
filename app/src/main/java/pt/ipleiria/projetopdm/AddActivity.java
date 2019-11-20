package pt.ipleiria.projetopdm;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class AddActivity extends AppCompatActivity {

    private SpinnerDialog spinnerDialog;
    private ArrayList<String> items;
    private TextView textViewSpinnerDialog;
    private TextView textViewSpinnerCountriesDialog;
    private ImageView imageVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Spinner spinnerVehicle = findViewById(R.id.spinnerVehicle);
        textViewSpinnerDialog = findViewById(R.id.SpinnerMarcas);
        textViewSpinnerCountriesDialog = findViewById(R.id.SpinnerCountries);
        imageVehicle = findViewById(R.id.imageVehicle);


        spinnerVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaMotas)));
                        imageVehicle.setImageResource(R.drawable.classe_a);
                        break;
                    case 1:
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaCarros)));
                        imageVehicle.setImageResource(R.drawable.classe_b);
                        break;
                    case 2:
                        //MUDAR LISTA
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaMotas)));
                        imageVehicle.setImageResource(R.drawable.classe_c);
                        break;
                    case 3:
                        //MUDAR LISTA
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaMotas)));
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
    }

    public void onClickButtonGallery(View view) {
    }

    public void onClickButtonAdd(View view) {
    }
}
