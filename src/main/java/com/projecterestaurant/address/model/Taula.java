/**
 * Classe que representa una taula d'un restaurant.
 */
package com.projecterestaurant.address.model;

import com.projecterestaurant.address.enums.TipusTaula;

public class Taula {
    private int id_taula;
    private int capacitat;
    private TipusTaula tipusTaula;
    private Boolean estat;

    /**
     * Constructor per crear una nova taula amb les dades especificades.
     * 
     * @param id_taula     La ID de la taula.
     * @param capacitat    La capacitat de la taula.
     * @param tipusTaula   El tipus de la taula.
     * @param estat        L'estat de la taula.
     */
    public Taula(int id_taula, int capacitat, TipusTaula tipusTaula, boolean estat) {
        this.id_taula = id_taula;
        this.capacitat = capacitat;
        this.tipusTaula = tipusTaula;
        this.estat = estat;
    }

    /**
     * Estableix la ID de la taula.
     * 
     * @param id_taula La nova ID de la taula.
     */
    public void setId_taula(int id_taula) {
        this.id_taula = id_taula;
    }

    /**
     * Estableix la capacitat de la taula.
     * 
     * @param capacitat La nova capacitat de la taula.
     */
    public void setCapacitat(int capacitat) {
        this.capacitat = capacitat;
    }

    /**
     * Estableix el tipus de la taula.
     * 
     * @param tipusTaula El nou tipus de la taula.
     */
    public void setTipusTaula(TipusTaula tipusTaula) {
        this.tipusTaula = tipusTaula;
    }

    /**
     * Estableix l'estat de la taula.
     * 
     * @param estat L'estat de la taula.
     */
    public void setEstat(boolean estat) {
        this.estat = estat;
    }

    /**
     * Obté la ID de la taula.
     * 
     * @return La ID de la taula.
     */
    public int getId_taula() {
        return id_taula;
    }

    /**
     * Obté la capacitat de la taula.
     * 
     * @return La capacitat de la taula.
     */
    public int getCapacitat() {
        return capacitat;
    }

    /**
     * Obté el tipus de la taula.
     * 
     * @return El tipus de la taula.
     */
    public TipusTaula getTipusTaula() {
        return tipusTaula;
    }

    /**
     * Obté l'estat de la taula.
     * 
     * @return L'estat de la taula.
     */
    public boolean isEstat() {
        return estat;
    }

    /**
     * Estableix l'estat de la taula.
     * 
     * @param estat L'estat de la taula.
     */
    public void setEstat(Boolean estat) {
        this.estat = estat;
    }

    /**
     * Retorna una representació en cadena de la taula.
     * 
     * @return Una cadena que representa la taula.
     */
    @Override
    public String toString() {
        return "Taula [id_taula=" + id_taula + ", capacitat=" + capacitat + ", tipusTaula=" + tipusTaula + ", estat="
                + estat + "]";
    }

}
