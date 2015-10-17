package br.com.jortec.minhasmotos.interfaces;

import android.view.View;

/**
 * Created by Jorliano on 15/10/2015.
 */
public interface RecyclerViewOnclickListener {
    public void onclickListener(View view, int position);
    public void onLongPressClickListener(View view, int position);
}
