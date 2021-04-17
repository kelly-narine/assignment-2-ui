package com.narine;

import com.narine.entity.Inventory;
import com.narine.entity.Store;
import com.narine.interceptor.Logged;
import com.narine.inventory.InventoryService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SessionScoped
@Named //allows the class to be accessed in the the JSF
public class StoreAppBean implements Serializable {

    private Long id;

    private String name;

    private String sport;

    private int quantity;

    private double unitPrice;

    private Date dateUpdated;

    @EJB
    private InventoryService inventoryService;

    private Long storeId;

    private String storeName;

    private String location;

    public List<Inventory> getInventoryList() {
        return inventoryService.getInventoryList();
    }

    @Logged
    public void addInventory() {
        Inventory inventory = new Inventory(id, name, sport, quantity, unitPrice, dateUpdated);
        Optional<Inventory> inventoryExists = inventoryService.getInventoryList() // go through items in the inventory list. check if the item to be added is already in the list
                .stream()
                .filter(i -> i.getName().equals(name)
                        && i.getSport().equals(sport)
                        && i.getUnitPrice() == unitPrice).findFirst();

        if (inventoryExists.isPresent()) {
            inventoryService.removeFromList(inventory);
            inventoryService.addToList(inventory);
        }
        else {
            inventoryService.addToList(inventory);
        }
        clearFields();
    }

    public void removeInventory(Inventory inventory) {
        inventoryService.removeFromList(inventory);
    }

    private void clearFields() {
        setId(null);
        setName("");
        setSport("");
        setQuantity(0);
        setUnitPrice(0);
        setDateUpdated(null);
    }

    public String storeInfo() {
        Store store = new Store(storeId, storeName, location, getInventoryList());
        return "inventory";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
