package me.carlosmachado.clinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import me.carlosmachado.clinic.activities.AddAppointmentActivity;
import me.carlosmachado.clinic.activities.AddDoctorActivity;
import me.carlosmachado.clinic.activities.AddPatientActivity;
import me.carlosmachado.clinic.activities.ListAppointmentActivity;
import me.carlosmachado.clinic.activities.ListDoctorActivity;
import me.carlosmachado.clinic.activities.ListPatientActivity;
import me.carlosmachado.clinic.dao.Database;

public class MainActivity extends AppCompatActivity {

    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        criarDB();
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

    private void criarDB(){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS medico (");
        sql.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("nome VARCHAR(125), ");
        sql.append("crm VARCHAR(20), ");
        sql.append("logradouro VARCHAR(125), ");
        sql.append("numero MEDIUMINT(8), ");
        sql.append("cidade VARCHAR(60), ");
        sql.append("uf VARCHAR(50), ");
        sql.append("celular VARCHAR(20), ");
        sql.append("fixo VARCHAR(20)");
        sql.append(");");

        sql.append("CREATE TABLE IF NOT EXISTS paciente(");
        sql.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("nome VARCHAR(125), ");
        sql.append("grp_sanguineo VARCHAR(5), ");
        sql.append("logradouro VARCHAR(125), ");
        sql.append("numero MEDIUMINT(8), ");
        sql.append("cidade VARCHAR(50), ");
        sql.append("uf VARCHAR(50), ");
        sql.append("celular VARCHAR(20), ");
        sql.append("fixo VARCHAR(20)");
        sql.append(");");

        sql.append("CREATE TABLE IF NOT EXISTS consulta (");
        sql.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("paciente_id INTEGER NOT NULL, ");
        sql.append("medico_id INTEGER NOT NULL, ");
        sql.append("data_hora_inicio DATETIME, ");
        sql.append("data_hora_fim DATETIME, ");
        sql.append("observacao VARCHAR(200), ");
        sql.append("FOREIGN KEY(paciente_id) REFERENCES paciente(id), ");
        sql.append("FOREIGN KEY(medico_id) REFERENCES medico(id)");
        sql.append(");");

        try {
            String[] queries = sql.toString().split(";");
            for(String query : queries){
                db.execSQL(query);
            }
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        db.close();
    }

}
