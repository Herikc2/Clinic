package me.carlosmachado.clinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import me.carlosmachado.clinic.R;

public class EditPatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);

        Intent values = getIntent();
        final String id = values.getStringExtra("id");

        select(id);
        // TODO: Colocar os dados do select na view

        Button clickEdit = findViewById(R.id.btnEdit);
        clickEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(id);
            }
        });

        Button clickDelete = findViewById(R.id.btnDelete);
        clickDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(id);
            }
        });
    }

    private void select(String id){
        // TODO: Selecionar na tabela "patient" os dados da view a partir do id
    }

    private void update(String id){
        // TODO: Atualizar na tabela "patient" os dados da view a partir do id
    }

    private void delete(String id){
        // TODO: Deletar na tabela "patient" a partir do id
    }

}