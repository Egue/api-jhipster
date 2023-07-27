package com.comunicamosmas.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jhipster_relations")
public class Relations implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="role_id")
    private String role;

    @Column(name="menu_id")
    private int menu;

    private String active;


    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

      
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Relations [role=" + role + ", menu=" + menu + "]";
    }

    

   
    
}
