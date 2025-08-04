package com.comunicamosmas.api.service.mapper;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class EmptyStringToZeroConverter extends AbstractBeanField<Integer , String>{
    @Override
    protected Integer convert(String value) throws CsvDataTypeMismatchException {
        if (value == null || value.trim().isEmpty()) {
            return 0; // Valor predeterminado para celdas vacías
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new CsvDataTypeMismatchException("Valor no numérico: " + value);
        }
    }

    
}
