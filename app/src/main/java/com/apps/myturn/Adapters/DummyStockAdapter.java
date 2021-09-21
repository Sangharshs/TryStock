package com.apps.myturn.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.myturn.Models.StockModel;
import com.apps.myturn.R;
import com.apps.myturn.StockListListner;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import static com.apps.myturn.Api.IMG;

public class DummyStockAdapter extends RecyclerView.Adapter<DummyStockAdapter.stockviewholder> {
    ArrayList<StockModel> stockModelList;
    private StockListListner stockListListner;
    private Context context;

    public DummyStockAdapter(ArrayList<StockModel> stockModelList, StockListListner stockListListner, Context context) {
        this.stockModelList = stockModelList;
        this.stockListListner = stockListListner;
        this.context = context;
    }

    @NonNull
    @Override
    public stockviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_item, parent, false);
        return new stockviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull stockviewholder holder, int position) {
        holder.bind(stockModelList.get(position));
//        if(stockModelList.get(position).current_stock_price.startsWith("-")){
//            Toast.makeText(context, stockModelList.get(position).getCompany_name(), Toast.LENGTH_SHORT).show();
//        }
//        if(stockModelList.get(position).current_stock_price.startsWith("+")){
//            Toast.makeText(context, stockModelList.get(position).getCompany_name(), Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public int getItemCount() {
        return stockModelList.size();
    }

    public ArrayList<StockModel> getSelectedStockModelList() {
        ArrayList<StockModel> selectedStocksList = new ArrayList<>();
        for (StockModel stockModel : stockModelList) {
            if (stockModel.isSelected) {
                selectedStocksList.add(stockModel);
                //Toast.makeText(context, String.valueOf(selectedStocksList.size()), Toast.LENGTH_SHORT).show();
            }
        }
        return selectedStocksList;
    }


    class stockviewholder extends RecyclerView.ViewHolder {
        RadioGroup radioGroup;
        RadioButton buyButton, sellButton;
        CardView cardViewBackground;
        RoundedImageView roundedImageView;
        TextView company_Name, stock_price, current_value;
        String buy_sell_Value;
        ImageView up_arrow, down_arrow;

        stockviewholder(@NonNull View itemView) {
            super(itemView);

            radioGroup = itemView.findViewById(R.id.radioGroup);
            buyButton = itemView.findViewById(R.id.buyButton);
            sellButton = itemView.findViewById(R.id.sellButton);
            cardViewBackground = itemView.findViewById(R.id.cardBackGround);
            roundedImageView = itemView.findViewById(R.id.roundedImageView);
            company_Name = itemView.findViewById(R.id.company_name);
            stock_price = itemView.findViewById(R.id.stock_price);
            current_value = itemView.findViewById(R.id.up_down_value);
            up_arrow = itemView.findViewById(R.id.up_arrow_imageView);
            down_arrow = itemView.findViewById(R.id.down_arrow_imageView);
        }

        void bind(final StockModel s) {

            company_Name.setText(s.company_name);


            Glide.with(itemView.getContext()).load(IMG + s.getCompanyLogo()).into(roundedImageView);
            current_value.setText(s.getUp_down_indicator());
            stock_price.setText(s.getCurrent_stock_price());


//            if (s.current_stock_price.startsWith("-")) {
//                down_arrow.setVisibility(View.VISIBLE);
//            }
//            if (s.current_stock_price.startsWith("+")) {
//                up_arrow.setVisibility(View.VISIBLE);
//            }

            if (s.isSelected != null)
                if (s.isSelected) {
                    if(s.buy_sell_value.equals("Buy")){
                        buyButton.setChecked(true);
                    }
                    if(s.buy_sell_value.equals("Sell")){
                        sellButton.setChecked(true);
                    }
                    cardViewBackground.setCardBackgroundColor(itemView.getResources().getColor(R.color.selected));
                } else {
                    cardViewBackground.setCardBackgroundColor(itemView.getResources().getColor(R.color.white));

                }
            buyButton.setClickable(false);
                sellButton.setClickable(false);

//            buyButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    s.buy_sell_value = buy_sell_Value = "Buy";
//                    stockListListner.buysellValue(buy_sell_Value);
//                    if (s.isSelected != null)
//                        if (s.isSelected) {
//                            radioGroup.clearCheck();
//                            cardViewBackground.setCardBackgroundColor(itemView.getResources().getColor(R.color.white));
//                            s.isSelected = false;
//                            if (getSelectedStockModelList().size() == 0) {
//                                stockListListner.onStockShowAction(false);
//                            }
//
//                        } else {
//                            cardViewBackground.setCardBackgroundColor(itemView.getResources().getColor(R.color.selected));
//                            s.isSelected = true;
//                            stockListListner.onStockShowAction(true);
//
//                        }
//                }
//            });
//
//            sellButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    s.buy_sell_value = buy_sell_Value = "Sell";
//                    stockListListner.buysellValue(buy_sell_Value);
//                    if (s.isSelected != null)
//                        if (s.isSelected) {
//                            radioGroup.clearCheck();
//                            cardViewBackground.setCardBackgroundColor(itemView.getResources().getColor(R.color.white));
//                            s.isSelected = false;
//                            if (getSelectedStockModelList().size() == 0) {
//                                stockListListner.onStockShowAction(false);
//                            }
//                        } else {
//                            cardViewBackground.setCardBackgroundColor(itemView.getResources().getColor(R.color.selected));
//                            s.isSelected = true;
//                            stockListListner.onStockShowAction(true);
//
//                        }
//                }
//            });
            buy_sell_Value = s.buy_sell_value;
        }
    }
}
