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

    public boolean dateIsCurrent(String dateStart) {
        Calendar calendar = Calendar.getInstance();
        int Current_YEAR = calendar.get(Calendar.YEAR);
        int Current_MONTH = calendar.get(Calendar.MONTH) + 1;
        int Current_DATE = calendar.get(Calendar.DATE);
        int Current_HOUR = calendar.get(Calendar.HOUR);
        int Current_MINUTE = calendar.get(Calendar.MINUTE);

        int YEAR = Integer.parseInt(dateStart.substring(6, 10));
        int MONTH = Integer.parseInt(dateStart.substring(3, 5));
        int DATE = Integer.parseInt(dateStart.substring(0, 2));
        int HOUR = _24hTo12h(Integer.parseInt(dateStart.substring(11, 13)));
        int MINUTE = Integer.parseInt(dateStart.substring(14, 16));

        if(YEAR < Current_YEAR)
            return false;
        else
        if(YEAR == Current_YEAR)
            if(MONTH < Current_MONTH)
                return false;
            else if(MONTH == Current_MONTH)
                if(DATE < Current_DATE)
                    return false;
                else if(DATE == Current_DATE)
                    if(HOUR < Current_HOUR)
                        return false;
                    else if(HOUR == Current_HOUR)
                        if(MINUTE < Current_MINUTE)
                            return false;

        return true;
    }

    public static int _24hTo12h(int hour){
        if(hour == 13)
            return 1;
        else if(hour == 14)
            return 2;
        else if(hour == 15)
            return 3;
        else if(hour == 16)
            return 4;
        else if(hour == 17)
            return 5;
        else if(hour == 18)
            return 6;
        else if(hour == 19)
            return 7;
        else if(hour == 20)
            return 8;
        else if(hour == 21)
            return 9;
        else if(hour == 22)
            return 10;
        else if(hour == 23)
            return 11;
        return 0;
    }

}
