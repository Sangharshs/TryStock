package com.apps.myturn.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.myturn.Models.MyTeam;
import com.apps.myturn.R;

import java.util.List;

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.viewholder> {

    List<MyTeam> myTeamList;

    public MyTeamAdapter(List<MyTeam> myTeamList) {
        this.myTeamList = myTeamList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item_design,
                parent,false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {


        holder.textView_points.setText(String.valueOf(myTeamList.get(position).points));
        holder.textView_price.setText(String.valueOf(myTeamList.get(position).stock_price));
        holder.textView_stock_name.setText(String.valueOf(myTeamList.get(position).stock_name));
        holder.textView_buy_sell_value.setText(String.valueOf(myTeamList.get(position).buy_sell_vallue));

        if(holder.textView_buy_sell_value.getText().toString().equals("Buy"))
        {
            holder.buyradioButton.setChecked(true);
            holder.buyradioButton.setVisibility(View.VISIBLE);
        }

        if(holder.textView_buy_sell_value.getText().toString().equals("Sell"))
        {
            holder.sell_radioBtn.setChecked(true);
            holder.sell_radioBtn.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return myTeamList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView textView_stock_name,textView_buy_sell_value,textView_price,textView_points;
        RadioButton buyradioButton,sell_radioBtn;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            buyradioButton = itemView.findViewById(R.id.buybtn);
            sell_radioBtn  = itemView.findViewById(R.id.sellbtn);
            textView_stock_name = itemView.findViewById(R.id.textView_stock_name);
            textView_buy_sell_value = itemView.findViewById(R.id.textView_buy_sell_value);
            textView_price = itemView.findViewById(R.id.textView_price);
            textView_points = itemView.findViewById(R.id.textView_points);
        }
    }
}
