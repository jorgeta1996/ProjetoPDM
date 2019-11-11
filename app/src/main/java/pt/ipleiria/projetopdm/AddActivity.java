package pt.ipleiria.projetopdm;

import android.app.Activity;
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

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(AddActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.countries));
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVehicle.setAdapter(adapterSpinner);
    }
}
