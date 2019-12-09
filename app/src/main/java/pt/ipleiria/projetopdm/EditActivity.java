package pt.ipleiria.projetopdm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pt.ipleiria.projetopdm.modelo.Veiculo;
import yuku.ambilwarna.AmbilWarnaDialog;

public class EditActivity extends AppCompatActivity {


    private EditText editText_Matricula;
    private EditText editText_Owner;
    private EditText editText_Model;
    private Button btnCor;

    private TextView textViewSpinnerMaker;
    private TextView textViewSpinnerCountriesDialog;

//    private ImageView imageVehicle;
//

     private int cor;
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
        btnCor = findViewById(R.id.buttonColorEdit);
        btnCor.setBackgroundColor(v.getCor());





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
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, cor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(EditActivity.this, getString(R.string.onCancel), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                btnCor.setBackgroundColor(color);
                cor=color;
            }
        });
        colorPicker.show();
    }

    public void onClickSpinnerMarcasEdit(View view) {
    }
}
