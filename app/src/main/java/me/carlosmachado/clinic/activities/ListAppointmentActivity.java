package me.carlosmachado.clinic.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.carlosmachado.clinic.R;

public class ListAppointmentActivity extends AppCompatActivity {

    SQLiteDatabase db;

    ListView lvAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_appointment);

        lvAppointment = findViewById(R.id.lvAppointment);

        lvAppointment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = lvAppointment.getChildAt(position);
                TextView tvListId = v.findViewById(R.id.tvListId);
                TextView tvListIdPatient = v.findViewById(R.id.tvListIdPatient);
                TextView tvListIdDoctor = v.findViewById(R.id.tvListIdDoctor);
                TextView tvListPatient = v.findViewById(R.id.tvListPatient);
                TextView tvListDoctor = v.findViewById(R.id.tvListDoctor);
                TextView tvListDateStart = v.findViewById(R.id.tvListDateStart);
                TextView tvListDateFinal = v.findViewById(R.id.tvListDateFinal);
                TextView tvListDescription = v.findViewById(R.id.tvListDescription);

                Intent i = new Intent(getApplicationContext(), EditAppointmentActivity.class);
                i.putExtra("id", tvListId.getText().toString());
                i.putExtra("idPatient", tvListIdPatient.getText().toString());
                i.putExtra("idDoctor", tvListIdDoctor.getText().toString());
                i.putExtra("patient", tvListPatient.getText().toString());
                i.putExtra("doctor", tvListDoctor.getText().toString());
                i.putExtra("dateStart", tvListDateStart.getText().toString());
                i.putExtra("dateFinal", tvListDateFinal.getText().toString());
                i.putExtra("description", tvListDescription.getText().toString());
                startActivity(i);
            }
        });

        select();
    }

    private void select() {
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.*, p.nome_, m.nome FROM consulta c, paciente p, medico m");
        sql.append(" where c.paciente_id = p._id and c.medico_id = m._id;");
        Cursor dados_appointment = db.rawQuery(sql.toString(), null);
        String[] from = {"_id", "paciente_id", "medico_id", "nome_", "nome", "data_hora_inicio", "data_hora_fim", "observacao"};
        int[] to = {R.id.tvListId, R.id.tvListIdPatient, R.id.tvListIdDoctor, R.id.tvListPatient, R.id.tvListDoctor, R.id.tvListDateStart, R.id.tvListDateFinal, R.id.tvListDescription,};

        SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.dados_appointment, dados_appointment, from, to, 0);
        lvAppointment.setAdapter(scAdapter);

        db.close();
        // TODO: Selecionar na tabela "appointment" todas as consultas
        // TODO: Adicionar à view todas as consultas da tabela "appointment"
    }

}