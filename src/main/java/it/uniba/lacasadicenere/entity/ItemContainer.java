package it.uniba.lacasadicenere.entity;

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

    public List<Item> getList() {
        return list;
    }
    public void setList(List<Item> list) {
        this.list = list;
    }

    public void add(Item item) {
        list.add(item);
    }

    public void remove(Item item) {
        list.remove(item);
    }
}