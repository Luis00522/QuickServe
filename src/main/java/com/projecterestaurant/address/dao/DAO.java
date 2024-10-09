package com.projecterestaurant.address.dao;

import java.util.List;

/**
 * Interfície genèrica per a operacions bàsiques d'accés a dades (CRUD).
 * @param <T> Tipus de l'objecte
 * @param <K> Tipus de la clau primària
 */
public interface DAO<T, K> {

    /**
     * Insereix un nou objecte a la font de dades.
     * @param t L'objecte a inserir
     */
    public void insert(T t);

    /**
     * Actualitza un objecte existent a la font de dades.
     * @param t L'objecte a actualitzar
     */
    public void update(T t);

    /**
     * Elimina un objecte de la font de dades.
     * @param t L'objecte a eliminar
     */
    public void delete(T t);

    /**
     * Seleciona un objecte de la font de dades mitjançant la seva clau primària.
     * @param id La clau primària de l'objecte
     * @return L'objecte seleccionat
     */
    public T selectById(K id);

    /**
     * Seleciona tots els objectes de la font de dades.
     * @return Llista d'objectes
     */
    public List<T> selectAll();
}
