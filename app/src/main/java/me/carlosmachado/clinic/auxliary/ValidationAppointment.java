package me.carlosmachado.clinic.auxliary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ValidationAppointment {

    public boolean validationHour(int hora_inicial, int hora_final, int minuto_inicial, int minuto_final){
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

        if(hora_inicial >= 8 && hora_inicial <= 12)
            if(hora_final > 12 || (hora_final == 12 && minuto_final > 0))
                return false;

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

    public boolean checkFDS(String dataS) {
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

}
