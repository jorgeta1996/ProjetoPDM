package pt.ipleiria.projetopdm.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;

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





            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //POR FAZER
        }

        @Override
        public boolean onLongClick(View view) {
            //POR FAZER
            return false;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            //POR FAZER
            return false;
        }


    }
}
