package pt.ipleiria.projetopdm;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pt.ipleiria.projetopdm.modelo.Veiculo;

public class EditActivity extends AppCompatActivity {


    private EditText editText_Matricula;
    private EditText editText_Owner;
    private EditText editText_Model;

    private TextView textViewSpinnerMaker;
    private TextView textViewSpinnerCountriesDialog;

//    private ImageView imageVehicle;
//
//    private Button btnCor;
//    private int cor;
//    private String pathPhoto;

    public static final String EDIT_CONTACT = "EDITVEHICLE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);



        Veiculo v = (Veiculo) getIntent().getSerializableExtra(MainActivity.VEICULO);



        editText_Matricula = findViewById(R.id.editTextPlateEdit);
        editText_Matricula.setText(v.getMatricula());
        editText_Owner = findViewById(R.id.editTextOwnerEdit);
        editText_Owner.setText(v.getProprietario());
        editText_Model= findViewById(R.id.editTextModelEdit);
        editText_Model.setText(v.getModelo());





//
//
//        imageView = findViewById(R.id.imageViewUpdate);
//
//        if (c.getPathPhoto().trim().isEmpty()) {
//            imageView.setImageResource(R.drawable.ic_no_photo);
//        } else {
//            try {
//                File f = new File(this.getFilesDir() + "/" + c.getPathPhoto());
//                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//                imageView.setImageBitmap(b);
//            } catch (Exception e) {
//                imageView.setImageResource(R.drawable.ic_no_photo);
//            }
//        }
    }


    public void onClickSpinnerCountriesEdit(View view) {
    }

    public void onClickButtonEdit(View view) {
    }

    public void onClickButtonGalleryEdit(View view) {
    }

    public void onClickButtonPictureEdit(View view) {
    }

    public void onClickBtnColorEdit(View view) {
    }

    public void onClickSpinnerMarcasEdit(View view) {
    }
}
