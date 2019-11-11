package pt.ipleiria.projetopdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipleiria.projetopdm.modelo.GestorVeiculos;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listViewV;
    private GestorVeiculos gestorVeiculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        Intent i = getIntent();
        listViewV =  findViewById(R.id.listview_searchActivity);
        listViewV.setOnItemClickListener(this);

    }


    public void onClickProcurar(View view) {
        EditText editText_search = findViewById(R.id.editText_searchMatricula);
        String matriculaToSearch = editText_search.getText().toString();

//        ArrayList<GestorVeiculos> searchedVehicles = gestorVeiculos.procurarmatricula(matriculaToSearch);
//        ArrayAdapter<GestorVeiculos> adapter = new ArrayAdapter<GestorVeiculos>(this, android.R.layout.simple_list_item_1, searchedVehivles);

//        if (searchedVehicles.isEmpty()) {
//            Toast.makeText(this, getString(R.string.VehiclesNotFoundString), Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}


