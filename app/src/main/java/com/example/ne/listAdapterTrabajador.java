package com.example.ne;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class listAdapterTrabajador extends RecyclerView.Adapter<listAdapterTrabajador.ViewHolder> {
    private List<listElementTrabajador> dataTrabajadores;
    private LayoutInflater mInflaterT;
    private Context context;

    public listAdapterTrabajador(List<listElementTrabajador> listTrabajadores, Context context){
        this.mInflaterT = LayoutInflater.from(context);
        this.context = context;
        this.dataTrabajadores = listTrabajadores;
    }

    @Override
    public int getItemCount(){
        return dataTrabajadores.size();
    }

    @NonNull
    @Override
    public listAdapterTrabajador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflaterT.inflate(R.layout.list_element_trabajador,null);
        return new listAdapterTrabajador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final listAdapterTrabajador.ViewHolder holder, final int position){
        holder.bindData(dataTrabajadores.get(position));
    }

    public void setItems(List<listElementTrabajador> items){
        dataTrabajadores = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView tvTCodigo, tvTNombre, tvTEstado;
        ImageButton imageButton;
        listElementTrabajador trabajador;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            tvTCodigo = itemView.findViewById(R.id.textViewTCodigo);
            tvTNombre = itemView.findViewById(R.id.textViewTNombre);
            tvTEstado = itemView.findViewById(R.id.textViewTEstado);
            imageButton = itemView.findViewById(R.id.imageButtonSettingsTrabajador);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Modificar al trabajador "+ trabajador.getNombre(), Toast.LENGTH_SHORT).show();
                    openAddDialog(trabajador.getCodigo(),trabajador.getNombre(),trabajador.getEstado());
                }
            });
        }

        void bindData(final listElementTrabajador item){
            trabajador = item;
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            tvTCodigo.setText(item.getCodigo());
            tvTNombre.setText(item.getNombre());
            tvTEstado.setText(item.getEstado());
        }
    }

    public void openAddDialog(String codigo, String nombre, String estado){
        dialogModTrabajador dialog = new dialogModTrabajador(codigo, nombre, estado);
        dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"");
    }
}
