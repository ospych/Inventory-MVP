package com.example.inventorymvp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.inventorymvp.Data.Inventory;
import com.example.inventorymvp.Data.InventoryRepository;

import java.util.List;

public class MainPresenter extends AndroidViewModel implements MainContract.Presenter {
    private InventoryRepository repository;
    LiveData<List<Inventory>> items;

    public MainPresenter(@NonNull Application application) {
        super(application);
        repository = new InventoryRepository(application);
        items = repository.getAllItems();
    }

    public LiveData<List<Inventory>> getItems() {
        return items;
    }

    @Override
    public void insert(Inventory inventory) {
        repository.insert(inventory);
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
    public void deleteAllItems() {
        repository.deleteAllItems();
    }
}
