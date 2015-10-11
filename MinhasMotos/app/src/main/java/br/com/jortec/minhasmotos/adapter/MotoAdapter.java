package br.com.jortec.minhasmotos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.jortec.minhasmotos.R;
import br.com.jortec.minhasmotos.dominio.Moto;

/**
 * Created by Jorliano on 11/10/2015.
 */
public class MotoAdapter extends RecyclerView.Adapter<MotoAdapter.MyViewHolder> {
    private List<Moto> listaMotos;
    private LayoutInflater inflater;

    public MotoAdapter(Context c, List l){
        listaMotos = l;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.item_moto, viewGroup, false);
        MyViewHolder mvh= new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        viewHolder.imagem.setImageResource (listaMotos.get(position).getFoto());
        viewHolder.modelo.setText(listaMotos.get(position).getModelo());
        viewHolder.marca.setText(listaMotos.get(position).getMarca());
    }

    @Override
    public int getItemCount() {
        return listaMotos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView modelo;
        public TextView marca;
        public ImageView imagem;

        public MyViewHolder(View itemView) {
            super(itemView);

            modelo = (TextView) itemView.findViewById(R.id.txModelo);
            marca = (TextView) itemView.findViewById(R.id.txMarca);
            imagem = (ImageView) itemView.findViewById(R.id.imagem);

        }
    }
}
