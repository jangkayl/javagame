package GlobalClasses;

import interfaces.InvetoryInterface;
import world1.GameLogic;

public class Inventory implements InvetoryInterface{
    private static final int MAX_ITEMS = 20;  
    private static Item[] inventoryItems = new Item[MAX_ITEMS];  
    private static int itemCount = 0;
    private static Shop shop; 
    private static String[] slotName = {"HEAD","BODY","HAND","BOOTS","FOOD"};
    private static Item[] slot = new Item[5];

    public static class Item {
        public String name;
        public String description;
        String body;
        String effect;
        
        public Item(String name, String description, String body, String effect) {
            this.name = name;
            this.body = body;
            this.description = description;
            this.effect = effect;
        }
    }

    public int getItemCount(){
        return itemCount;
    }

    public Item[] getInventoryItems(){
        return inventoryItems;
    }

    public Item[] getSlots(){
        return slot;
    }

    public void setSlots(Item[] slots){
        if (slots != null) {
            for (int i = 0; i < slots.length; i++) {
                slot[i] = slots[i];
            }
        }
    }

    public void setInventoryItems(Item[] items){
        if (items != null) {
            if (items.length <= MAX_ITEMS) {
                for (int i = 0; i < items.length; i++) {
                    inventoryItems[i] = items[i];
                }
                itemCount = items.length;
            } else {
                System.out.println("Not enough space in the inventodry!");
            }
        }
    }

    public void setInventory(String item, String description, String body, String effect) {
        if (itemCount < MAX_ITEMS) {
            inventoryItems[itemCount] = new Item(item, description, body, effect);
            itemCount++;
        } else {
            System.out.println("Inventory is full!");
        }
    }

    // Display the inventory menu
    public void inventoryMenu() {
        inventoryAsk();
        GameLogic.pressAnything();
    }

    public static void displayInventory(){
        shop = GameLogic.shop;
        System.out.print(GameLogic.blueText);
        System.out.println(GameLogic.centerBox("Inventory", 30));
        System.out.print(GameLogic.reset);
        System.out.println(GameLogic.centerText(50, "\nEquipped Items:"));
        System.out.print(GameLogic.centerText(50, "1. Head: " + (slot[0] != null ? slot[0].name + " - " + slot[0].effect : "Empty")));
        System.out.print(GameLogic.centerText(50, "2. Body: " + (slot[1] != null ? slot[1].name + " - " + slot[1].effect : "Empty")));
        System.out.print(GameLogic.centerText(50, "3. Hands: " + (slot[2] != null ? slot[2].name + " - " + slot[2].effect : "Empty")));
        System.out.print(GameLogic.centerText(50, "4. Boots: " + (slot[3] != null ? slot[3].name + " - " + slot[3].effect : "Empty")));
        System.out.print(GameLogic.centerText(50, "\nBuff Items:"));
        System.out.print(GameLogic.centerText(50, (slot[4] != null ? slot[4].name + " - " + slot[4].effect : "Empty")));
        System.out.print(GameLogic.centerText(50, "\nInventory Items:"));
        if (itemCount == 0) {
            System.out.println(GameLogic.centerText(50, "NO ITEM YET"));
            return;
        }

        for (int j = 0; j < itemCount; j++) {
            Item currentItem = inventoryItems[j];
            System.out.print(GameLogic.centerText(50,(j + 1) + ". " + currentItem.name + " - " + currentItem.effect));
        }
    }

