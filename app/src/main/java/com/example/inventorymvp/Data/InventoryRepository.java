package com.example.inventorymvp.Data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class InventoryRepository {
    private InventoryDao inventoryDao;
    private LiveData<List<Inventory>> allItems;

    public InventoryRepository(Application application){
        InventoryDatabase database = InventoryDatabase.getInstance(application);
        inventoryDao = database.inventoryDao();
        allItems = inventoryDao.getAllItems();
    }
    public LiveData<Inventory> getItem(int id){
        return inventoryDao.getItem(id);
    }

    public void insert(Inventory inventory) {
        new InsertAsyncTask(inventoryDao).execute(inventory);
    }

    public void update(Inventory inventory) {
        new UpdateAsyncTask(inventoryDao).execute(inventory);
    }

    public void delete(Inventory inventory) {
        new DeleteAsyncTask(inventoryDao).execute(inventory);
    }

    public void deleteAllItems() {
        new DeleteAllAsyncTask(inventoryDao).execute();
    }

    public LiveData<List<Inventory>> getAllItems(){
        return allItems;
    }


    private static class InsertAsyncTask extends AsyncTask<Inventory, Void, Void> {
        private InventoryDao inventoryDao;

        public InsertAsyncTask(InventoryDao inventoryDao) {
            this.inventoryDao = inventoryDao;
        }

        @Override
        protected Void doInBackground(Inventory... inventories) {
            inventoryDao.insert(inventories[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Inventory, Void, Void> {
        private InventoryDao inventoryDao;

        public UpdateAsyncTask(InventoryDao inventoryDao) {
            this.inventoryDao = inventoryDao;
        }

        @Override
        protected Void doInBackground(Inventory... inventories) {
            inventoryDao.update(inventories[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Inventory, Void, Void>{
        private InventoryDao inventoryDao;

        public DeleteAsyncTask(InventoryDao inventoryDao) {
            this.inventoryDao = inventoryDao;
        }

        @Override
        protected Void doInBackground(Inventory... inventories) {
            inventoryDao.delete(inventories[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Inventory, Void, Void>{
        private InventoryDao inventoryDao;

        public DeleteAllAsyncTask(InventoryDao inventoryDao) {
            this.inventoryDao = inventoryDao;
        }

        @Override
        protected Void doInBackground(Inventory... inventories) {
            inventoryDao.deleteAllItems();
            return null;
        }
    }
}
