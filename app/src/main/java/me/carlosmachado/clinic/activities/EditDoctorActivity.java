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

public class EditDoctorActivity extends AppCompatActivity {

    SQLiteDatabase db;

    EditText etName;
    EditText etCRM;
    EditText etAddress;
    EditText etNumber;
    EditText etCity;
    EditText etCellphone;
    EditText etPhone;

    Spinner spStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor);

        etName = findViewById(R.id.etName);
        etCRM = findViewById(R.id.etCRM);
        etAddress = findViewById(R.id.etAddress);
        etNumber = findViewById(R.id.etNumber);
        etCity = findViewById(R.id.etCity);
        etCellphone = findViewById(R.id.etCellphone);
        etPhone = findViewById(R.id.etPhone);
        spStates = findViewById(R.id.spStates);

        ArrayAdapter spArrayAdapterStates = ArrayAdapter.createFromResource(getApplicationContext(), R.array.brazil_states_array, android.R.layout.simple_list_item_checked);
        spStates.setAdapter(spArrayAdapterStates);

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
        etCRM.setText(valores.getStringExtra("CRM"));
        etAddress.setText(valores.getStringExtra("address"));
        etNumber.setText(valores.getStringExtra("number"));
        etCity.setText(valores.getStringExtra("city"));
        etCellphone.setText(valores.getStringExtra("cellPhone"));
        etPhone.setText(valores.getStringExtra("phone"));

        String statesExtras = valores.getStringExtra("state");

        String[] statesArray = getResources().getStringArray(R.array.brazil_states_array);
        int aux = 0;
        for(String s : statesArray){
            if(s.equals(statesExtras))
                break;
            aux++;
        }
        spStates.setSelection(aux);
    }

    private void clearFills(){
        etName.setText("");
        etCRM.setText("");
        etAddress.setText("");
        etNumber.setText("");
        etCity.setText("");
        etCellphone.setText("");
        etPhone.setText("");

        spStates.setSelection(0);
    }

    private void update(String id) {
        String name = etName.getText().toString().trim();
        String CRM = etCRM.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String number = etNumber.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String cellphone = etCellphone.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (name.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (CRM.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o CRM!", Toast.LENGTH_LONG).show();
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
        }
        else {
            db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();
            String state = spStates.getSelectedItem().toString();
            sql.append("UPDATE medico SET");
            sql.append(" nome = '" + name + "', ");
            sql.append("crm  = '" + CRM + "', ");
            sql.append("logradouro = '" + address + "', ");
            sql.append("numero = " + number + ", ");
            sql.append("cidade = '" + city + "', ");
            sql.append("uf = '" + state + "', ");
            sql.append("celular = '" + cellphone + "', ");
            sql.append("fixo = '" + phone + "'");
            sql.append(" where _id = '" + id + "';");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Médico atualizado", Toast.LENGTH_LONG).show();
                clearFills();
                Intent i = new Intent(getApplicationContext(), ListDoctorActivity.class);
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
        sql.append("DELETE FROM medico");
        sql.append(" where _id = '" + id + "';");

        try {
            db.execSQL(sql.toString());
            Toast.makeText(getApplicationContext(), "Médico excluido", Toast.LENGTH_LONG).show();
            clearFills();
            Intent i = new Intent(getApplicationContext(), ListPatientActivity.class);
            startActivity(i);
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        db.close();
    }

}