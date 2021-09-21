package com.apps.myturn.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.myturn.MenuActivity;
import com.apps.myturn.Models.MenuModel;
import com.apps.myturn.MyWalletActivity;
import com.apps.myturn.R;

import java.util.List;


public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemsAdapter.ViewHolder> {
    List<MenuModel> menuModelList;

    public MenuItemsAdapter(List<MenuModel> menuModelList) {
        this.menuModelList = menuModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_design, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MenuModel menuModel = menuModelList.get(position);

        menuModel.setBtnName(menuModel.getBtnName());
        menuModel.setImage(menuModel.getImage());

        holder.menuIcon.setImageResource(menuModel.getImage());
        holder.menu_nameTxView.setText(menuModel.getBtnName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getAdapterPosition() == 0) {
                    Intent intent = new Intent(v.getContext(), MyWalletActivity.class);
                    v.getContext().startActivity(intent);
                } else{
                    Intent intent = new Intent(v.getContext(), MenuActivity.class);
                    intent.putExtra("url",menuModel.getUrl());
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView menuIcon;
        TextView menu_nameTxView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menu_nameTxView = itemView.findViewById(R.id.menuNameTxView);
            menuIcon = itemView.findViewById(R.id.icon_imgview);
        }
    }
}
