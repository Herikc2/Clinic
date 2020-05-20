package me.carlosmachado.clinic.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import me.carlosmachado.clinic.R;
import me.carlosmachado.clinic.auxliary.Person;

public class AddAppointmentActivity extends AppCompatActivity {

    SQLiteDatabase db;

    EditText tvDateStart;
    EditText tvDateFinal;
    EditText etDescription;

    Spinner spPatients;
    Spinner spDoctors;

    int hora_inicial;
    int minuto_inicial;
    int hora_final;
    int minuto_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        tvDateStart = findViewById(R.id.tvStartDate);
        tvDateFinal = findViewById(R.id.tvEndDate);
        etDescription = findViewById(R.id.etIntegrates);
        spPatients = findViewById(R.id.spPatient_);
        spDoctors = findViewById(R.id.spDoctor);

        Button clickAdd = findViewById(R.id.btnAdd);
        clickAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
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

        selectPatients();
        selectDoctors();
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
                    String dateString = "";
                    if(month < 10) {
                        dateString = date + "/" + "0" + (month + 1) + "/" + year;
                    }
                    else {
                        dateString = date + "/" + (month + 1) + "/" + year;
                    }

                    if(op.equals("0")) {
                        tvDateStart.setText(dateString);
                        tvDateFinal.setText(dateString);
                        pickerTime("0");
                    }
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
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute){
                String timeString = "";
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

    private void clearFills(){
        etDescription.setText("");
        tvDateStart.setText("");
        tvDateFinal.setText("");

        spPatients.setSelection(0);
        spDoctors.setSelection(0);
    }

    private boolean validationHour(){
        if((hora_inicial >= 8 && hora_inicial <= 12) || (hora_inicial >= 13 && hora_inicial <= 17)){
            if(hora_inicial == 12 && minuto_inicial > 0)
                return false;
            if(hora_inicial == 13 && minuto_inicial < 30)
                return false;
            if(hora_inicial ==  17 && minuto_inicial > 30)
                return false;
        } else
            return false;

        if((hora_final >= 8 && hora_final <= 12) || (hora_final >= 13 && hora_final <= 17)){
            if(hora_final == 12 && minuto_final > 0)
                return false;
            if(hora_final == 13 && minuto_final < 30)
                return false;
            if(hora_final ==  17 && minuto_final > 30)
                return false;
        } else
            return false;

        if(hora_final < hora_inicial)
            return false;
        if(hora_inicial == hora_final && minuto_final <= minuto_inicial)
            return false;

        if(hora_inicial >= 8 && hora_inicial <= 12){
            if(hora_final > 12 || (hora_final == 12 && minuto_final > 0))
                return false;
            return true;
        }

        if(hora_inicial >= 13 && hora_inicial <= 17){
            if(hora_final == 13 && minuto_final < 30)
                return false;
            if(hora_final == 17 && minuto_final > 30)
                return false;
            if(hora_final < 13 || hora_final > 17)
                return false;
            return true;
        }

        return true;
    }

    private boolean validationAppointment(){
        String a = tvDateStart.getText().toString();
        String b = tvDateFinal.getText().toString();

        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT paciente_id FROM consulta");
        sql.append(" where data_hora_inicio BETWEEN '" + tvDateStart.getText().toString() + "' and '" + tvDateFinal.getText().toString() + "';");
        //sql.append(" or data_hora_inicio BETWEEN '" + tvDateStart.getText().toString() + "' and '" + tvDateFinal.getText().toString() + "';");
        //sql.append(" where data_hora_inicio BETWEEN '" + tvDateStart.getText().toString() + "' and '" + tvDateFinal.getText().toString() + "';");
        Cursor cursor = db.rawQuery(sql.toString(), null);

        if(cursor != null) {
            if(cursor.getCount() > 0)
                return false;
        }

        db.close();
        return true;
    }

    private boolean checkFDS(String dataS)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar data = Calendar.getInstance();
        try {
            data.setTime(sdf.parse(dataS));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return false;
        else if (data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            return false;
        else
            return true;
    }

    private void insert() {
        String dateStart = tvDateStart.getText().toString().trim();
        String dateFinal = tvDateFinal.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (spPatients.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Por favor, informe um paciente!", Toast.LENGTH_LONG).show();
        } else if (spDoctors.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Por favor, informe um médico!", Toast.LENGTH_LONG).show();
        } else if (dateStart.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe a data de inicio!", Toast.LENGTH_LONG).show();
        } else if (dateFinal.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe a data final!", Toast.LENGTH_LONG).show();
        } else if (description.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe a descrição!", Toast.LENGTH_LONG).show();
        } else {
            if(!validationHour()) {
                Toast.makeText(getApplicationContext(), "Expediente do consultório: 08:00 até 12:00;\n" +
                        "13:30 até 17:30\n", Toast.LENGTH_LONG).show();
                return;
            }

            if(!checkFDS(dateStart)){
                Toast.makeText(getApplicationContext(), "Expediente do consultório de Segunda a Sexta-Feira\n", Toast.LENGTH_LONG).show();
                return;
            }

            if(!validationAppointment()) {
                Toast.makeText(getApplicationContext(), "Já possui um agendamento para esse horário!", Toast.LENGTH_LONG).show();
                return;
            }

            db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();
            Person patient = ((Person)spPatients.getSelectedItem());
            Person doctor = ((Person)spDoctors.getSelectedItem());

            sql.append("INSERT INTO consulta (paciente_id,medico_id,data_hora_inicio,data_hora_fim,observacao) VALUES (");
            sql.append("'" + patient.getId() + "', ");
            sql.append("'" + doctor.getId() + "', ");
            sql.append("'" + dateStart + "', ");
            sql.append("'" + dateFinal + "', ");
            sql.append("'" + description + "'");
            sql.append(");");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Consulta cadastrada", Toast.LENGTH_LONG).show();
                clearFills();
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            db.close();
        }
    }

    private void selectPatients(){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        ArrayList<Person> patients = new ArrayList<Person>();
        Cursor cursor = db.rawQuery("SELECT _id, nome_ FROM paciente;", null);

        patients.add(new Person(-1, "Selecione um paciente"));
        if(cursor != null && cursor.moveToFirst()){
            do{
                patients.add(new Person(cursor.getInt(0), cursor.getString(1)));
            }while(cursor.moveToNext());
        }
        if(patients.size() <= 1)
            patients.set(0, new Person(-1, "Nenhum paciente cadastrado"));

        ArrayAdapter scAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, patients);
        spPatients.setAdapter(scAdapter);
        db.close();
    }

    private void selectDoctors(){
        db = openOrCreateDatabase("consulta.db", Context.MODE_PRIVATE, null);
        ArrayList<Person> doctors = new ArrayList<Person>();
        Cursor cursor = db.rawQuery("SELECT _id, nome FROM medico;", null);

        doctors.add(new Person(-1, "Selecione um médico"));
        if(cursor != null && cursor.moveToFirst()){
            do{
                doctors.add(new Person(cursor.getInt(0), cursor.getString(1)));
            }while(cursor.moveToNext());
        }

        if(doctors.size() <= 1)
            doctors.set(0, new Person(-1, "Nenhum médico cadastrado"));

        ArrayAdapter scAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, doctors);
        spDoctors.setAdapter(scAdapter);

        db.close();
    }

}