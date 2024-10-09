/**
 * Classe que representa un usuari del sistema.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projecterestaurant.address.model;

import java.util.Objects;

/**
 * Classe que representa un usuari del sistema.
 */
public class Usuari {

    String nom, password;
    private int id;
    private int supervisor;

    /**
     * Constructor per crear un nou usuari amb el correu i la contrasenya especificats.
     * 
     * @param correu    El correu de l'usuari.
     * @param password  La contrasenya de l'usuari.
     */
    public Usuari(String correu, String password) {
        this.nom = correu;
        this.password = password;
    }

    /**
     * Obté el correu de l'usuari.
     * 
     * @return El correu de l'usuari.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Estableix el correu de l'usuari.
     * 
     * @param nom El nou correu de l'usuari.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obté la contrasenya de l'usuari.
     * 
     * @return La contrasenya de l'usuari.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Estableix la contrasenya de l'usuari.
     * 
     * @param password La nova contrasenya de l'usuari.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Estableix la ID de l'usuari.
     * 
     * @param id La nova ID de l'usuari.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obté la ID de l'usuari.
     * 
     * @return La ID de l'usuari.
     */
    public int getId() {
        return id;
    }

    /**
     * Estableix el supervisor de l'usuari.
     * 
     * @param supervisor La ID del supervisor de l'usuari.
     */
    public void setSupervisor(int supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * Obté el supervisor de l'usuari.
     * 
     * @return La ID del supervisor de l'usuari.
     */
    public int getSupervisor() {
        return supervisor;
    }

    /**
     * Calcula el codi hash per a l'usuari.
     * 
     * @return El codi hash de l'usuari.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.nom) + Objects.hashCode(this.password);
        return hash;
    }

    /**
     * Comprova si aquest usuari és igual a un altre objecte.
     * 
     * @param obj L'objecte amb el qual es vol comparar.
     * @return Cert si aquest usuari és igual a l'objecte especificat; fals altrament.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuari other = (Usuari) obj;
        if (!Objects.equals(this.nom, other.nom) && !Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

}
