package it.uniba.lacasadicenere.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

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
        return Collections.unmodifiableList(containedItems);
    }

    public Item getContainedItemByName(String name) {
        if (name == null) return null;
        return containedItems.stream()
                .filter(i -> name.equalsIgnoreCase(i.getName()))
                .findFirst().orElse(null);
    }

    public void addContainedItem(Item item) {
        if (item == null) return;
        boolean exists = containedItems.stream()
                .anyMatch(i -> i.getName() != null && i.getName().equalsIgnoreCase(item.getName()));
        if (!exists) {
            this.containedItems.add(item);
        }
    }

    public void removeContainedItem(Item item) {
        if (item == null) return;
        containedItems.removeIf(i -> i.getName() != null && i.getName().equalsIgnoreCase(item.getName()));
    }

    public boolean isEmpty() {
        return this.containedItems.isEmpty();
    }
}
