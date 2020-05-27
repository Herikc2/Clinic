package me.carlosmachado.clinic.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import me.carlosmachado.clinic.R;
import me.carlosmachado.clinic.auxliary.Person;
import me.carlosmachado.clinic.auxliary.ValidationAppointment;

public class EditAppointmentActivity extends AppCompatActivity {

    SQLiteDatabase db;

    EditText tvDateStart;
    EditText tvDateFinal;
    EditText etDescription;

    String IdPatient;
    String IdDoctor;

    Spinner spPatients;
    Spinner spDoctors;

    int hora_inicial;
    int minuto_inicial;
    int hora_final;
    int minuto_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);

        tvDateStart = findViewById(R.id.tvStartDate);
        tvDateFinal = findViewById(R.id.tvEndDate);
        etDescription = findViewById(R.id.etIntegrates);
        spPatients = findViewById(R.id.spPatient_);
        spDoctors = findViewById(R.id.spDoctor);

        Intent valores = getIntent();

        final String id = valores.getStringExtra("id");
        fillFields(valores);

        // TODO: Colocar os dados do select na view
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

        tvDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDate("0");
            }
        });
        tvDateFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDate("1");
            }
        });
    }

    private void fillFields(Intent valores){
        tvDateStart.setText(valores.getStringExtra("dateStart"));
        tvDateFinal.setText(valores.getStringExtra("dateFinal"));
        etDescription.setText(valores.getStringExtra("description"));
        IdPatient = valores.getStringExtra("idPatient");
        IdDoctor = valores.getStringExtra("idDoctor");

        String patientsExtra = valores.getStringExtra("idPatient");

        hora_inicial = Integer.parseInt(tvDateStart.getText().toString().substring(11, 13));
        minuto_inicial = Integer.parseInt(tvDateStart.getText().toString().substring(14, 16));
        hora_final = Integer.parseInt(tvDateFinal.getText().toString().substring(11, 13));
        minuto_final = Integer.parseInt(tvDateFinal.getText().toString().substring(14, 16));

        Person[] patientsArray = selectPatients();
        for(int i = 0; i < patientsArray.length; i++)
            if(patientsArray[0].getId() == Integer.parseInt(patientsExtra))
                spPatients.setSelection(i + 1);

        String doctorExtra = valores.getStringExtra("idPatient");

        Person[] doctorsArray = selectDoctors();
        for(int i = 0; i < doctorsArray.length; i++)
            if(doctorsArray[0].getId() == Integer.parseInt(doctorExtra))
                spDoctors.setSelection(i + 1);
    }

    private void pickerDate(final String op){
        if(op.equals("0")) {
            Calendar calendar = Calendar.getInstance();

            int YEAR = calendar.get(Calendar.YEAR);
            int MONTH = calendar.get(Calendar.MONTH);
            int DATE = calendar.get(Calendar.DATE);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int date) {
                    String dateString;
                    if(month < 10) {
                        if(date < 10)
                            dateString = "0" + date + "/" + "0" + (month + 1) + "/" + year;
                        else
                            dateString = date + "/" + "0" + (month + 1) + "/" + year;
                    }
                    else {
                        if(date < 10)
                            dateString = "0" + date + "/" + (month + 1) + "/" + year;
                        else
                            dateString = date + "/" + (month + 1) + "/" + year;
                    }

                    tvDateStart.setText(dateString);
                    tvDateFinal.setText(dateString);
                    pickerTime("0");
                }
            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        } else if (op.equals("1")) {
            tvDateFinal.setText(tvDateStart.getText().subSequence(0, 10));
            pickerTime("1");
        }
    }

    private void pickerTime(final String op){
        Calendar calendar = Calendar.getInstance();
        int HOUR = 0, MINUTE = 0;
        if(op.equals("0")){
            HOUR = calendar.get(Calendar.HOUR);
            MINUTE = calendar.get(Calendar.MINUTE);
        } else if(op.equals("1")){
            HOUR = hora_inicial;
            MINUTE = minuto_inicial;
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute){
                String timeString;
                if(hour >=0 && hour < 10)
                    if(minute >=0 && minute < 10)
                        timeString = "0" + hour + ":" + "0" + minute;
                    else
                        timeString = "0" + hour + ":" + minute;
                else
                    if(minute >=0 && minute < 10)
                        timeString = hour + ":" + "0" + minute;
                    else
                        timeString = hour + ":" + minute;

                if(op.equals("0")) {
                    hora_inicial = hour;
                    minuto_inicial = minute;
                    tvDateStart.setText(tvDateStart.getText() + " " + timeString);
                }
                else if(op.equals("1")) {
                    hora_final = hour;
                    minuto_final = minute;
                    tvDateFinal.setText(tvDateFinal.getText() + " " + timeString);
                }
            }
        }, HOUR, MINUTE, true);
        timePickerDialog.show();
    }

    private boolean validationAppointment(String id) {
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT paciente_id FROM consulta");
        sql.append(" where (data_hora_inicio BETWEEN '" + tvDateStart.getText().toString() + "' and '" + tvDateFinal.getText().toString() + "'");
        sql.append(" or data_hora_fim BETWEEN '" + tvDateStart.getText().toString() + "' and '" + tvDateFinal.getText().toString() + "')");
        sql.append(" and _id != " + id + ";");
        Cursor cursor = db.rawQuery(sql.toString(), null);

        if(cursor != null) {
            if(cursor.getCount() > 0)
                return false;
        }

        db.close();
        return true;
    }

    private boolean validations(String id){
        ValidationAppointment val = new ValidationAppointment();
        String dateStart = tvDateStart.getText().toString().trim();
        String dateFinal = tvDateFinal.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (spPatients.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Por favor, informe um paciente!", Toast.LENGTH_LONG).show();
            return false;
        } else if (spDoctors.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Por favor, informe um médico!", Toast.LENGTH_LONG).show();
            return false;
        } else if (dateStart.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe a data de inicio!", Toast.LENGTH_LONG).show();
            return false;
        } else if (dateFinal.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe a data final!", Toast.LENGTH_LONG).show();
            return false;
        } else if (description.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe a descrição!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (dateFinal.equals(dateStart.substring(0, 10))){
            Toast.makeText(getApplicationContext(), "Preencha um horario para finalizar a consulta", Toast.LENGTH_LONG).show();
            return false;
        } else if(!val.validationHour(hora_inicial, hora_final, minuto_inicial, minuto_final)) {
            Toast.makeText(getApplicationContext(), "Expediente do consultório: 08:00 até 12:00 e \n" +
                    "13:30 até 17:30\n", Toast.LENGTH_LONG).show();
            return false;
        } else if(!val.checkFDS(tvDateStart.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "Expediente do consultório de Segunda a Sexta-Feira\n", Toast.LENGTH_LONG).show();
            return false;
        } else if(!validationAppointment(id)) {
            Toast.makeText(getApplicationContext(), "Já possui um agendamento para esse horário!", Toast.LENGTH_LONG).show();
            return false;
        }else if(!val.dateIsCurrent(dateStart)){
            Toast.makeText(getApplicationContext(), "Essa data já passou!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private Person[] selectPatients(){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        ArrayList<Person> patients = new ArrayList<Person>();
        Cursor cursor = db.rawQuery("SELECT _id, nome_ FROM paciente;", null);
        Person[] patientsArray = new Person[cursor.getCount()];

        final int idPos = cursor.getColumnIndex("_id");
        final int nomePos = cursor.getColumnIndex("nome_");

        patients.add(new Person(-1, "Selecione um paciente"));
        if(cursor != null && cursor.moveToFirst()){
            int aux = 0;
            do{
                patients.add(new Person(cursor.getInt(0), cursor.getString(1)));

                patientsArray[aux] = new Person(cursor.getInt(idPos), cursor.getString(nomePos));
                aux++;
            }while(cursor.moveToNext());
        }
        if(patients.size() <= 1)
            patients.set(0, new Person(-1, "Nenhum paciente cadastrado"));

        ArrayAdapter scAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, patients);
        spPatients.setAdapter(scAdapter);
        db.close();

        return patientsArray;
    }

    private Person[] selectDoctors(){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        ArrayList<Person> doctors = new ArrayList<Person>();
        Cursor cursor = db.rawQuery("SELECT _id, nome FROM medico;", null);
        Person[] doctorsArray = new Person[cursor.getCount()];

        final int idPos = cursor.getColumnIndex("_id");
        final int nomePos = cursor.getColumnIndex("nome");

        doctors.add(new Person(-1, "Selecione um médico"));
        if(cursor != null && cursor.moveToFirst()){
            int aux =  0;
            do{
                doctors.add(new Person(cursor.getInt(0), cursor.getString(1)));

                doctorsArray[aux] = new Person(cursor.getInt(idPos), cursor.getString(nomePos));
                aux++;
            }while(cursor.moveToNext());
        }

        if(doctors.size() <= 1)
            doctors.set(0, new Person(-1, "Nenhum médico cadastrado"));

        ArrayAdapter scAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, doctors);
        spDoctors.setAdapter(scAdapter);

        db.close();
        return doctorsArray;
    }

    private void clearFills(){
        etDescription.setText("");
        tvDateStart.setText("");
        tvDateFinal.setText("");

        spPatients.setSelection(0);
        spDoctors.setSelection(0);
    }

    private void update(String id){
        String dateStart = tvDateStart.getText().toString().trim();
        String dateFinal = tvDateFinal.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if(!validations(id))
            return;
        else {
            db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();
            Person patient = ((Person)spPatients.getSelectedItem());
            Person doctor = ((Person)spDoctors.getSelectedItem());

            sql.append("UPDATE consulta SET");
            sql.append(" paciente_id = '" + patient.getId() + "', ");
            sql.append("medico_id  = '" + doctor.getId() + "', ");
            sql.append("data_hora_inicio = '" + dateStart + "', ");
            sql.append("data_hora_fim  = '" + dateFinal + "', ");
            sql.append("observacao = '" + description + "'");
            sql.append(" where _id = '" + id + "';");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Consulta atualizada", Toast.LENGTH_LONG).show();
                clearFills();
                Intent i = new Intent(getApplicationContext(), ListAppointmentActivity.class);
                startActivity(i);
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            db.close();
        }
    }

    private void delete(String id){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM consulta");
        sql.append(" where _id = '" + id + "';");

        try {
            db.execSQL(sql.toString());
            Toast.makeText(getApplicationContext(), "Consulta excluida", Toast.LENGTH_LONG).show();
            clearFills();
            Intent i = new Intent(getApplicationContext(), ListAppointmentActivity.class);
            startActivity(i);
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        db.close();
    }

}