package me.carlosmachado.clinic.activities;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import me.carlosmachado.clinic.R;
import me.carlosmachado.clinic.auxliary.Mask;

public class EditPatientActivity extends AppCompatActivity {

    SQLiteDatabase db;

    EditText etName;
    EditText etAddress;
    EditText etNumber;
    EditText etCity;
    EditText etCellphone;
    EditText etPhone;

    Spinner spBloodTypes;
    Spinner spStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etNumber = findViewById(R.id.etNumber);
        etCity = findViewById(R.id.etCity);
        etCellphone = findViewById(R.id.etCellphone);
        etPhone = findViewById(R.id.etPhone);
        spBloodTypes = findViewById(R.id.spBloodTypes);
        spStates = findViewById(R.id.spStates);

        ArrayAdapter spArrayAdapterStates = ArrayAdapter.createFromResource(getApplicationContext(), R.array.brazil_states_array, android.R.layout.simple_list_item_checked);
        spStates.setAdapter(spArrayAdapterStates);

        ArrayAdapter spArrayAdapterBlood = ArrayAdapter.createFromResource(getApplicationContext(), R.array.blood_types_array, android.R.layout.simple_list_item_checked);
        spBloodTypes.setAdapter(spArrayAdapterBlood);

        Intent valores = getIntent();
        fillFields(valores);

        final String id = valores.getStringExtra("id");

        etCellphone.addTextChangedListener(Mask.insert("(##)#####-####", etCellphone));
        etPhone.addTextChangedListener(Mask.insert("(##)####-####", etPhone));

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

    private void fillFields(Intent valores){
        etName.setText(valores.getStringExtra("name"));
        etAddress.setText(valores.getStringExtra("address"));
        etNumber.setText(valores.getStringExtra("number"));
        etCity.setText(valores.getStringExtra("city"));
        etCellphone.setText(valores.getStringExtra("cellPhone"));
        etPhone.setText(valores.getStringExtra("phone"));

        String statesExtras = valores.getStringExtra("state");
        String bloodTypeExtras = valores.getStringExtra("bloodType");

        String[] statesArray = getResources().getStringArray(R.array.brazil_states_array);
        int aux = 0;
        for(String s : statesArray){
            if(s.equals(statesExtras))
                break;
            aux++;
        }
        spStates.setSelection(aux);

        String[] bloodTypeArray = getResources().getStringArray(R.array.blood_types_array);
        aux = 0;
        for(String bt : bloodTypeArray){
            if(bt.equals(bloodTypeExtras))
                break;
            aux++;
        }
        spBloodTypes.setSelection(aux);
    }

    private void clearFills(){
        etName.setText("");
        etAddress.setText("");
        etNumber.setText("");
        etCity.setText("");
        etCellphone.setText("");
        etPhone.setText("");

        spBloodTypes.setSelection(0);
        spStates.setSelection(0);
    }

    private void update(String id) {
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String number = etNumber.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String cellphone = etCellphone.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (name.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (address.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o endereço!", Toast.LENGTH_LONG).show();
        } else if (number.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o numero!", Toast.LENGTH_LONG).show();
        } else if (city.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe a cidade!", Toast.LENGTH_LONG).show();
        } else if (cellphone.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o celular!", Toast.LENGTH_LONG).show();
        } else if (phone.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o telefone!", Toast.LENGTH_LONG).show();
        } else if (spStates.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o seu estado!", Toast.LENGTH_LONG).show();
        } else if (spBloodTypes.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o seu tipo sanguingeo!", Toast.LENGTH_LONG).show();
        } else {
            db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();
            String bloodType = spBloodTypes.getSelectedItem().toString();
            String state = spStates.getSelectedItem().toString();
            sql.append("UPDATE paciente SET");
            sql.append(" nome_ = '" + name + "', ");
            sql.append("grp_sanguineo  = '" + bloodType + "', ");
            sql.append("logradouro = '" + address + "', ");
            sql.append("numero = " + number + ", ");
            sql.append("cidade = '" + city + "', ");
            sql.append("uf = '" + state + "', ");
            sql.append("celular = '" + cellphone + "', ");
            sql.append("fixo = '" + phone + "'");
            sql.append(" where _id = '" + id + "';");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Paciente atualizado", Toast.LENGTH_LONG).show();
                clearFills();
                Intent i = new Intent(getApplicationContext(), ListPatientActivity.class);
                startActivity(i);
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            db.close();
        }
    }

    private void delete (String id){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM paciente");
        sql.append(" where _id = '" + id + "';");

        try {
            db.execSQL(sql.toString());
            Toast.makeText(getApplicationContext(), "Paciente excluido", Toast.LENGTH_LONG).show();
            clearFills();
            Intent i = new Intent(getApplicationContext(), ListPatientActivity.class);
            startActivity(i);
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        db.close();
    }
}