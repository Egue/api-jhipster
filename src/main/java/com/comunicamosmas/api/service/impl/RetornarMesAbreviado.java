package com.comunicamosmas.api.service.impl;

import org.springframework.stereotype.Service;

import com.comunicamosmas.api.service.IretornarMesAbreviado;

@Service
public class RetornarMesAbreviado implements IretornarMesAbreviado {

    @Override
    public String mes(Integer mes) {

        String mesString = "";

        switch (mes) {
            case 1:
                mesString = "Enr";
                break;
            case 2:
                mesString = "Feb";
                break;
            case 3:
                mesString = "Mar";
                break;
            case 4:
                mesString = "Abr";
                break;
            case 5:
                mesString = "May";
                break;
            case 6:
                mesString = "Jun";
                break;
            case 7:
                mesString = "Jul";
                break;
            case 8:
                mesString = "Ago";
                break;
            case 9:
                mesString = "Sep";
                break;
            case 10:
                mesString = "Oct";
                break;
            case 11:
                mesString = "Nov";
                break;
            case 12:
                mesString = "Dic";
                break;

            default:
                break;
        }

        return mesString;
    }

}
