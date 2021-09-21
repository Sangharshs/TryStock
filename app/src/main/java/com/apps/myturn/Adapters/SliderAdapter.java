package com.apps.myturn.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.apps.myturn.Models.SliderItem;
import com.apps.myturn.R;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import static com.apps.myturn.Api.IMG1;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderAdapterVH> {

    private List<SliderItem> mSliderItems = new ArrayList<>();
    private ViewPager2 viewPager2;


    public SliderAdapter(List<SliderItem> mSliderItems, ViewPager2 viewPager2) {
        this.mSliderItems = mSliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderAdapterVH(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,
                        parent, false));
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        SliderItem sliderItem = mSliderItems.get(position);

        Glide.with(viewHolder.itemView)
                .load(IMG1+sliderItem.getImageUrl())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        if (position == mSliderItems.size() - 2) {
            viewPager2.post(runnable);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(sliderItem.getPromotionalUrl()); // missing 'http://' will cause crashed
                if (uri.toString().startsWith("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSliderItems.size();
    }

    class SliderAdapterVH extends RecyclerView.ViewHolder {
        View itemView;
        RoundedImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.imageView);
            this.itemView = itemView;
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mSliderItems.addAll(mSliderItems);
            notifyDataSetChanged();
        }
    };
}
