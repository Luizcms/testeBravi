package com.luiz.saindodotedio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Lista extends AppCompatActivity {
    DbHelper db;
    Button delete;
    EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        delete = (Button)findViewById(R.id.delete);
        id = (EditText)findViewById(R.id.id);
        TextView txtlista = findViewById(R.id.view_list);

        db = new DbHelper(this);
        String data = db.getData();
        txtlista.setText(data);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i = id.getText().toString();
                if (i.equals("")){
                    Toast.makeText(Lista.this,"Insira o id que deseja excluir",Toast.LENGTH_SHORT).show();
                }else {
                    long l = Long.parseLong(i);
                    db.deleteActivity(l);
                    Toast.makeText(Lista.this,"Deletado",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}