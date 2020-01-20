package pt.ipleiria.projetopdm.recycler;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.Resource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.ipleiria.projetopdm.EditActivity;
import pt.ipleiria.projetopdm.MainActivity;
import pt.ipleiria.projetopdm.R;
import pt.ipleiria.projetopdm.modelo.GestorVeiculos;
import pt.ipleiria.projetopdm.modelo.Veiculo;

public class RecyclerVehiclesAdapter extends RecyclerView.Adapter<RecyclerVehiclesAdapter.VehiclesHolder> {

    private GestorVeiculos gestorVeiculos;
    private Context context;
    private int itemPosition;
    private LayoutInflater layoutInflater;
    private ArrayList<Veiculo> listaVeiculos = new ArrayList<>();
    private HashMap<Integer, Boolean> checkBoxStates = new HashMap<>();
    private boolean longClick;

    public RecyclerVehiclesAdapter(GestorVeiculos gestorVeiculos, Context context) {
        this.gestorVeiculos = gestorVeiculos;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public VehiclesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_layout, parent, false);
        return new VehiclesHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiclesHolder holder, int position) {
        final Veiculo mCurrent = gestorVeiculos.getVeiculos().get(position);
        holder.textViewLicPlate.setText(mCurrent.getMatricula());
        holder.textViewMarca.setText(mCurrent.getMarca());
        holder.textViewModelo.setText(mCurrent.getModelo());
        holder.textViewOwner.setText(mCurrent.getProprietario());
        holder.textViewCountry.setText(mCurrent.getCountry());
        holder.textViewcategory.setText(mCurrent.getCategoria());
        holder.textViewColor.setBackgroundColor(mCurrent.getCor());

        if (mCurrent.getPathPhoto().isEmpty()) {
            switch (mCurrent.getCategoria()) {
                case "Class A":
                    holder.imageView.setImageResource(R.drawable.classe_a);
                    break;
                case "Class B":
                    holder.imageView.setImageResource(R.drawable.classe_b);
                    break;
                case "Class C":
                    holder.imageView.setImageResource(R.drawable.classe_c);
                    break;
                case "Class D":
                    holder.imageView.setImageResource(R.drawable.classe_d);
                    break;
            }
        } else {
            try {
                File f = new File(context.getFilesDir() + "/" + mCurrent.getPathPhoto());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                holder.imageView.setImageBitmap(b);
            } catch (Exception e) {
                switch (mCurrent.getCategoria()) {
                    case "Class A":
                        holder.imageView.setImageResource(R.drawable.classe_a);
                        break;
                    case "Class B":
                        holder.imageView.setImageResource(R.drawable.classe_b);
                        break;
                    case "Class C":
                        holder.imageView.setImageResource(R.drawable.classe_c);
                        break;
                    case "Class D":
                        holder.imageView.setImageResource(R.drawable.classe_d);
                        break;
                }
            }
        }

        if (listaVeiculos.contains(mCurrent))
            holder.checkbox.setChecked(true);
        else
            holder.checkbox.setChecked(false);

        holder.checkbox.setVisibility(checkBoxStates.get(position) != null && !checkBoxStates.get(position) ? View.GONE : View.VISIBLE);

        holder.itemView.setLongClickable(true);
        holder.itemView.setClickable(true);
    }

    @Override
    public int getItemCount() {
        return gestorVeiculos.getVeiculos().size();
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public class VehiclesHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final TextView textViewLicPlate;
        public final TextView textViewMarca;
        public final TextView textViewModelo;
        public final ImageView imageView;
        public final TextView textViewOwner;
        public final TextView textViewCountry;
        public final TextView textViewcategory;
        public final TextView textViewColor;
        public final LinearLayout layoutExpand;
        public final CheckBox checkbox;
        public final Button mItemDelete;
        public final Button mItemEdit;
        public final Button mItemShare;

        public final RecyclerVehiclesAdapter mAdapter;


        public VehiclesHolder(@NonNull View itemView, RecyclerVehiclesAdapter adapter) {
            super(itemView);
            textViewModelo = itemView.findViewById(R.id.textViewModelo);
            textViewMarca = itemView.findViewById(R.id.textViewMarca);
            textViewLicPlate = itemView.findViewById(R.id.textViewLicPlate);
            imageView = itemView.findViewById(R.id.imageView);
            textViewOwner = itemView.findViewById(R.id.textViewOwner);
            textViewCountry = itemView.findViewById(R.id.textViewCountry);
            textViewcategory = itemView.findViewById(R.id.textViewClass);
            textViewColor = itemView.findViewById(R.id.textViewColor);
            layoutExpand = itemView.findViewById(R.id.linearLayout_expansivo);
            layoutExpand.setVisibility(View.GONE);
            checkbox = itemView.findViewById(R.id.checkBoxItem);
            checkbox.setVisibility(View.GONE);


            mItemDelete = itemView.findViewById(R.id.buttonEliminar);
            mItemEdit = itemView.findViewById(R.id.buttonEditar);
            mItemShare = itemView.findViewById(R.id.buttonShare);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            for (int i = 0; i < gestorVeiculos.getVeiculos().size(); i++) {
                checkBoxStates.put(i, false);
                checkbox.setVisibility(checkBoxStates.get(i) != null && !checkBoxStates.get(i) ? View.GONE : View.VISIBLE);
            }

            /**  Método onClick do botão EDIT  **/
            mItemEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemPosition = getLayoutPosition();
                    Intent iEdit = new Intent(context, EditActivity.class);
                    iEdit.putExtra(MainActivity.VEICULO,gestorVeiculos.obterVeiculo(itemPosition));
                    ((Activity)context).startActivityForResult(iEdit, MainActivity.EDIT_VEHICLE_REQUEST_CODE);
                }
            });
            /**  Método onClick do botão DELETE  **/
            mItemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemPosition = getLayoutPosition();
                    deleteVehicle(itemPosition);
                }
            });
            /**  Método onClick do botão SHARE  **/
            mItemShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


