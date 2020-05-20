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

public class AddPatientActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_patient);

        Button clickAdd = findViewById(R.id.btnAdd);
        clickAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });

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

        etCellphone.addTextChangedListener(Mask.insert("(##)#####-####", etCellphone));
        etPhone.addTextChangedListener(Mask.insert("(##)####-####", etPhone));
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

    private void insert () {
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
            sql.append("INSERT INTO paciente (nome_,grp_sanguineo,logradouro,numero,cidade,uf,celular,fixo) VALUES (");
            sql.append("'" + name + "', ");
            sql.append("'" + bloodType + "', ");
            sql.append("'" + address + "', ");
            sql.append(number + ", ");
            sql.append("'" + city + "', ");
            sql.append("'" + state + "', ");
            sql.append("'" + cellphone + "', ");
            sql.append("'" + phone + "'");
            sql.append(");");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Paciente inserido", Toast.LENGTH_LONG).show();
                clearFills();
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            db.close();
        }
    }
}