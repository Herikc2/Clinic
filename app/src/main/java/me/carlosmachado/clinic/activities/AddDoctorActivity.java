package me.carlosmachado.clinic.activities;

import android.content.Context;
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

public class AddDoctorActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_doctor);

        Button clickAdd = findViewById(R.id.btnAdd);
        clickAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });

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

        etCellphone.addTextChangedListener(Mask.insert("(##)#####-####", etCellphone));
        etPhone.addTextChangedListener(Mask.insert("(##)####-####", etPhone));
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

    private void insert() {
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
        } else {
            db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();
            String state = spStates.getSelectedItem().toString();
            sql.append("INSERT INTO medico (nome,crm,logradouro,numero,cidade,uf,celular,fixo) VALUES (");
            sql.append("'" + name + "', ");
            sql.append("'" + CRM + "', ");
            sql.append("'" + address + "', ");
            sql.append(number + ", ");
            sql.append("'" + city + "', ");
            sql.append("'" + state + "', ");
            sql.append("'" + cellphone + "', ");
            sql.append("'" + phone + "'");
            sql.append(");");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Médico inserido", Toast.LENGTH_LONG).show();
                clearFills();
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            db.close();
        }
    }
}