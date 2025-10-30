package it.uniba.lacasadicenere.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un contenitore di oggetti.
 */
public class ItemContainer extends Item {

    /**
     * Lista di oggetti contenuti nel contenitore
     */
    private List<Item> list = new ArrayList<>();

    /**
     * Costruttore dell'oggetto ItemContainer.
     * @param name
     * @param description
     * @param pickable
     * @param aliases
     */
    public ItemContainer(String name, String description,  boolean pickable, List<String> aliases) {
        super(name, description, pickable, aliases);
    }

    /**
     * Restituisce la lista di oggetti contenuti nel contenitore.
     * @return lista di oggetti
     */
    public List<Item> getList() {
        return list;
    }

    /**
     * Imposta la lista di oggetti contenuti nel contenitore.
     * @param list
     */
    public void setList(List<Item> list) {
        this.list = list;
    }

    /**
     * Aggiunge un oggetto al contenitore.
     * @param item
     */
    public void add(Item item) {
        list.add(item);
    }

    /**
     * Rimuove un oggetto dal contenitore.
     * @param item
     */
    public void remove(Item item) {
        list.remove(item);
    }
}