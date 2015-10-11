package br.com.jortec.minhasmotos;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import br.com.jortec.minhasmotos.dominio.Moto;
import br.com.jortec.minhasmotos.fragments.MotoFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.barraPrincipal);
        setSupportActionBar(toolbar);

        //Fragments
        MotoFragment frag = (MotoFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(frag == null){
            frag = new MotoFragment();
            FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
            fts.replace(R.id.rl_fragment_container, frag, "mainFrag");
            fts.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Moto> getLista(int qtd){
        String modelos[] = new String[]{"Fan 125","Fan 150","Bros 160","Factor","Hornet","Falcon","Fazer 250"};
        String marcas[] = new String[]{"Honda","Yamaha","Suzuki","Dafra","Honda","Importada","Desconhecida"};
        int foto[] = new int[]{R.drawable.moto1,R.drawable.moto2,R.drawable.moto3,R.drawable.moto4,R.drawable.moto5,R.drawable.moto6,R.drawable.moto7};

        List<Moto> lista = new ArrayList<Moto>();
        for (int i = 0; i < qtd; i++){
            Moto m = new Moto();
            m.setModelo(modelos[i]);
            m.setMarca(marcas[i]);
            m.setFoto(foto[i]);

            lista.add(m);
        }
        return lista;
    }
}
