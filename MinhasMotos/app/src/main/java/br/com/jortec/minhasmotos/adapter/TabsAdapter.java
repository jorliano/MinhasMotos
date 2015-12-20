package br.com.jortec.minhasmotos.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.jortec.minhasmotos.fragments.MotoFragment;
import br.com.jortec.minhasmotos.fragments.MotosEsporteFragment;
import br.com.jortec.minhasmotos.fragments.MotosLuxoFragment;
import br.com.jortec.minhasmotos.fragments.MotosVelhasFragment;

/**
 * Created by Jorliano on 26/10/2015.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    private Context context;
    private String[] titulos = {"TODOS","LUXO","ESPORTIVOS","COLECIONADOR"};
    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);
        context = c;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;

        if(position == 0){ // ALL MOTOS
            frag = new MotoFragment();
        }
        else if(position == 1){ // LUXURY MOTOS
            frag = new MotosLuxoFragment();
        }
        else if(position == 2){ // SPORT MOTOS
            frag = new MotosEsporteFragment();
        }
        else if(position == 3){ // OLD MOTOS
            frag = new MotosVelhasFragment();
        }

        return frag;
    }

    @Override
    public int getCount() {
        return titulos.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (titulos[ position]);
    }
}
