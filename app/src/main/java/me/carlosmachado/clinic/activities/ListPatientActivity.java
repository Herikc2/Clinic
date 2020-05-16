package me.carlosmachado.clinic.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import me.carlosmachado.clinic.R;

public class ListPatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_list_patient);

        select();
        // TODO: Criar a list view clicável
    }

    private void select() {
        // TODO: Selecionar na tabela "patient" todas as consultas
        // TODO: Adicionar à view todas as consultas da tabela "appointment"
    }

}