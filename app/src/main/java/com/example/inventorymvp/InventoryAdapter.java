package com.example.inventorymvp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inventorymvp.Data.Inventory;

public class InventoryAdapter extends ListAdapter<Inventory, InventoryAdapter.Holder> {
    private OnItemClickListener listener;

    public InventoryAdapter() {super(DIFF_CALLBACK);}

    private static final DiffUtil.ItemCallback<Inventory> DIFF_CALLBACK = new DiffUtil.ItemCallback<Inventory>() {
        @Override
        public boolean areItemsTheSame(@NonNull Inventory oldItem, @NonNull Inventory newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Inventory oldItem, @NonNull Inventory newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getSupplier().equals(newItem.getSupplier()) &&
                    oldItem.getQuantity() == newItem.getQuantity();
        }
    };

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inventory_item, parent, false);
        return new Holder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Inventory current = getItem(position);

        holder.titleTV.setText("Item: " + current.getTitle());
        holder.supplierTV.setText("Supplier: " + current.getSupplier());
        holder.quantityTV.setText("Quantity: " + current.getQuantity());
        holder.priceTV.setText(current.getPrice() + "$");

        Glide.with(holder.imageView.getContext()).load(current.getImage()).into(holder.imageView);

    }

    public Inventory getItemAt(int position) {
        return getItem(position);
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView titleTV;
        private TextView supplierTV;
        private TextView quantityTV;
        private TextView priceTV;
        private ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
            supplierTV = itemView.findViewById(R.id.supplierTV);
            quantityTV = itemView.findViewById(R.id.quantityTV);
            priceTV = itemView.findViewById(R.id.priceTV);
            imageView = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Inventory inventory);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
