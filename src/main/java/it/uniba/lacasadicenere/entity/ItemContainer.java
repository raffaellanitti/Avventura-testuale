package it.uniba.lacasadicenere.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe che rappresenta un contenitore di oggetti.
 * Può essere bloccato/sbloccato e contenere oggetti interni.
 */
public class ItemContainer extends Item {

    private final List<Item> containedItems;
    private boolean locked;  
    private boolean open;    

    public ItemContainer(String name, String description, boolean pickable,
                         List<String> aliases, List<Item> containedItems) {
        super(name, description, pickable, aliases);
        this.containedItems = (containedItems != null) ? containedItems : new ArrayList<>();
        this.locked = true;   // di default lo Scrigno è chiuso
        this.open = false;
    }

    /** Ritorna una lista non modificabile degli oggetti contenuti. */
    public List<Item> getContainedItems() {
        return Collections.unmodifiableList(containedItems);
    }

    /** Restituisce un oggetto per nome o alias. */
    public Item getContainedItemByName(String name) {
        if (name == null) return null;
        return containedItems.stream()
                .filter(i -> i.hasName(name))
                .findFirst()
                .orElse(null);
    }

    /** Aggiunge un oggetto al contenitore (se non già presente). */
    public void addContainedItem(Item item) {
        if (item == null) return;
        boolean exists = containedItems.stream()
                .anyMatch(i -> i.hasName(item.getName()));
        if (!exists) {
            this.containedItems.add(item);
        }
    }

    /** Rimuove un oggetto dal contenitore. */
    public void removeContainedItem(Item item) {
        if (item == null) return;
        containedItems.removeIf(i -> i.hasName(item.getName()));
    }

    /** Indica se il contenitore è vuoto. */
    public boolean isEmpty() {
        return this.containedItems.isEmpty();
    }

    // 🔒 Gestione apertura/chiusura
    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    /** Descrizione dinamica a seconda dello stato */
    @Override
    public String getDescription() {
        if (locked) {
            return super.getDescription() + " Sembra chiuso a chiave.";
        } else if (!open) {
            return super.getDescription() + " È sbloccato ma ancora chiuso.";
        } else {
            if (isEmpty()) {
                return super.getDescription() + " È aperto ma vuoto.";
            } else {
                StringBuilder desc = new StringBuilder(super.getDescription());
                desc.append(" È aperto e contiene: ");
                for (Item item : containedItems) {
                    desc.append(item.getName()).append(", ");
                }
                return desc.substring(0, desc.length() - 2) + ".";
            }
        }
    }
}
