package com.example.inventorymvp;

import com.example.inventorymvp.Data.Inventory;

public interface MainContract {
    interface Presenter {
        void insert(Inventory inventory);

        void update(Inventory inventory);

        void delete(Inventory inventory);

        void deleteAllItems();
    }
    interface View {

    }
}
