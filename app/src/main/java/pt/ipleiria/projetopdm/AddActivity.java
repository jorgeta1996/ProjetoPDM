package pt.ipleiria.projetopdm;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;


import androidx.appcompat.app.AppCompatActivity;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class AddActivity extends AppCompatActivity {

    private SpinnerDialog spinnerDialog;
    private ArrayList<String> items;
    private TextView textViewSpinnerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Spinner spinnerVehicle = findViewById(R.id.spinnerVehicle);
        Spinner spinnerCountries = findViewById(R.id.spinnerCountries);




        ArrayAdapter<String> adapterSpinnerVehicle = new ArrayAdapter<>(AddActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.classes));
        adapterSpinnerVehicle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVehicle.setAdapter(adapterSpinnerVehicle);


        spinnerVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaMotas)));
                        spinnerDialog = new SpinnerDialog(AddActivity.this, items, "Select items");
                        break;
                    case 1:
                        items = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.listaCarros)));
                        spinnerDialog = new SpinnerDialog(AddActivity.this, items, "Select items");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//        ArrayAdapter<String> adapterSpinnerCountries = new ArrayAdapter<>(AddActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.countries));
//        adapterSpinnerCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCountries.setAdapter(adapterSpinnerCountries);



        textViewSpinnerDialog = findViewById(R.id.textViewTesteSpinner);
        spinnerDialog = new SpinnerDialog(AddActivity.this, items, "Select items");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                textViewSpinnerDialog.setText(item);
            }
        });

        textViewSpinnerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });
    }
}
