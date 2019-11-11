package pt.ipleiria.projetopdm;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Spinner spinnerVehicle = findViewById(R.id.spinnerVehicle);
        Spinner spinnerCountries = findViewById(R.id.spinnerCountries);

        ArrayAdapter<String> adapterSpinnerVehicle = new ArrayAdapter<String>(AddActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.classes));
        adapterSpinnerVehicle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVehicle.setAdapter(adapterSpinnerVehicle);

        ArrayAdapter<String> adapterSpinnerCountries = new ArrayAdapter<String>(AddActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.countries));
        adapterSpinnerCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountries.setAdapter(adapterSpinnerCountries);
    }
}
