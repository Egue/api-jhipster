package com.comunicamosmas.api.service.mapper;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class SmartConverter<T> extends AbstractBeanField<T, String> {
    @Override
    @SuppressWarnings("unchecked")
    protected T convert(String value) throws CsvDataTypeMismatchException {
        if (value == null || value.trim().isEmpty()) {
            // Determina el tipo genérico y devuelve el valor por defecto apropiado
            if (Integer.class.equals(getField().getType())) {
                return (T) Integer.valueOf(0);
            } else if (String.class.equals(getField().getType())) {
                return (T) "";
            }
            // Puedes añadir más tipos aquí
        }
        
        try {
            if (Integer.class.equals(getField().getType())) {
                return (T) Integer.valueOf(value.trim());
            }
            return (T) value.trim(); // Para String y otros tipos
        } catch (NumberFormatException e) {
            throw new CsvDataTypeMismatchException("Valor no numérico: " + value);
        }
    }
}
    

