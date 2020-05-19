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

public class ListDoctorActivity extends AppCompatActivity {

    SQLiteDatabase db;

    ListView lvDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_doctor);

        lvDoctor = findViewById(R.id.lvDoctors);

        lvDoctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = lvDoctor.getChildAt(position);
                TextView tvListId = v.findViewById(R.id.tvListId);
                TextView tvListName = v.findViewById(R.id.tvListName);
                TextView tvListCRM = v.findViewById(R.id.tvListCRM);
                TextView tvListAddress = v.findViewById(R.id.tvListAddress);
                TextView tvListNumber = v.findViewById(R.id.tvListNumber);
                TextView tvListCity = v.findViewById(R.id.tvListCity);
                TextView tvListState = v.findViewById(R.id.tvListState);
                TextView tvListCellPhone = v.findViewById(R.id.tvListCellPhone);
                TextView tvListPhone = v.findViewById(R.id.tvListPhone);

                Intent i = new Intent(getApplicationContext(), EditDoctorActivity.class);
                i.putExtra("id", tvListId.getText().toString());
                i.putExtra("name", tvListName.getText().toString());
                i.putExtra("CRM", tvListCRM.getText().toString());
                i.putExtra("address", tvListAddress.getText().toString());
                i.putExtra("number", tvListNumber.getText().toString());
                i.putExtra("city", tvListCity.getText().toString());
                i.putExtra("state", tvListState.getText().toString());
                i.putExtra("cellPhone", tvListCellPhone.getText().toString());
                i.putExtra("phone", tvListPhone.getText().toString());
                startActivity(i);
            }
        });

        select();
    }

    private void select() {
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM medico;");
        Cursor dados_doctor = db.rawQuery(sql.toString(), null);
        String[] from = {"_id", "nome", "crm", "logradouro", "numero", "cidade", "uf", "celular", "fixo"};
        int[] to = {R.id.tvListId, R.id.tvListName, R.id.tvListCRM, R.id.tvListAddress, R.id.tvListNumber, R.id.tvListCity, R.id.tvListState, R.id.tvListCellPhone, R.id.tvListPhone};

        SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.dados_doctor, dados_doctor, from, to, 0);
        lvDoctor.setAdapter(scAdapter);

        db.close();
    }

}