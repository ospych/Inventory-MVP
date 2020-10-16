package com.example.inventorymvp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.inventorymvp.Data.Inventory;
import com.example.inventorymvp.Data.InventoryRepository;

public class EditPresenter extends AndroidViewModel implements EditContract.Presenter {
    private InventoryRepository repository;
    LiveData<Inventory> inventory;

    public EditPresenter(@NonNull Application application) {
        super(application);
        repository = new InventoryRepository(application);
    }

    @Override
    public void insert(Inventory inventory) {
        repository.insert(inventory);
    }

    @Override
    public void getItem(int id) {
        inventory = repository.getItem(id);
    }

    @Override
    public void update(Inventory inventory) {
        repository.update(inventory);
    }

    @Override
    public void delete(Inventory inventory) {
        repository.delete(inventory);
    }

    @Override
    public void save(String title, String supplier, double price, int quantity, String image) {
        Inventory item = new Inventory(title, supplier, price, quantity, image);

        if (this.inventory == null){
            insert(item);
        } else {
            Inventory inventoryItem = this.inventory.getValue();
            if (inventoryItem != null)
                item.setId(inventoryItem.getId());
            update(item);
        }
    }
}
