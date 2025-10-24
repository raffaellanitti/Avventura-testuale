package it.uniba.lacasadicenere.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class ItemContainer extends Item {
    
    private List<Item> containedItems;

    public ItemContainer(String name, String description, boolean pickable, List<String> aliases, List<Item> containedItems) {
        super(name, description, pickable, aliases);

        this.containedItems = (containedItems != null) ? containedItems : new ArrayList<>();
    }

    public List<Item> getContainedItems() {
        return containedItems;
    }

    public void addContainedItem(Item item) {
        if (item != null && !this.containedItems.contains(item)) {
            this.containedItems.add(item);
        }
    }

    public void removeContainedItem(Item item) {
        if (item != null) {
            this.containedItems.remove(item);
        }
    }

    public boolean isEmpty() {
        return this.containedItems.isEmpty();
    }
}
