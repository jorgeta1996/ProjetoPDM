package pt.ipleiria.projetopdm.recycler;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;

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

    public RecyclerVehiclesAdapter(GestorVeiculos gestorVeiculos, Context context) {
        this.gestorVeiculos = gestorVeiculos;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VehiclesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_layout, parent, false);
        return new VehiclesHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiclesHolder holder, int position) {
        Veiculo mCurrent = gestorVeiculos.getVeiculos().get(position);
        holder.textViewLicPlate.setText(mCurrent.getMatricula());
        holder.textViewMarca.setText(mCurrent.getMarca());
        holder.textViewModelo.setText(mCurrent.getModelo());
        holder.textViewOwner.setText(mCurrent.getProprietario());
        holder.textViewCountry.setText(mCurrent.getCountry());
        holder.textViewcategory.setText(mCurrent.getCategoria());
        holder.textViewColor.setBackgroundColor(mCurrent.getCor()); //(?)





        if (mCurrent.getPathPhoto().isEmpty()) {
            switch (mCurrent.getCategoria()){
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
                File f=new File(context.getFilesDir() + "/" + mCurrent.getPathPhoto());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                holder.imageView.setImageBitmap(b);
            } catch (Exception e) {
                switch (mCurrent.getCategoria()){
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

    public class VehiclesHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {
        public final TextView textViewLicPlate;
        public final TextView textViewMarca;
        public final TextView textViewModelo;
        public final ImageView imageView;
        public final TextView textViewOwner;
        public final TextView textViewCountry;
        public final TextView textViewcategory;
        public final TextView textViewColor;
        public final LinearLayout layoutExpand;



        final RecyclerVehiclesAdapter mAdapter;

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
            layoutExpand  = itemView.findViewById(R.id.linearLayout_expansivo);
            layoutExpand.setVisibility(View.GONE);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (layoutExpand.getVisibility() != View.VISIBLE){
                layoutExpand.setVisibility(View.VISIBLE);

            }else{
                layoutExpand.setVisibility(View.GONE);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.getMenuInflater().inflate(R.menu.menu_item, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
            return true;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            itemPosition = getLayoutPosition();
            switch(menuItem.getItemId()){
                case R.id.itemDelete:
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle(context.getString(R.string.txtRemove));
                    dialog.setMessage(context.getString(R.string.txtConfirmRemove));

                    dialog.setPositiveButton(context.getString(R.string.txtConfirmYes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String path= gestorVeiculos.getVeiculos().get(itemPosition).getPathPhoto();
                            if (path!="") {
                                File f=new File(context.getFilesDir() + "/" + path);
                                f.delete();
                            }
                            gestorVeiculos.removerVeiculo(itemPosition);

                            mAdapter.notifyDataSetChanged();
                        }
                    });

                    dialog.setNegativeButton(context.getString(R.string.txtConfirmNo), null);
                    dialog.show();
                    break;
                case R.id.itemEdit:

                    Intent iUpdate = new Intent(context, EditActivity.class);
                    iUpdate.putExtra(MainActivity.VEICULO, gestorVeiculos.obterVeiculo(itemPosition));
                    ((Activity)context).startActivityForResult(iUpdate, MainActivity.EDIT_VEHICLE_REQUEST_CODE);
                    mAdapter.notifyDataSetChanged();;


                    break;
                case R.id.itemShare:
//                    Intent sendIntent = new Intent();
//                    sendIntent.setAction(Intent.ACTION_SEND);
//                    sendIntent.putExtra(Intent.EXTRA_TEXT,);
//                    sendIntent.setType("text/plain");
//
//                    Intent shareIntent = Intent.createChooser(sendIntent, null);
//                    startActivity(shareIntent);

                    break;

            }
            return true;
        }


    }
}