    public void inventoryAsk(){
        int choice;
        
        outerLoop:
        while(true){
            GameLogic.clearConsole();
            displayInventory();
            System.out.println(); 
            System.out.print(GameLogic.orangeText);
            System.out.print(GameLogic.centerText(50,"What would you like to do?"));
            System.out.print(GameLogic.centerText(50,"1. Equip an item"));
            System.out.print(GameLogic.centerText(50,"2. Remove an item"));
            System.out.print(GameLogic.centerText(50,"0. Go back to Menu"));
            System.out.println(); 
            int actionChoice = GameLogic.readInt(GameLogic.centerText("", 87) + "Choose an action (0 to 2): ", 0, 2);
            System.out.print(GameLogic.reset);
            if (actionChoice == 0) {
                System.out.println("Returning to the menu...");
                return;
            } else if (actionChoice == 1){
                if(itemCount == 0){
                    System.out.println();
                    System.out.print(GameLogic.redText);
                    System.out.println(GameLogic.centerBox("There is nothing to equip yet.", 50));
                    System.out.print(GameLogic.reset);
                    GameLogic.pressAnything();
                    continue outerLoop;
                }
                while(true){
                    System.out.println(); 
                    System.out.print(GameLogic.orangeText);
                    System.out.print(GameLogic.centerText(50,"Which item do you want to equip? (Press 0 to go back)"));
                    choice = GameLogic.readInt(GameLogic.centerText("", 87) + "Choose your item by number (1 to " + itemCount + "): ", 0, itemCount);
                    System.out.print(GameLogic.reset);
                    System.out.println();
                    if (choice == 0) {  
                        GameLogic.pressAnything();
                        continue outerLoop;
                    }
                    if(isNull(inventoryItems[choice-1].body)){
                        inventoryUse(choice-1, inventoryItems[choice-1].body);
                        continue outerLoop;
                    } else {
                        System.out.print(GameLogic.redText);
                        System.out.print(GameLogic.centerBox("You have something equipped on your " + inventoryItems[choice-1].body, 50)); 
                        System.out.print(GameLogic.reset);
                        GameLogic.pressAnything();
                        continue outerLoop;
                    }
                }
            } else if (actionChoice == 2){
                if(!(slot[0] != null || slot[1] != null || slot[2] != null || slot[3] != null || slot[4] != null)){
                    System.out.println();
                    System.out.print(GameLogic.redText);
                    System.out.println(GameLogic.centerBox("There is nothing to remove yet.", 50));
                    System.out.print(GameLogic.reset);
                    GameLogic.pressAnything();
                    continue outerLoop;
                }
                while(true){
                    System.out.println(); 
                    System.out.print(GameLogic.orangeText);
                    System.out.print(GameLogic.centerText(50, "Which item do you want to unequip? (Press 0 to go back or 5 to UNEQUIP ALL)"));
                    System.out.print(GameLogic.centerText(" ", 80));
                    choice = GameLogic.readInt("Choose your item by number (1 to 4): ", 0, 5);
                    System.out.print(GameLogic.reset);
                    System.out.println(); 
                    if(choice == 5){
                        removeAllInventory();
                        continue outerLoop;
                    }
                    if (choice == 0) {  
                        GameLogic.pressAnything();
                        continue outerLoop;
                    }
                    if(!isNull(slotName[choice-1])){
                        inventoryRemove(slotName[choice-1]);
                        continue outerLoop;
                    } else {
                        System.out.println();
                        System.out.print(GameLogic.redText);
                        System.out.println(GameLogic.centerBox("You don't equipped something on your " + slotName[choice-1], 50));
                        System.out.print(GameLogic.reset);
                        GameLogic.pressAnything();
                        continue outerLoop;
                    }
                } 
            }
        }
    }

    static boolean isNull(String body){
        if(body == "HEAD"){
            return slot[0] == null;
        } else if(body == "BODY"){
            return slot[1] == null;
        } else if(body == "HAND") {
            return slot[2] == null;
        } else if(body == "BOOTS") {
            return slot[3] == null;
        } else if(body == "FOOD") {
            return slot[4] == null;
        }
        return false;
    }

    static boolean noEquip(Item equip){
       if(equip == null){
           System.out.println();
           System.out.println("No item equipped.");
           GameLogic.pressAnything();
           return true;
       } else { 
           return false;
       }
    }
    
