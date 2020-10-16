package com.example.inventorymvp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InventoryDao {
    @Insert
    void insert(Inventory inventory);

    @Update
    void update(Inventory inventory);

    @Delete
    void delete(Inventory inventory);

    @Query("DELETE FROM InventoryDB")
    void deleteAllItems();

    @Query("SELECT * FROM InventoryDB WHERE id=:id")
    LiveData<Inventory> getItem(int id);

    @Query("SELECT * FROM InventoryDB")
    LiveData<List<Inventory>> getAllItems();
}
