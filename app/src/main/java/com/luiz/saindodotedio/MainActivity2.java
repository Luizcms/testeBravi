package com.luiz.saindodotedio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    DbHelper db;
    TextView descricao,tipo,situacao;
    EditText id;
    Button start,stop,salvar,listar,andamento,realizado,desistencia,search,update;
    Chronometer chronometer;
    String i,desc,ty,time,sit;
    long stopTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        descricao = (TextView)findViewById(R.id.txtdescricao);
        tipo = (TextView)findViewById(R.id.txttipo);
        situacao = (TextView)findViewById(R.id.situacao);
        start = (Button)findViewById(R.id.start);
        stop = (Button)findViewById(R.id.stop);
        salvar = (Button)findViewById(R.id.salvar);
        andamento = (Button)findViewById(R.id.andamento);
        realizado = (Button)findViewById(R.id.realizado);
        desistencia = (Button)findViewById(R.id.desistencia);
        listar = (Button)findViewById(R.id.view);
        search = (Button)findViewById(R.id.search);
        update = (Button)findViewById(R.id.update);
        id = (EditText) findViewById(R.id.id);
        descricao = findViewById(R.id.txtdescricao);
        tipo = findViewById(R.id.txttipo);

        stopTime = 0;

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String description = bundle.getString("description");
        String type = bundle.getString("type");
        //String key = bundle.getString("key");


        descricao.setText(description);
        tipo.setText(type);

        db= new DbHelper(this);

        andamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                situacao.setText("atividade em andamento");
            }
        });
        realizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                situacao.setText("realizado");
            }
        });
        desistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                situacao.setText("desistiu");
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer = findViewById(R.id.chronometer);

                chronometer.setBase(SystemClock.elapsedRealtime()- stopTime) ;
                chronometer.start();
                situacao.setText("atividade em andamento");
                //Toast.makeText(MainActivity2.this,"ok",Toast.LENGTH_SHORT).show();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chronometer = findViewById(R.id.chronometer);

                 stopTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                 chronometer.stop();
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer = findViewById(R.id.chronometer);
                String desc = descricao.getText().toString();
                String t = tipo.getText().toString();
                String time = chronometer.getText().toString();
                String situation = situacao.getText().toString();

                db.insertActivity(desc,t,time,situation);
                Intent intent = new Intent(MainActivity2.this,Lista.class);
                startActivity(intent);

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = id.getText().toString();
                if (i.equals("")){
                    Toast.makeText(MainActivity2.this,"Insira o código",Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        long l1 = Long.parseLong(id.getText().toString());
                        desc=db.getDescription(l1);
                        ty=db.getType(l1);
                        time=db.getTime(l1);
                        sit=db.getSituation(l1);

                        descricao.setText(desc);
                        tipo.setText(ty);
                        chronometer.setText(time);
                        situacao.setText(sit);

                    }catch (Exception e){
                        Toast.makeText(MainActivity2.this,"Código inválido!",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id.length()<1){
                    Toast.makeText(MainActivity2.this,"insira o id",Toast.LENGTH_SHORT).show();
                }else{
                    Chronometer chronometer = findViewById(R.id.chronometer);
                    String desc = descricao.getText().toString();
                    String t = tipo.getText().toString();
                    String time = chronometer.getText().toString();
                    String situation = situacao.getText().toString();
                    long i = Long.parseLong(id.getText().toString());

                    db.updateActivity(i,desc,t,time,situation);
                    Intent intent = new Intent(MainActivity2.this,Lista.class);
                    startActivity(intent);

                }

            }
        });
        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity2.this,Lista.class);
                startActivity(intent);

            }
        });


    }
}