package pt.ipleiria.projetopdm.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import pt.ipleiria.projetopdm.R;
import pt.ipleiria.projetopdm.modelo.GestorVeiculos;

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
        View itemView = layoutInflater.inflate(R.layout.item_layout, parent, false);//POR ACABAR
        return new VehiclesHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiclesHolder holder, int position) {
        //POR ACABAR
    }

    @Override
    public int getItemCount() {
        return gestorVeiculos.getVeiculos().size();
    }

    public class VehiclesHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {
        public VehiclesHolder(@NonNull View itemView, RecyclerVehiclesAdapter adapter) {
            //POR ACABAR
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            return false;
        }
    }
}
