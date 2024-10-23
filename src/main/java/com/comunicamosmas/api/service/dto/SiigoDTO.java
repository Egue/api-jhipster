package com.comunicamosmas.api.service.dto;

import java.util.List;

public class SiigoDTO {
    
    public static class AuthSiigo{

        private String username;

        private String access_key;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAccess_key() {
            return access_key;
        }

        public void setAccess_key(String access_key) {
            this.access_key = access_key;
        }
        
    }

    public static class FacturaVenta{
        private Document document;
        private String date;
        private Customer customer;
        private Integer cost_center; //Centro de costo, el campo es obligatorio según la configuración del comprobante
        private Currency currency;
        private Integer seller; //ID del vendedor asociado a la factura.
        private Stamp stamp; //Campo para indicar el envío de la factura electronica
        private Stamp mail; //Campo para indicar el envío de la factura al cliente
        private String observations;//Comentarios para agregar información a la factura.
        private List<Items> items; //Productos o Servicios asociados a la factura.
        private List<Payments> payments;//Formas de pago asociadas a la factura.
        private List<GlobalDiscounts> globaldiscounts;
        public Document getDocument() {
            return document;
        }
        public void setDocument(Document document) {
            this.document = document;
        }
        public String getDate() {
            return date;
        }
        public void setDate(String date) {
            this.date = date;
        }
        public Customer getCustomer() {
            return customer;
        }
        public void setCustomer(Customer customer) {
            this.customer = customer;
        }
        public Integer getCost_center() {
            return cost_center;
        }
        public void setCost_center(Integer cost_center) {
            this.cost_center = cost_center;
        }
        public Currency getCurrency() {
            return currency;
        }
        public void setCurrency(Currency currency) {
            this.currency = currency;
        }
        public Integer getSeller() {
            return seller;
        }
        public void setSeller(Integer seller) {
            this.seller = seller;
        }
        public Stamp getStamp() {
            return stamp;
        }
        public void setStamp(Stamp stamp) {
            this.stamp = stamp;
        }
        public Stamp getMail() {
            return mail;
        }
        public void setMail(Stamp mail) {
            this.mail = mail;
        }
        public String getObservations() {
            return observations;
        }
        public void setObservations(String observations) {
            this.observations = observations;
        }
        public List<Items> getItems() {
            return items;
        }
        public void setItems(List<Items> items) {
            this.items = items;
        }
        public List<Payments> getPayments() {
            return payments;
        }
        public void setPayments(List<Payments> payments) {
            this.payments = payments;
        }
        public List<GlobalDiscounts> getGlobaldiscounts() {
            return globaldiscounts;
        }
        public void setGlobaldiscounts(List<GlobalDiscounts> globaldiscounts) {
            this.globaldiscounts = globaldiscounts;
        }

        
    }

    public static class Customer{
        private String person_type; //Tipo de persona asociado al documento. --> Person
        private String id_type; //Identificador del tipo de tercero. --> 13
        private String indentification; //Número de identificación del cliente.--> 13832081
        private int branch_office; //Sucursal, valor por default 0. -->0
        private List<String> name; //"name": ["Manuel","Camacho"]
        private Address address;
        private List<Phones> phones;
        private List<Contacts> contacts;
        public String getPerson_type() {
            return person_type;
        }
        public void setPerson_type(String person_type) {
            this.person_type = person_type;
        }
        public String getId_type() {
            return id_type;
        }
        public void setId_type(String id_type) {
            this.id_type = id_type;
        }
        public String getIndentification() {
            return indentification;
        }
        public void setIndentification(String indentification) {
            this.indentification = indentification;
        }
        public int getBranch_office() {
            return branch_office;
        }
        public void setBranch_office(int branch_office) {
            this.branch_office = branch_office;
        }
        public List<String> getName() {
            return name;
        }
        public void setName(List<String> name) {
            this.name = name;
        }
        public Address getAddress() {
            return address;
        }
        public void setAddress(Address address) {
            this.address = address;
        }
        public List<Phones> getPhones() {
            return phones;
        }
        public void setPhones(List<Phones> phones) {
            this.phones = phones;
        }
        public List<Contacts> getContacts() {
            return contacts;
        }
        public void setContacts(List<Contacts> contacts) {
            this.contacts = contacts;
        }
        
    }