    public static void inventoryUse(int itemIndex, String equipmentSlot) {
        if (itemIndex < 0 || itemIndex >= itemCount) {
            System.out.println("Invalid item index.");
            return;
        }

        Item selectedItem = inventoryItems[itemIndex];

        // Check if its in Shop
        for(int i = 0; i < Shop.items.length; i++){
            if(shop.getItemIndexByDescription(selectedItem.description) != -1){
                shop.applyEffect(selectedItem.effect);
                break;
            }
        }

        System.out.print(GameLogic.greenText);
        switch (equipmentSlot.toUpperCase()) {
            case "HEAD":
                slot[0] = selectedItem;
                System.out.println(GameLogic.centerBox(selectedItem.name + " has been equipped to HEAD.", 50));
                break;
            case "BODY":
                slot[1] = selectedItem;
                System.out.println(GameLogic.centerBox(selectedItem.name + " has been equipped to BODY.", 50));
                break;
            case "HAND":
                slot[2] = selectedItem;
                System.out.println(GameLogic.centerBox(selectedItem.name + " has been equipped to HAND.", 50));
                break;
            case "BOOTS":
                slot[3] = selectedItem;
                System.out.println(GameLogic.centerBox(selectedItem.name + " has been equipped to BOOTS.", 50));
                break;
            case "FOOD":
                slot[4] = selectedItem;
                System.out.print(GameLogic.centerText(50, selectedItem.name + " has been consumed! You feel refreshed."));
                break;
            default:
                System.out.println("Invalid slot.");
                return;
        }
        System.out.print(GameLogic.reset);
        
        for (int i = itemIndex; i < itemCount - 1; i++) {
            inventoryItems[i] = inventoryItems[i + 1];
        }

        inventoryItems[itemCount - 1] = null;
        itemCount--;
        
        GameLogic.pressAnything();
    }

    public void remove(Item removedItem){
        if(removedItem != null) {
            inventoryItems[itemCount] = removedItem;
            itemCount++;

            // Check if its in Shop
            for(int i = 0; i < Shop.items.length; i++){
                if(shop.getItemIndexByDescription(removedItem.description) != -1){
                    shop.removeEffect(removedItem.effect);
                    break;
                }
            }
            
        }
    }

    public void removeAllInventory(){
        Item removedItem = null;
        for(int i = 0 ; i < 5; i++){
            removedItem = slot[i];
            slot[i] = null;
            remove(removedItem);
        }
        System.out.print(GameLogic.greenText);
        System.out.print(GameLogic.centerBox("All items has been returned to your inventory.", 50));
        System.out.print(GameLogic.reset);
        GameLogic.pressAnything();
    }

    public void deleteAllInventoryItems(){
        inventoryItems = new Item[0];
        slot = new Item[0];
        itemCount = 0;
    }

    public void restoreToNull(){
        inventoryItems = new Item[MAX_ITEMS];
        slot = new Item[5];
    }

    public void inventoryRemove(String equipmentSlot) {
        Item removedItem = null;
        System.out.println();

        System.out.print(GameLogic.greenText);
        switch (equipmentSlot.toUpperCase()) {
            case "HEAD":
                if(noEquip(slot[0])) return;
                removedItem = slot[0];
                slot[0] = null;
                System.out.print(GameLogic.centerBox(removedItem.name + " have been removed from your HEAD and been returned to your inventory.", 100));
                break;
            case "BODY":
                if(noEquip(slot[1])) return;
                removedItem = slot[1];
                slot[1] = null;
                System.out.print(GameLogic.centerBox(removedItem.name + " have been removed from your BODY and been returned to your inventory.", 100));
                break;
            case "HAND":
                if(noEquip(slot[2])) return;
                removedItem = slot[2];
                slot[2] = null;
                System.out.print(GameLogic.centerBox(removedItem.name + " have been removed from your HAND and been returned to your inventory.", 100));
                break;
            case "BOOTS":
                if(noEquip(slot[3])) return;
                removedItem = slot[3];
                slot[3] = null;
                System.out.print(GameLogic.centerBox(removedItem.name + " have been removed from your BOOTS and been returned to your inventory.", 100));
                break;
            default:
                System.out.println("Invalid slot.");
                return;
        }
        remove(removedItem);
        System.out.print(GameLogic.reset);
        GameLogic.pressAnything();
    }
    
    public boolean checkSlotsValid(){
        for(int i = 0; i < slot.length; i++){
            if(slot[i] != null){
                switch (slot[i].name) {
                    case "Reinforced Headband":
                    case "Blood-Forged Knuckles":
                    case "Shadowrunner Sneakers":
                    case "Tactical Combat Boots":
                        return false;
                    default:
                        break;
                }
            }
        }
        return true;
    }
}