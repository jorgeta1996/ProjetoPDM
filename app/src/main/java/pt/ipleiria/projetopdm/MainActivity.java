package pt.ipleiria.projetopdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TESTE
    }


    public void onClickAddVehicle(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void onClickLista(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
