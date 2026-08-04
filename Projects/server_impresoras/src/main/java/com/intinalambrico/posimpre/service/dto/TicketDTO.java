package com.intinalambrico.posimpre.service.dto;

import java.util.List;

public class TicketDTO {
    private String nombreTienda;
    private String numero;
    private double total;
    private List<Item> items;

    public static class Item {
        public String nombre;
        public double precio;
        public int cantidad;
    }
}
