package com.example.clase07;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clase07.entity.Empleado;

public class ListaEmpleadosAdapter
        extends RecyclerView.Adapter<ListaEmpleadosAdapter.EmpleadoViewHolder> {

    private Empleado[] listaEmpleados;
    private Context contexto;
    private int employeeIdFontBold;

    public ListaEmpleadosAdapter(Empleado[] listaEmpleados,
                                 Context contexto, int employeeIdFontBold) {
        this.listaEmpleados = listaEmpleados;
        this.contexto = contexto;
        this.employeeIdFontBold = employeeIdFontBold;
    }

    @NonNull
    @Override
    public EmpleadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(contexto).inflate(R.layout.item_rv, parent, false);
        EmpleadoViewHolder empleadoViewHolder = new EmpleadoViewHolder(itemview);
        return empleadoViewHolder;
    }

    @Override
    public void onBindViewHolder(EmpleadoViewHolder holder, int position) {
        Empleado empleado = listaEmpleados[position];
        holder.empleado = empleado;
        holder.contextoVh = contexto;
        holder.employeeIdFontBoldVh = employeeIdFontBold;
        holder.llenarViewHolder();
    }

    @Override
    public int getItemCount() {
        return listaEmpleados.length;
    }

    public void actualizarEmpleado(int empIdFontBold) {
        int index = 0;
        for (int i = 0; i < listaEmpleados.length; i++) {
            if(listaEmpleados[i].getEmployeeId() == empIdFontBold){
                index = i;
                break;
            }
        }
        listaEmpleados[index].setFirst_name("Pedrito");
        employeeIdFontBold = empIdFontBold;
        this.notifyItemChanged(index);
    }


    public static class EmpleadoViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public Empleado empleado;
        public Context contextoVh;
        public int employeeIdFontBoldVh;

        public EmpleadoViewHolder(final View itemview) {
            super(itemview);
            this.textView = itemview.findViewById(R.id.textView3);
            Button botonEditarEmpleado = itemview.findViewById(R.id.btnEditarEmp);

            botonEditarEmpleado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("infoApp", "clic boton " + empleado.getEmployeeId());
                    Intent intent = new Intent(contextoVh, EmpleadoActivity.class);
                    intent.putExtra("id", empleado.getEmployeeId());
                    contextoVh.startActivity(intent);
                }
            });
        }

        public void llenarViewHolder() {
            String data = empleado.getEmployeeId() +
                    " / " + empleado.getFirst_name() +
                    " / " + empleado.getLast_name();
            textView.setText(data);

            if (empleado.getEmployeeId() == employeeIdFontBoldVh) {
                Log.d("infoApp", String.valueOf(empleado.getEmployeeId()));
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD_ITALIC);
            } else {
                textView.setTypeface(null, Typeface.ITALIC);
            }
        }

    }

}
