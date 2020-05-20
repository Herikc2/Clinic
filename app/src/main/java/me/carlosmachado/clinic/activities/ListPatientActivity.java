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

public class ListPatientActivity extends AppCompatActivity {

    SQLiteDatabase db;

    ListView lvPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_list_patient);

        lvPatient = findViewById(R.id.lvAppointment);

        select();
        lvPatient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = lvPatient.getChildAt(position);
                TextView tvListId = v.findViewById(R.id.tvListId);
                TextView tvListName = v.findViewById(R.id.tvListName);
                TextView tvListBloodType = v.findViewById(R.id.tvListBloodType);
                TextView tvListAddress = v.findViewById(R.id.tvListAddress);
                TextView tvListNumber = v.findViewById(R.id.tvListNumber);
                TextView tvListCity = v.findViewById(R.id.tvListCity);
                TextView tvListState = v.findViewById(R.id.tvListState);
                TextView tvListCellPhone = v.findViewById(R.id.tvListCellPhone);
                TextView tvListPhone = v.findViewById(R.id.tvListPhone);

                Intent i = new Intent(getApplicationContext(), EditPatientActivity.class);
                i.putExtra("id", tvListId.getText().toString());
                i.putExtra("name", tvListName.getText().toString());
                i.putExtra("bloodType", tvListBloodType.getText().toString());
                i.putExtra("address", tvListAddress.getText().toString());
                i.putExtra("number", tvListNumber.getText().toString());
                i.putExtra("city", tvListCity.getText().toString());
                i.putExtra("state", tvListState.getText().toString());
                i.putExtra("cellPhone", tvListCellPhone.getText().toString());
                i.putExtra("phone", tvListPhone.getText().toString());
                startActivity(i);
            }
        });
    }

    private void select() {
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM paciente;");
        Cursor dados_patient = db.rawQuery(sql.toString(), null);
        String[] from = {"_id", "nome_", "grp_sanguineo", "logradouro", "numero", "cidade", "uf", "celular", "fixo"};
        int[] to = {R.id.tvListId, R.id.tvListName, R.id.tvListBloodType, R.id.tvListAddress, R.id.tvListNumber, R.id.tvListCity, R.id.tvListState, R.id.tvListCellPhone, R.id.tvListPhone};

        SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.dados_patient, dados_patient, from, to, 0);
        lvPatient.setAdapter(scAdapter);

        db.close();
    }

}