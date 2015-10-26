package br.com.jortec.minhasmotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    MaterialDialog materialDialog;
    Moto moto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moto_detalhe);

        marca = (TextView) findViewById(R.id.txt_marca_detalhe);
        modelo = (TextView) findViewById(R.id.txt_modelo_detalhe);
        descricao = (TextView) findViewById(R.id.txt_descricao_detalhe);
        imagem = (ImageView) findViewById(R.id.imagem_detalhe);
        btDetalhe = (Button) findViewById(R.id.bt_detalhe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detalhe_toolbar);
        toolbar.setTitle(R.string.barraPrincipal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey("dados")){
            moto = (Moto) bundle.getSerializable("dados");

            marca.setText(moto.getMarca());
            modelo.setText(moto.getMarca());
            descricao.setText(moto.getDescricao());
            imagem.setImageResource(moto.getFoto());
        }

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
}