//                    Intent sendIntent = new Intent();
//
//                    String filePath = null;
//                    Bitmap bitmap = null;
//                    Bitmap icon =null;
//                    Uri uri =null;
//                    ImageView imgView =null;
//                    Uri imgUri = Uri.parse(gestorVeiculos.obterVeiculo(itemPosition).getPathPhoto());
//
//                    if(gestorVeiculos.obterVeiculo(itemPosition).getPathPhoto().isEmpty()){
//
//                        switch (gestorVeiculos.obterVeiculo(itemPosition).getCategoria()){
//                            case "Class A":
//                               // bitmap =BitmapFactory.decodeResource(context.getResources(),R.drawable.classe_b);
//
//
//                                icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.classe_a);
//                                  break;
//                            case "Class B":
//                                 bitmap =BitmapFactory.decodeResource(context.getResources(),R.drawable.classe_b);
//                                 break;
//                            case "Class C":
//                                 bitmap =BitmapFactory.decodeResource(context.getResources(),R.drawable.classe_c);
//                                 break;
//                            case "Class D":
//                                 bitmap =BitmapFactory.decodeResource(context.getResources(),R.drawable.classe_d);
//                                 break;
//                        }
//                    }else{
//                        filePath=gestorVeiculos.obterVeiculo(itemPosition).getPathPhoto();
//                    }
//
//                    String aaa =context.getResources().getString((R.string.VehicleInfo)) + "\n\n"
//                            + context.getResources().getString((R.string.licensePlateString)) +": "+gestorVeiculos.obterVeiculo(itemPosition).getMatricula()+"\n"+
//                            context.getResources().getString((R.string.textViewOwner)) +": "+gestorVeiculos.obterVeiculo(itemPosition).getProprietario()+"\n"+
//                            context.getResources().getString((R.string.country)) +": "+gestorVeiculos.obterVeiculo(itemPosition).getCountry()+"\n"+
//                            context.getResources().getString((R.string.category)) +": "+gestorVeiculos.obterVeiculo(itemPosition).getCategoria()+"\n"+
//                            context.getResources().getString((R.string.textView_Make)) +": "+gestorVeiculos.obterVeiculo(itemPosition).getMarca()+"\n"+
//                            context.getResources().getString((R.string.textview_model)) +": "+gestorVeiculos.obterVeiculo(itemPosition).getModelo()+"\n"+
//                            context.getResources().getString((R.string.txtView_color)) +": "+gestorVeiculos.obterVeiculo(itemPosition).getCor()+"\n"+
//                            context.getResources().getString((R.string.photo)) +":\n";
//
//
//                    sendIntent.setAction(Intent.ACTION_SEND);
//                    sendIntent.setType("image/jpg");//setType// ("application/image");
//                     // sendIntent.putExtra(Intent.EXTRA_SUBJECT, );
//                    sendIntent.putExtra(Intent.EXTRA_TEXT,  aaa);
//               //     sendIntent.putExtra(Intent.EXTRA_STREAM, R.drawable.classe_a);
//                    context.startActivity(sendIntent);
//

