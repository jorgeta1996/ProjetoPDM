package pt.ipleiria.projetopdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.ipleiria.projetopdm.modelo.GestorVeiculos;
import pt.ipleiria.projetopdm.modelo.Veiculo;
import pt.ipleiria.projetopdm.recycler.RecyclerVehiclesAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private GestorVeiculos gestorVeiculos;
    private RecyclerView mRecyclerView;
    private RecyclerVehiclesAdapter mAdapter;

    public static final int ADD_VEHICLE_REQUEST_CODE = 1;
    private static final String ESTADO_GESTOR_VEICULOS = "ESTADO_GESTOR_VEICULOS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            this.gestorVeiculos = gestorVeiculos.getInstance();
//            this.gestorVeiculos.lerFicheiro(this);
        } else {
            this.gestorVeiculos = (GestorVeiculos) savedInstanceState.getSerializable(ESTADO_GESTOR_VEICULOS);
        }

        mRecyclerView = findViewById(R.id.recyclerViewMain);
        mAdapter = new RecyclerVehiclesAdapter(gestorVeiculos,this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void onClickAddVehicle(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, ADD_VEHICLE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_VEHICLE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Veiculo newVeiculo = (Veiculo) data.getSerializableExtra(AddActivity.NEW_VEHICLE);
                    if (!(gestorVeiculos.getVeiculos().contains(newVeiculo)))
                        gestorVeiculos.adicionarVeiculo(newVeiculo);
                    else
                        gestorVeiculos.atualizarVeiculo(gestorVeiculos.getVeiculos().indexOf(newVeiculo), newVeiculo);
                    mAdapter.notifyDataSetChanged();
                }
                break;
//            case REQUEST_CODE_UPDATE_CONTACT:
//                if (resultCode == RESULT_OK) {
//                    Contacto updateContact = (Contacto) data.getSerializableExtra(UpdateActivity.UPDATE_CONTACT);
//                    gestorContactos.atualizarContacto(mAdapter.getmPosition(), updateContact);
//                    mAdapter.notifyDataSetChanged();
//                }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ESTADO_GESTOR_VEICULOS, gestorVeiculos);

    }
}
