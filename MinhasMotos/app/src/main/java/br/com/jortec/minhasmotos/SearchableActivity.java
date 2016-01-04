package br.com.jortec.minhasmotos;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.jortec.minhasmotos.adapter.MotoAdapter;
import br.com.jortec.minhasmotos.dominio.Moto;
import br.com.jortec.minhasmotos.extras.UtilConexao;
import br.com.jortec.minhasmotos.interfaces.RecyclerViewOnclickListener;

/**
 * Created by Jorliano on 27/12/2015.
 */
public class SearchableActivity extends AppCompatActivity implements RecyclerViewOnclickListener {
    private RecyclerView recyclerView;
    private List<Moto> listaMotos;
    private List<Moto> listaAux;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    CoordinatorLayout cl_container;
    MotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle(R.string.barraPrincipal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cl_container = (CoordinatorLayout) findViewById(R.id.cl_container);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        listaMotos = (new MainActivity()).getLista(7);
        listaAux = new ArrayList<>();

        adapter = new MotoAdapter(this, listaAux);
        adapter.setRecyclerViewOnclickListener(this);
        recyclerView.setAdapter(adapter);

       // handleIntent(getIntent());
    }
    @Override
    public void onclickListener(View view, int position) {

        Moto moto = new Moto();
        moto.setModelo(listaMotos.get(position).getModelo());
        moto.setMarca(listaMotos.get(position).getMarca());
        moto.setCategoria(listaMotos.get(position).getCategoria());
        moto.setDescricao(listaMotos.get(position).getDescricao());
        moto.setFoto(listaMotos.get(position).getFoto());

        Intent intent = new Intent(this, MotoDetalheActivity.class);
        intent.putExtra("dados", moto);

        //TRANSACTION
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            View tvImagem = view.findViewById(R.id.imagem);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,Pair.create(tvImagem, "element1"));

            this.startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);
        }
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
        Toast.makeText(this, "onLongPressClickListener", Toast.LENGTH_SHORT).show();
    }

    /*@Override
     protected void onNewIntent(Intent intent) {
         setIntent(intent);
         handleIntent(intent);
     }
     private void handleIntent(Intent intent) {
         if (Intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction())) {
             String query = intent.getStringExtra(SearchManager.QUERY);

             toolbar.setTitle(query);
             filtroSarch(query);
         }
     }*/
    public void filtroSarch (String q){
       listaAux.clear();
        if(!q.equals("")) {
            for (int i = 0; i < listaMotos.size(); i++) {
                if (listaMotos.get(i).getModelo().toLowerCase().startsWith(q.toLowerCase())) {
                    listaAux.add(listaMotos.get(i));
                }
            }


            if(listaAux.isEmpty()){
                recyclerView.setVisibility( View.GONE );
                TextView tv = new TextView(this);
                tv.setText("Pesquisa não foi encontrado");
                tv.setId(1);
                tv.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                tv.setGravity(Gravity.CENTER);
                cl_container.addView(tv);
            }else if(cl_container.findViewById(1) != null){
                recyclerView.setVisibility( View.VISIBLE);
                cl_container.removeView(cl_container.findViewById(1));
            }

            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_searchable_activity, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.action_searchable_activity);
        searchView = (SearchView) item.getActionView();


        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setQueryHint(getResources().getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(getApplicationContext(), query+" onQueryTextSubmit", Toast.LENGTH_LONG).show();
                //filtroSarch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getApplicationContext(), newText+ "onQueryTextChange", Toast.LENGTH_LONG).show();
                filtroSarch(newText);
                return true;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }
      /*  else if( id == R.id.action_delete ){
            SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this,
                    SearchableProvider.AUTHORITY,
                    SearchableProvider.MODE);

            searchRecentSuggestions.clearHistory();

            Toast.makeText(this, "Cookies removidos", Toast.LENGTH_SHORT).show();
        }*/

        return true;
    }
}