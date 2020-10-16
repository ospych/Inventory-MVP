package com.example.inventorymvp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.inventorymvp.Data.Inventory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity implements EditContract.View{
    public static final String EXTRA_ID = "com.example.inventoryMVP.EXTRA_ID";
    private static final String TAG = "EditActivity";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    EditPresenter editPresenter;

    File storageDir;
    File image;
    String currentPhotoPath;
    Uri photoURI;


    private EditText titleET;
    private EditText supplierET;
    private EditText quantityET;
    private EditText priceET;
    private ImageView imageIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editPresenter = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication()))
                .get(EditPresenter.class);

        titleET = findViewById(R.id.edit_title);
        supplierET = findViewById(R.id.edit_supplier);
        priceET = findViewById(R.id.edit_price);
        quantityET = findViewById(R.id.edit_quantity);
        imageIV = findViewById(R.id.view_image);

        if (getIntent().hasExtra(EXTRA_ID)) {
            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            editPresenter.getItem(id);
            editPresenter.inventory.observe(this, new Observer<Inventory>() {
                @Override
                public void onChanged(Inventory inventory) {
                    if (inventory == null) return;
                    titleET.setText(inventory.getTitle());
                    supplierET.setText(inventory.getSupplier());

                    double priceDouble = inventory.getPrice();
                    String priceString = Double.toString(priceDouble);

                    priceET.setText(priceString);

                    int quantityInt = inventory.getQuantity();
                    String quantityString = Integer.toString(quantityInt);

                    quantityET.setText(quantityString);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_item) {
            saveInventory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.inventoryMVP.fileProvider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            }
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File createImageFile() throws IOException{
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void saveInventory() {
        String title = "";
        String supplier = "";
        double price = 0;
        int quantity = 0;

        if (titleET.getEditableText().toString().length() == 0 || supplierET.getEditableText()
                .toString()
                .length() == 0) {
            Toast.makeText(EditActivity.this, "Please input title and description", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (priceET.getEditableText().toString().length() == 0 || quantityET.getEditableText().toString().length() == 0) {
            Toast.makeText(EditActivity.this, "Please input price and quantity", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Toast.makeText(EditActivity.this, "Saved", Toast.LENGTH_SHORT)
                .show();
        title = titleET.getEditableText().toString();
        Log.v(TAG, "title= " + title);

        supplier = supplierET.getEditableText().toString();
        Log.v(TAG, "supplierTV= " + supplier);

        price = Double.parseDouble(priceET.getEditableText().toString());
        Log.v(TAG, "priceTV= " + price);

        quantity = Integer.parseInt(quantityET.getEditableText().toString());
        Log.v(TAG, "quantityTV= " + quantity);

        String picture = photoURI.toString();

        if(title == null || supplier == null || price == 0 || quantity == 0){
            Toast.makeText(EditActivity.this, "No data to DB", Toast.LENGTH_SHORT)
                    .show();
        } else {
            editPresenter.save(title, supplier, price, quantity, picture);
            finish();
        }
    }
}
