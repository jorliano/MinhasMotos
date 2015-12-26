package br.com.jortec.minhasmotos;

import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.jortec.minhasmotos.dominio.Moto;
import me.drakeet.materialdialog.MaterialDialog;

public class MotoDetalheActivity extends AppCompatActivity {
    private TextView marca;
    private TextView modelo;
    private TextView descricao;
    private ImageView imagem;
    private Button btDetalhe;
    private ViewGroup viewGroup;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    MaterialDialog materialDialog;
    Moto moto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TRASECTION
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            /*getWindow().setEnterTransition(new Explode().setDuration(3000));
            getWindow().setReturnTransition(new Fade().setDuration(3000));*/
            getWindow().setSharedElementEnterTransition(new ChangeBounds());

            Transition transition = getWindow().getSharedElementEnterTransition();
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {}
                @Override
                public void onTransitionEnd(Transition transition) {
                    TransitionManager.beginDelayedTransition(viewGroup, new Slide());
                    descricao.setVisibility(View.VISIBLE);
                    btDetalhe.setVisibility(View.VISIBLE);
                }
                @Override
                public void onTransitionCancel(Transition transition) {  }
                @Override
                public void onTransitionPause(Transition transition) {}
                @Override
                public void onTransitionResume(Transition transition) {}
            });
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moto_detalhe);

        marca = (TextView) findViewById(R.id.txMarca);
        modelo = (TextView) findViewById(R.id.txModelo);
        descricao = (TextView) findViewById(R.id.txt_descricao_detalhe);
        imagem = (ImageView) findViewById(R.id.imagem);
        btDetalhe = (Button) findViewById(R.id.bt_detalhe);
        viewGroup = (ViewGroup) findViewById(R.id.ll_descricao);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("dados")){
            moto = (Moto) bundle.getSerializable("dados");

            marca.setText(moto.getMarca());
            modelo.setText(moto.getModelo());
            descricao.setText(moto.getDescricao());
            imagem.setImageResource(moto.getFoto());
        }

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle( moto.getModelo());

        Toolbar toolbar = (Toolbar) findViewById(R.id.detalhe_toolbar);
        toolbar.setTitle(R.string.barraPrincipal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         btDetalhe.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 materialDialog = new MaterialDialog(v.getContext())
                         .setTitle("MaterialDialog")
                         .setMessage("Hello world!")
                         .setBackgroundResource(R.drawable.moto3)
                         .setPositiveButton("OK", new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 materialDialog.dismiss();
                             }
                         })
                         .setNegativeButton("CANCEL", new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 materialDialog.dismiss();
                             }
                         });

                 materialDialog.show();

             }
         });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_moto_detalhe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return true;
    }
    @Override
    public void onBackPressed(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            TransitionManager.beginDelayedTransition(viewGroup, new Slide());
           descricao.setVisibility(View.INVISIBLE);
            btDetalhe.setVisibility(View.INVISIBLE);
        }
        super.onBackPressed();
    }
}
