package br.com.jortec.minhasmotos;

import android.content.DialogInterface;
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
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import br.com.jortec.minhasmotos.dominio.Moto;
import me.drakeet.materialdialog.MaterialDialog;

public class MotoDetalheActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
                                                                      TimePickerDialog.OnTimeSetListener,
                                                                      DialogInterface.OnCancelListener{
    private TextView marca;
    private TextView modelo;
    private TextView descricao;
    private TextView agendarTeste;
    private ImageView imagem;
    private Button btDetalhe;
    private Button btAgendarTeste;
    private ViewGroup viewGroup;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    MaterialDialog materialDialog;
    Moto moto;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
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
        agendarTeste = (TextView) findViewById(R.id.tv_test_drive);
        imagem = (ImageView) findViewById(R.id.imagem);
        btDetalhe = (Button) findViewById(R.id.bt_detalhe);
        btAgendarTeste = (Button) findViewById(R.id.bt_test_drive);
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

        btAgendarTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedukeTesteDrive(v);
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

    //TESTE DRIVE
        private int ano, mes, dia, hora ,minuto;

        public void schedukeTesteDrive(View v){
            initData();
            Calendar calendarDefault = Calendar.getInstance();
            calendarDefault.set(ano,mes,dia);

            DatePickerDialog dataPickerDialog = DatePickerDialog.newInstance(
                    this,
                    calendarDefault.get(Calendar.YEAR),
                    calendarDefault.get(Calendar.MONTH),
                    calendarDefault.get(Calendar.DAY_OF_MONTH));

            Calendar cMin = Calendar.getInstance();
            Calendar cMax = Calendar.getInstance();
            cMax.set(cMax.get(Calendar.YEAR), 11, 31);
            dataPickerDialog.setMinDate(cMin);
            dataPickerDialog.setMaxDate(cMax);

            List<Calendar> dayList = new LinkedList<>();
            Calendar[] dayArray;
            Calendar cAux = Calendar.getInstance();

            while(cAux.getTimeInMillis() <= cMax.getTimeInMillis()){
                if(cAux.get(Calendar.DAY_OF_WEEK) != 1 && cAux.get(Calendar.DAY_OF_WEEK) != 7 ){
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(cAux.getTimeInMillis());

                    dayList.add(c);
                }
                cAux.setTimeInMillis(cAux.getTimeInMillis() + (24 * 60 * 60 * 100));
            }
            dayArray = new Calendar[dayList.size()];
            for (int i = 0; i < dayArray.length; i ++){
                dayArray[i] = dayList.get(i);
            }

            dataPickerDialog.setSelectableDays(dayArray);
            dataPickerDialog.setOnCancelListener(this);
            dataPickerDialog.show(getFragmentManager(), "DataPickerDialog");
        }

        public void initData(){
            if(ano == 0) {
                Calendar c = Calendar.getInstance();
                ano = c.get(Calendar.YEAR);
                mes = c.get(Calendar.MONTH);
                dia = c.get(Calendar.DAY_OF_MONTH);
                hora = c.get(Calendar.HOUR_OF_DAY);
                minuto = c.get(Calendar.MINUTE);
            }
        }

        @Override
        public void onDateSet(DatePickerDialog datePickerDialog, int i, int i1, int i2) {
            Calendar tDefault = Calendar.getInstance();
            tDefault.set(ano, mes, dia, hora, minuto);
            ano = i;
            mes = i1;
            dia = i2;

            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                    this,
                    tDefault.get(Calendar.HOUR_OF_DAY),
                    tDefault.get(Calendar.MINUTE),
                    true
            );
            timePickerDialog.setOnCancelListener(this);
            timePickerDialog.show(getFragmentManager(),"TimePickerDialog");
        }

        @Override
        public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1, int i2) {
            if(i < 9 || i >18){
                onDateSet(null, ano, mes, dia);
                Toast.makeText(this, "Somente entre 9h as 18h",Toast.LENGTH_SHORT).show();
                return;
            }
            hora = i;
            minuto = i1;

            agendarTeste.setText((dia < 10 ? "0" + dia : dia) + "/" +
                                 (mes < 10 ? "0"+(mes+1) : mes+1)+"/"+
                                  ano+" as "+
                                 (hora < 10 ? "0" + hora : hora)+"h"+
                                 (minuto < 10 ? "0" + minuto : minuto) );
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            ano = mes = dia = hora = minuto = 0;
            agendarTeste.setText("");
        }
}
