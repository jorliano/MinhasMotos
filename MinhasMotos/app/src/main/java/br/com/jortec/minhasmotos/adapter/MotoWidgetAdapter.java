package br.com.jortec.minhasmotos.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import br.com.jortec.minhasmotos.MainActivity;
import br.com.jortec.minhasmotos.R;
import br.com.jortec.minhasmotos.dominio.Moto;
import br.com.jortec.minhasmotos.provider.MotoWidgetProvider;

/**
 * Created by Jorliano on 03/01/2016.
 */
public class MotoWidgetAdapter implements RemoteViewsService.RemoteViewsFactory  {
    private Context context;
    private List<Moto> lista;
    private int size;

    public  MotoWidgetAdapter(Context context, Intent intent){
     this.context = context;

        float scale = context.getResources().getDisplayMetrics().density;
        size = (int)( 50 * scale + 0.5f );
    }

    @Override
    public void onCreate() {
       lista = new MainActivity().getLista(7);
    }

    @Override
    public void onDataSetChanged() {
        Collections.shuffle(lista, new Random());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_item);
        views.setTextViewText(R.id.tv_model, lista.get(position).getModelo() );
        views.setTextViewText(R.id.tv_brand, lista.get(position).getMarca());
        views.setImageViewResource(R.id.tv_moto, lista.get(position).getFoto());

        Log.i("MODELO ", "" + lista.get(position).getModelo());
        Log.i("MARCA ", ""+lista.get(position).getMarca() );
        Log.i("FOTO ", ""+lista.get(position).getFoto() );


       /* try {
            Bitmap myBitmap = Glide.with(context.getApplicationContext())
                    .load(DataUrl.getUrlCustom(lista.get(position).getFoto()), size))
                    .asBitmap()
                    .centerCrop()
                    .into(size, size)
                    .get();

            views.setImageViewBitmap(R.id.iv_car, myBitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/

        Intent itFilter = new Intent();
        itFilter.putExtra(MotoWidgetProvider.FILTER_MOTOS_ITEM, lista.get(position).getModelo());
        views.setOnClickFillInIntent(R.id.rl_container, itFilter);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
