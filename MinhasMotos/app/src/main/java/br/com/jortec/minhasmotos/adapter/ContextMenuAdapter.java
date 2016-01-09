package br.com.jortec.minhasmotos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.jortec.minhasmotos.R;
import br.com.jortec.minhasmotos.dominio.ContextMenuItem;

/**
 * Created by Jorliano on 06/01/2016.
 */
public class ContextMenuAdapter extends BaseAdapter {
    private Context context;
    private List<ContextMenuItem> lista;
    private LayoutInflater layoutInflater;
    private int extraPadding;

    public ContextMenuAdapter(Context context, List<ContextMenuItem> lista){
        this.context = context;
        this.lista = lista;
        layoutInflater = LayoutInflater.from(context);

        float scale = context.getResources().getDisplayMetrics().density;
        extraPadding = (int)(8 * scale + 0.5f);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if( convertView == null ){
            convertView = layoutInflater.inflate(R.layout.context_menu, parent, false);
            holder = new ViewHolder();
            convertView.setTag( holder );

            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvLabel = (TextView) convertView.findViewById(R.id.tv_label);
            holder.vwDivider = convertView.findViewById(R.id.vw_divider);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ivIcon.setImageResource( lista.get(position).getIcon() );
        holder.tvLabel.setText( lista.get(position).getLabel() );


        /*/ BACKGROUND
        if( position == 0 ){
            ((ViewGroup) convertView).getChildAt(0).setBackgroundResource( R.drawable.context_menu_top_background );
        }
        else if( position == lista.size() - 1 ){
            ((ViewGroup) convertView).getChildAt(0).setBackgroundResource( R.drawable.context_menu_bottom_background );
        }
        else{
            ((ViewGroup) convertView).getChildAt(0).setBackgroundResource( R.drawable.context_menu_middle_background );
        }*/

        // H_LINE
            holder.vwDivider.setVisibility( position == lista.size() - 2 ? View.VISIBLE : View.GONE );

        // EXTRA PADDING
            ((ViewGroup) convertView).getChildAt(0).setPadding(0,
                    position == 0 || position == lista.size() - 1 ? extraPadding : 0,
                    0,
                    position == lista.size() - 1 ? extraPadding : 0);

        return convertView;
    }

    public static class ViewHolder{
        ImageView ivIcon;
        TextView tvLabel;
        View vwDivider;
    }
}
