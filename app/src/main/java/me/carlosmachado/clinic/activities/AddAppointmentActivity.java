package me.carlosmachado.clinic.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import me.carlosmachado.clinic.R;
import me.carlosmachado.clinic.auxliary.Alert;
import me.carlosmachado.clinic.auxliary.Mask;

public class AddAppointmentActivity extends AppCompatActivity {

    EditText tvDateStart;
    EditText tvDateFinal;
    EditText etDescription;

    Spinner spPatients;
    Spinner spDoctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        tvDateStart = findViewById(R.id.tvStartDate);
        tvDateFinal = findViewById(R.id.tvEndDate);
        etDescription = findViewById(R.id.etIntegrates);
        spPatients = findViewById(R.id.spPatient);
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

        //tvDateStart.addTextChangedListener(Mask.insert("(##)#####-####", tvDateStart));
        //tvDateFinal.addTextChangedListener(Mask.insert("(##)####-####", etPhone));
    }

    private void pickerDate(final String op){
        Calendar calendar = Calendar.getInstance();

        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int date) {
                String dateString = "";
                if(month < 10) {
                    dateString = date + ":" + "0" + (month + 1) + ":" + year;
                }
                else {
                    dateString = date + ":" + (month + 1) + ":" + year;
                }

                if(op.equals("0")) {
                    tvDateStart.setText(dateString);
                    pickerTime("0");
                }
                else if (op.equals("1")) {
                    tvDateFinal.setText(dateString);
                    pickerTime("1");
                }
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }

    private void pickerTime(final String op){
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute){
                String timeString = hour + ":" + minute;
                if(op.equals("0"))
                    tvDateStart.setText(tvDateStart.getText() + " " + timeString);
                else if(op.equals("1"))
                    tvDateFinal.setText(tvDateFinal.getText() + " " + timeString);
            }
        }, HOUR, MINUTE, true);
        timePickerDialog.show();
    }

    private void insert(){
        // TODO: Inserir na tabela "appointment" os dados da view
    }

    private void selectPatients(){
        // TODO: Pegar todos os dados da tabela "patient" e jogar no spinner "spPatient"
    }

    private void selectDoctors(){
        // TODO: Pegar todos os dados da tabela "doctor" e jogar no spinner "spDoctor"
    }

}