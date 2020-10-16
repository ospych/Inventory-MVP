package com.example.inventorymvp;

import com.example.inventorymvp.Data.Inventory;

public interface EditContract {
    interface Presenter{
        void insert(Inventory inventory);

        void getItem(int id);

        void update(Inventory inventory);

        void delete(Inventory inventory);

        void save(String title, String supplier, double price, int quantity, String image);
    }

    interface View{
        void saveInventory();
    }
}
