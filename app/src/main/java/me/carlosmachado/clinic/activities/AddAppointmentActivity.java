package me.carlosmachado.clinic.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import me.carlosmachado.clinic.R;
import me.carlosmachado.clinic.auxliary.Alert;

public class AddAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        Button clickAdd = findViewById(R.id.btnAdd);
        clickAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });

        selectPatients();
        selectDoctors();
    }

    private void insert(){
        // TODO: Inserir na tabela "appointment" os dados da view
    }

    private void selectPatients(){
        // TODO: Pegar todos os dados da tabela "patient" e jogar no spinner "spPatient"
    }

    private void selectDoctors(){
        // TODO: Pegar todos os dados da tabela "doctor" e jogar no spinner "spDoctor"
    }

}