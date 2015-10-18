package br.com.jortec.minhasmotos;


import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

import br.com.jortec.minhasmotos.dominio.Moto;
import br.com.jortec.minhasmotos.fragments.MotoFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Drawer.Result navegadorDrawer;
    private AccountHeader.Result accountHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.barraPrincipal);
        setSupportActionBar(toolbar);

        //FRAGMENTO
        MotoFragment frag = (MotoFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if (frag == null) {
            frag = new MotoFragment();
            FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
            fts.replace(R.id.rl_fragment_container, frag, "mainFrag");
            fts.commit();
        }

        // NAVIGATION DRAWER
        // HEADER
        accountHeader = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(true)
                //.withHeaderBackground(R.drawable.tema)
                .withHeaderBackground(android.R.drawable.screen_background_dark_transparent)
                .addProfiles(
                        new ProfileDrawerItem().withName("Jorliano").withEmail("jorliano@gmail.com").withIcon(getResources().getDrawable(R.drawable.moto7))
                )
                .build();

        // BODY

        navegadorDrawer = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.START)
                .withSavedInstance(savedInstanceState)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_moto_luxo),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_moto_popular),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_settings),
                        new SwitchDrawerItem().withName(R.string.drawer_item_notificacao).withChecked(true)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(MainActivity.this, "onItemClick: " + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(MainActivity.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();
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

    public List<Moto> getLista(int qtd) {
        String modelos[] = new String[]{"Fan 125", "Fan 150", "Bros 160", "Factor", "Hornet", "Falcon", "Fazer 250"};
        String marcas[] = new String[]{"Honda", "Yamaha", "Suzuki", "Dafra", "Honda", "Importada", "Desconhecida"};
        int foto[] = new int[]{R.drawable.moto1, R.drawable.moto2, R.drawable.moto3, R.drawable.moto4, R.drawable.moto5, R.drawable.moto6, R.drawable.moto7};

        List<Moto> lista = new ArrayList<Moto>();
        for (int i = 0; i < qtd; i++) {
            Moto m = new Moto();
            m.setModelo(modelos[i]);
            m.setMarca(marcas[i]);
            m.setFoto(foto[i]);

            lista.add(m);
        }
        return lista;
    }
}
