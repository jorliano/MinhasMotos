package br.com.jortec.minhasmotos.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import br.com.jortec.minhasmotos.MainActivity;
import br.com.jortec.minhasmotos.R;
import br.com.jortec.minhasmotos.service.MotoWidgetService;

/**
 * Created by Jorliano on 03/01/2016.
 */
public class MotoWidgetProvider extends AppWidgetProvider {
    public static final String LOAD_MOTOS = "br.com.jotlianoo.minhasmotos.provider.LOAD_MOTOS";
    public static final String FILTER_MOTOS = "br.com.jotlianoo.minhasmotos.provider.FILTER_MOTOS";
    public static final String FILTER_MOTOS_ITEM = "br.com.jotlianoo.minhasmotos.provider.FILTER_MOTOS_ITEM";

    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        if( intent != null ){
            if( !intent.getAction().equals( LOAD_MOTOS ) ){
                int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

                if( appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID ){
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_collection);
                    onUpdate(context, appWidgetManager, new int[]{appWidgetId});
                }
            }
            else if( !intent.getAction().equals( FILTER_MOTOS ) ){
                String modelo = intent.getStringExtra(FILTER_MOTOS_ITEM);

                Intent it = new Intent(context, MainActivity.class);
                it.putExtra(FILTER_MOTOS_ITEM, modelo);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            }
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i =0; i<appWidgetIds.length; i++){
            Intent itService = new Intent(context, MotoWidgetService.class);
            itService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_collection);
            views.setRemoteAdapter(R.id.lv_collection, itService);
            views.setEmptyView(R.id.lv_collection, R.id.tv_loading);

            Intent itLoadMotos = new Intent(context, MotoWidgetProvider.class);
            itLoadMotos.setAction( LOAD_MOTOS );
            itLoadMotos.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            PendingIntent piLoadCars = PendingIntent.getBroadcast(context, 0, itLoadMotos, 0);
            views.setOnClickPendingIntent(R.id.iv_update_collection, piLoadCars);

            Intent itOpen = new Intent(context, MainActivity.class);
            PendingIntent piOpen = PendingIntent.getActivity(context, 0, itOpen, 0);
            views.setOnClickPendingIntent(R.id.iv_open_app, piOpen);

            Intent itFilterMoto = new Intent(context, MotoWidgetProvider.class);
            itFilterMoto.setAction(FILTER_MOTOS);
            PendingIntent piFilterCar = PendingIntent.getBroadcast(context, 0, itFilterMoto, 0);
            views.setPendingIntentTemplate(R.id.lv_collection, piFilterCar);

            appWidgetManager.updateAppWidget(appWidgetIds[i], views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[i], R.id.lv_collection);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
