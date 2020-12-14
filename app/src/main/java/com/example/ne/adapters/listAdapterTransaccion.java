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
import com.example.ne.clases.listElementTrabajador;
import com.example.ne.clases.listElementTransaccion;
import com.example.ne.dialogs.dialogModTrabajador;
import com.example.ne.dialogs.dialogModTransaccion;

import java.util.List;

public class listAdapterTransaccion extends RecyclerView.Adapter<listAdapterTransaccion.ViewHolder> {
    private List<listElementTransaccion> dataTransacciones;
    private LayoutInflater mInflaterT;
    private Context context;

    public listAdapterTransaccion(List<listElementTransaccion> dataTransacciones, Context context){
        this.mInflaterT = LayoutInflater.from(context);
        this.context = context;
        this.dataTransacciones = dataTransacciones;
    }

    @Override
    public int getItemCount(){
        return dataTransacciones.size();
    }

    @NonNull
    @Override
    public listAdapterTransaccion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflaterT.inflate(R.layout.list_element_transaccion,null);
        return new listAdapterTransaccion.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final listAdapterTransaccion.ViewHolder holder, final int position){
        holder.bindData(dataTransacciones.get(position));
    }

    public void setItems(List<listElementTransaccion> items){
        dataTransacciones = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView tvTPCodigo, tvTPPermiso, tvTPTrabajador, tvTPHoras, tvTPEstado;
        ImageButton imageButton;
        listElementTransaccion transaccion;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageViewTP);
            tvTPCodigo = itemView.findViewById(R.id.textViewTPCodigo);
            tvTPPermiso = itemView.findViewById(R.id.textViewTPPermiso);
            tvTPTrabajador = itemView.findViewById(R.id.textViewTPTrabajador);
            tvTPHoras = itemView.findViewById(R.id.textViewTPHoras);
            tvTPEstado = itemView.findViewById(R.id.textViewTPEstado);

            imageButton = itemView.findViewById(R.id.imageButtonSettingsTransaccion);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(v.getContext(),"Modificar a la transacción "+ transaccion.getCodigo(), Toast.LENGTH_SHORT).show();
                    openModDialog(
                            transaccion.getCodigo(),
                            transaccion.getPermiso(),
                            transaccion.getTrabajador(),
                            transaccion.getHoras(),
                            transaccion.getEstado()
                    );
                }
            });
        }

        void bindData(final listElementTransaccion item){
            transaccion = item;
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            tvTPCodigo.setText(item.getCodigo());
            tvTPPermiso.setText(item.getPermiso());
            tvTPTrabajador.setText(item.getTrabajador());
            tvTPHoras.setText(item.getHoras());
            tvTPEstado.setText(item.getEstado());
        }
    }

    public void openModDialog(String codigo, String permiso, String trabajador, String horas, String estado){

        dialogModTransaccion dialog = new dialogModTransaccion(codigo, permiso, trabajador, horas, estado);
        dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"");

    }
}