//                    Uri imageuri2=Uri.parse("android.resource://"+context.getPackageName()+"/"+R.drawable.classe_a);
//
//                    Intent share = new Intent(Intent.ACTION_SEND);
//                    share.setType("image/jpg");
//                    share.putExtra(Intent.EXTRA_STREAM, imageuri2);
//                    context.startActivity(Intent.createChooser(share, "Share Image using"));
//




                }
            });

        }

        @Override
        public void onClick(View view) {
            itemPosition=getLayoutPosition();
            if (checkbox.getVisibility() != View.VISIBLE) {
                if (layoutExpand.getVisibility() != View.VISIBLE) {
                    layoutExpand.setVisibility(View.VISIBLE);
                } else {
                    layoutExpand.setVisibility(View.GONE);
                }
            } else if (checkbox.isChecked()) {

                checkbox.setChecked(false);
                listaVeiculos.remove(gestorVeiculos.getVeiculos().get(itemPosition));
            } else {
                checkbox.setChecked(true);
                listaVeiculos.add(gestorVeiculos.getVeiculos().get(itemPosition));
            }
        }

        @Override
        public boolean onLongClick(View view) {
            for (int i = 0; i < gestorVeiculos.getVeiculos().size(); i++) {
                if (longClick) {
                    checkBoxStates.put(i, false);
                } else {
                    checkBoxStates.put(i, true);
                }
                checkbox.setVisibility(checkBoxStates.get(i) != null && !checkBoxStates.get(i) ? View.GONE : View.VISIBLE);
                notifyItemChanged(i);
            }

            if (longClick) {
                longClick = false;
                listaVeiculos.clear();
                notifyDataSetChanged();
            } else
                longClick = true;

            return true;
        }

        private void deleteVehicle(final int position){
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(context.getString(R.string.txtRemove));
            dialog.setMessage(context.getString(R.string.txtConfirmRemove));

            dialog.setPositiveButton(context.getString(R.string.txtConfirmYes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    String path = gestorVeiculos.getVeiculos().get(position).getPathPhoto();
                    if (path != "") {
                        File f = new File(context.getFilesDir() + "/" + path);
                        f.delete();
                    }
                    gestorVeiculos.removerVeiculo(position);

                    mAdapter.notifyDataSetChanged();
                }
            });

            dialog.setNegativeButton(context.getString(R.string.txtConfirmNo), null);
            dialog.show();
        }
    }

    public ArrayList<Veiculo> getListaVeiculos(){
        return listaVeiculos;
    }

    public Veiculo getVeiculoByIndex(int index){
        return listaVeiculos.get(index);
    }

    public void clearListaVeiculos(){
        listaVeiculos.clear();
        longClick=false;
        for (int i = 0; i < gestorVeiculos.getVeiculos().size(); i++) {
            checkBoxStates.put(i, false);
        }
    }

}
