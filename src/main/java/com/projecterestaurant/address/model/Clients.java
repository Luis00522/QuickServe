/**
 * Classe que representa un client d'un restaurant.
 */
package com.projecterestaurant.address.model;

public class Clients {
    private int id_client;
    private String nom;
    private String cognom;
    private int telefon;

    /**
     * Constructor per crear un nou client amb els paràmetres especificats.
     * 
     * @param id_client La ID del client.
     * @param nom       El nom del client.
     * @param cognom    El cognom del client.
     * @param telefon   El número de telèfon del client.
     */
    public Clients(int id_client, String nom, String cognom, int telefon) {
        this.id_client = id_client;
        this.nom = nom;
        this.cognom = cognom;
        this.telefon = telefon;
    }

    /**
     * Estableix la ID del client.
     * 
     * @param id_client La nova ID del client.
     */
    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    /**
     * Estableix el nom del client.
     * 
     * @param nom El nou nom del client.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Estableix el cognom del client.
     * 
     * @param cognom El nou cognom del client.
     */
    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    /**
     * Estableix el número de telèfon del client.
     * 
     * @param telefon El nou número de telèfon del client.
     */
    public void setTelefon(int telefon) {
        this.telefon = telefon;
    }

    /**
     * Obté la ID del client.
     * 
     * @return La ID del client.
     */
    public int getId_client() {
        return id_client;
    }

    /**
     * Obté el nom del client.
     * 
     * @return El nom del client.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Obté el cognom del client.
     * 
     * @return El cognom del client.
     */
    public String getCognom() {
        return cognom;
    }

    /**
     * Obté el número de telèfon del client.
     * 
     * @return El número de telèfon del client.
     */
    public int getTelefon() {
        return telefon;
    }

    /**
     * Retorna una representació en cadena del client.
     * 
     * @return Una cadena que representa el client.
     */
    @Override
    public String toString() {
        return "Clients [id_client=" + id_client + ", nom=" + nom + ", cognom=" + cognom + ", telefon=" + telefon + "]";
    }
}