    private static class GlobalDiscounts{
        private Integer id;
        private Integer percentage;
        private Integer value;
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public Integer getPercentage() {
            return percentage;
        }
        public void setPercentage(Integer percentage) {
            this.percentage = percentage;
        }
        public Integer getValue() {
            return value;
        }
        public void setValue(Integer value) {
            this.value = value;
        }
        
    }

    public static class Payments{
        private Integer id;
        private Double value;
        private String due_date;
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public Double getValue() {
            return value;
        }
        public void setValue(Double value) {
            this.value = value;
        }
        public String getDue_date() {
            return due_date;
        }
        public void setDue_date(String due_date) {
            this.due_date = due_date;
        }
        
    }

    public static class Items{
        private String code;
        private String description;
        private Integer quantity;
        private Double price;
        private Integer discount;
        private List<Taxes> taxes; //Impuestos que se desean asociar al producto o servicio.
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public Integer getQuantity() {
            return quantity;
        }
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
        public Double getPrice() {
            return price;
        }
        public void setPrice(Double price) {
            this.price = price;
        }
        public Integer getDiscount() {
            return discount;
        }
        public void setDiscount(Integer discount) {
            this.discount = discount;
        }
        public List<Taxes> getTaxes() {
            return taxes;
        }
        public void setTaxes(List<Taxes> taxes) {
            this.taxes = taxes;
        }
        
    }

    public static class Taxes{
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
        
    }

    public static class Stamp{
        private Boolean send;

        public Boolean getSend() {
            return send;
        }

        public void setSend(Boolean send) {
            this.send = send;
        }
        
    }

    public static class Currency{
        private String code;
        private Double exchange_rate;
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public Double getExchange_rate() {
            return exchange_rate;
        }
        public void setExchange_rate(Double exchange_rate) {
            this.exchange_rate = exchange_rate;
        }

        
    }

    public static class Contacts{
        private String first_name;

        private String last_name;

        private String email;

        private Phones phone;

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Phones getPhone() {
            return phone;
        }

        public void setPhone(Phones phone) {
            this.phone = phone;
        }

        
    }

    public static class Phones{
        private String indicative;

        private String number;

        private String extension;

        public String getIndicative() {
            return indicative;
        }

        public void setIndicative(String indicative) {
            this.indicative = indicative;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        
    }
    
    public static class Address{
        private String address;
        private City city;
        private String postal_code;
        
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public City getCity() {
            return city;
        }
        public void setCity(City city) {
            this.city = city;
        }
        public String getPostal_code() {
            return postal_code;
        }
        public void setPostal_code(String postal_code) {
            this.postal_code = postal_code;
        }
        
    }

    public static class City {
        private String country_code;
        private String country_name;
        private String state_code;
        private String state_name;
        private String city_code;
        private String city_name; 
        public String getCountry_code() {
            return country_code;
        }
        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }
        public String getCountry_name() {
            return country_name;
        }
        public void setCountry_name(String country_name) {
            this.country_name = country_name;
        }
        public String getState_code() {
            return state_code;
        }
        public void setState_code(String state_code) {
            this.state_code = state_code;
        }
        public String getState_name() {
            return state_name;
        }
        public void setState_name(String state_name) {
            this.state_name = state_name;
        }
        public String getCity_code() {
            return city_code;
        }
        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }
        public String getCity_name() {
            return city_name;
        }
        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }
        

    }

    public static class Document {
        private int id; //Identificador del comprobante https://api.siigo.com/v1/document-types?type=FV

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        
    }


}
