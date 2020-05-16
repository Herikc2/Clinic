package me.carlosmachado.clinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.carlosmachado.clinic.activities.AddAppointmentActivity;
import me.carlosmachado.clinic.activities.AddDoctorActivity;
import me.carlosmachado.clinic.activities.AddPatientActivity;
import me.carlosmachado.clinic.activities.ListAppointmentActivity;
import me.carlosmachado.clinic.activities.ListDoctorActivity;
import me.carlosmachado.clinic.activities.ListPatientActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButtons();
    }

    private void initializeButtons(){
        Button clickAddAppointment = findViewById(R.id.btnAddAppointment);
        clickAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddAppointmentActivity.class);
                startActivity(i);
            }
        });
        Button clickAddDoctor = findViewById(R.id.btnAddDoctor);
        clickAddDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddDoctorActivity.class);
                startActivity(i);
            }
        });
        Button clickAddPatient = findViewById(R.id.btnAddPatient);
        clickAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddPatientActivity.class);
                startActivity(i);
            }
        });

        Button clickListAppointment = findViewById(R.id.btnListAppointment);
        clickListAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListAppointmentActivity.class);
                startActivity(i);
            }
        });
        Button clickListDoctor = findViewById(R.id.btnListDoctor);
        clickListDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListDoctorActivity.class);
                startActivity(i);
            }
        });
        Button clickListPatient = findViewById(R.id.btnListPatient);
        clickListPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListPatientActivity.class);
                startActivity(i);
            }
        });
    }

}
