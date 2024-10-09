/**
 * Classe que representa una reserva en un restaurant.
 */
package com.projecterestaurant.address.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserves {

    private int id_reserva;
    private Clients client;
    private Taula taula;
    private int numcomensals;
    private LocalDate data;
    private LocalTime hora_arribada;
    private LocalTime hora_sortida;
    private boolean pendent = false;

    /**
     * Constructor per defecte de la classe `Reserves`.
     */
    public Reserves() {
    }

    /**
     * Constructor per crear una nova reserva amb les dades especificades.
     * 
     * @param id_reserva     La ID de la reserva.
     * @param client         El client que realitza la reserva.
     * @param taula          La taula reservada.
     * @param numcomensals   El nombre de comensals de la reserva.
     * @param data           La data de la reserva.
     * @param hora_arribada  L'hora d'arribada prevista.
     * @param hora_sortida   L'hora de sortida prevista.
     * @param pendent        Indica si la reserva està pendent de confirmació.
     */
    public Reserves(int id_reserva, Clients client, Taula taula, int numcomensals, LocalDate data,
            LocalTime hora_arribada, LocalTime hora_sortida, boolean pendent) {
        this.id_reserva = id_reserva;
        this.client = client;
        this.taula = taula;
        this.numcomensals = numcomensals;
        this.data = data;
        this.hora_arribada = hora_arribada;
        this.hora_sortida = hora_sortida;
        this.pendent = pendent;
    }

    /**
     * Constructor alternatiu per crear una nova reserva sense indicar si està pendent.
     * 
     * @param id_reserva     La ID de la reserva.
     * @param client         El client que realitza la reserva.
     * @param taula          La taula reservada.
     * @param numcomensals   El nombre de comensals de la reserva.
     * @param data           La data de la reserva.
     * @param hora_arribada  L'hora d'arribada prevista.
     * @param hora_sortida   L'hora de sortida prevista.
     */
    public Reserves(int id_reserva, Clients client, Taula taula, int numcomensals, LocalDate data,
            LocalTime hora_arribada, LocalTime hora_sortida) {
        this.id_reserva = id_reserva;
        this.client = client;
        this.taula = taula;
        this.numcomensals = numcomensals;
        this.data = data;
        this.hora_arribada = hora_arribada;
        this.hora_sortida = hora_sortida;
    }

    // Mètodes getters i setters

    /**
     * Obté la ID de la reserva.
     * 
     * @return La ID de la reserva.
     */
    public int getId_reserva() {
        return id_reserva;
    }

    /**
     * Estableix la ID de la reserva.
     * 
     * @param id_reserva La nova ID de la reserva.
     */
    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    /**
     * Obté el client de la reserva.
     * 
     * @return El client de la reserva.
     */
    public Clients getClient() {
        return client;
    }

    /**
     * Estableix el client de la reserva.
     * 
     * @param client El nou client de la reserva.
     */
    public void setClient(Clients client) {
        this.client = client;
    }

    /**
     * Obté la taula de la reserva.
     * 
     * @return La taula de la reserva.
     */
    public Taula getTaula() {
        return taula;
    }

    /**
     * Estableix la taula de la reserva.
     * 
     * @param taula La nova taula de la reserva.
     */
    public void setTaula(Taula taula) {
        this.taula = taula;
    }

    /**
     * Obté la data de la reserva.
     * 
     * @return La data de la reserva.
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Estableix la data de la reserva.
     * 
     * @param data La nova data de la reserva.
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Obté l'hora d'arribada prevista per la reserva.
     * 
     * @return L'hora d'arribada prevista.
     */
    public LocalTime getHora_arribada() {
        return hora_arribada;
    }

    /**
     * Estableix l'hora d'arribada prevista per la reserva.
     * 
     * @param hora_arribada La nova hora d'arribada prevista.
     */
    public void setHora_arribada(LocalTime hora_arribada) {
        this.hora_arribada = hora_arribada;
    }

    /**
     * Obté l'hora de sortida prevista per la reserva.
     * 
     * @return L'hora de sortida prevista.
     */
    public LocalTime getHora_sortida() {
        return hora_sortida;
    }

    /**
     * Estableix l'hora de sortida prevista per la reserva.
     * 
     * @param hora_sortida La nova hora de sortida prevista.
     */
    public void setHora_sortida(LocalTime hora_sortida) {
        this.hora_sortida = hora_sortida;
    }

    /**
     * Indica si la reserva està pendent de confirmació.
     * 
     * @return Cert si la reserva està pendent; fals altrament.
     */
    public boolean isPendent() {
        return pendent;
    }

    /**
     * Estableix si la reserva està pendent de confirmació.
     * 
     * @param pendent Cert si la reserva està pendent; fals altrament.
     */
    public void setPendent(boolean pendent) {
        this.pendent = pendent;
    }

    /**
     * Obté el nombre de comensals de la reserva.
     * 
     * @return El nombre de comensals de la reserva.
     */
    public int getNumcomensals() {
        return numcomensals;
    }

    /**
     * Estableix el nombre de comensals de la reserva.
     * 
     * @param numcomensals El nou nombre de comensals de la reserva.
     */
    public void setNumcomensals(int numcomensals) {
        this.numcomensals = numcomensals;
    }

    /**
     * Obté la ID del client associat a la reserva.
     * 
     * @return La ID del client associat a la reserva.
     */
    public int getId_client() {
        return client.getId_client();
    }

    /**
     * Obté la ID de la taula associada a la reserva.
     * 
     * @return La ID de la taula associada a la reserva.
     */
    public int getId_taula() {
        return taula.getId_taula();
    }

    /**
     * Retorna una representació en cadena de la reserva.
     * 
     * @return Una cadena que representa la reserva.
     */
    @Override
    public String toString() {
        return "Reserves [id_reserva=" + id_reserva + ", client=" + client + ", taula=" + taula + ", data=" + data
                + ", hora_arribada=" + hora_arribada + ", hora_sortida=" + hora_sortida + ", pendent=" + pendent
                + ", numcomensals=" + numcomensals + "]";
    }
}

