package com.example.ne.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ne.R;
import com.example.ne.clases.listElementPermiso;
import com.example.ne.dialogs.dialogModPermiso;

import java.util.List;

public class listAdapterPermiso extends RecyclerView.Adapter<listAdapterPermiso.ViewHolder> {
    private List<listElementPermiso> dataPermisos;
    private LayoutInflater mInflaterT;
    private Context context;

    public listAdapterPermiso(List<listElementPermiso> listPermisos, Context context){
        this.mInflaterT = LayoutInflater.from(context);
        this.context = context;
        this.dataPermisos = listPermisos;
    }

    @Override
    public int getItemCount(){
        return dataPermisos.size();
    }

    @NonNull
    @Override
    public listAdapterPermiso.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflaterT.inflate(R.layout.list_element_permiso,null);
        return new listAdapterPermiso.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final listAdapterPermiso.ViewHolder holder, final int position){
        holder.bindData(dataPermisos.get(position));
    }

    public void setItems(List<listElementPermiso> items){
        dataPermisos = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView tvTCodigo, tvTNombre, tvTEstado;
        ImageButton imageButton;
        listElementPermiso permiso;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageViewTP);
            tvTCodigo = itemView.findViewById(R.id.textViewPCodigo);
            tvTNombre = itemView.findViewById(R.id.textViewPNombre);
            tvTEstado = itemView.findViewById(R.id.textViewPEstado);
            imageButton = itemView.findViewById(R.id.imageButtonSettingsTransaccion);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Modificar al permiso "+ permiso.getNombre(), Toast.LENGTH_SHORT).show();
                    openModDialog(permiso.getCodigo(),permiso.getNombre(),permiso.getEstado());
                }
            });
        }

        void bindData(final listElementPermiso item){
            permiso = item;
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            tvTCodigo.setText(item.getCodigo());
            tvTNombre.setText(item.getNombre());
            tvTEstado.setText(item.getEstado());
        }
    }

    public void openModDialog(String codigo, String nombre, String estado){
        dialogModPermiso dialog = new dialogModPermiso(codigo, nombre, estado);
        dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"");
    }
}
