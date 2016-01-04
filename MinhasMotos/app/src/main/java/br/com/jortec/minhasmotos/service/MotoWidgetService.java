package br.com.jortec.minhasmotos.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import br.com.jortec.minhasmotos.adapter.MotoWidgetAdapter;

/**
 * Created by Jorliano on 03/01/2016.
 */
public class MotoWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MotoWidgetAdapter(this, intent);
    }

}
