package com.example.ishaycena.tabfragments.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ishaycena.tabfragments.R;

import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.internal.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    // vars
    private Context context;
    private ArrayList<Found> lstFounds;
    private int lastItemPosition = -1;

    public RecyclerViewAdapter(Context context, ArrayList<Found> founds) {
        this.context = context;
        this.lstFounds = founds;
    }

    public void addItem(Found found){
        lstFounds.add(found);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_listitem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        // profile pic
        Glide.with(context)
                .asBitmap()
                .load(lstFounds.get(position).getImgProfileUrl())
                .into(holder.imgProfilePic);
        // badge
        Glide.with(context)
                .asBitmap()
                .load(lstFounds.get(position).getImgBadgeUrl())
                .into(holder.imgBadgePic);
        // found item
        Glide.with(context)
                .asBitmap()
                .load(lstFounds.get(position).getImgItem())
                .into(holder.imgItemPic);
        // open map
        Glide.with(context)
                .load(R.drawable.ic_map)
                .into(holder.imgOpenMap);

        // person name
        holder.tvPersonName.setText(lstFounds.get(position).getPersonName());

        // description
        holder.tvDescription.setText(lstFounds.get(position).getDescription());

        // start the slide-in animation
//         setAnimation(holder.itemView, position);
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastItemPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastItemPosition = position;

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked item");

                Toast.makeText(context, "clicked:\n" + lstFounds.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    private void setAnimation(View viewToAnimate, int position){
        // this code works, uncomment if your animation doesn't show
//        if (position > lastItemPosition){
//            Animation animation = AnimationUtils.loadAnimation(context, R.anim.left_to_right);
//            viewToAnimate.startAnimation(animation);
//            lastItemPosition = position;
//        }


    }

    @Override
    public int getItemCount() {
        return lstFounds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgProfilePic;
        ImageView imgBadgePic, imgItemPic, imgOpenMap;
        TextView tvPersonName, tvDescription;
        RelativeLayout parentLayout; // to attach onClickListener for the entire item
        LinearLayout linearLayout;

        public void clearAnimation()
        {
            linearLayout.clearAnimation();
        }

        public ViewHolder(View itemView) {
            super(itemView);
            imgProfilePic = itemView.findViewById(R.id.imgProfilePic);
            imgBadgePic = itemView.findViewById(R.id.imgBadgePic);
            imgItemPic = itemView.findViewById(R.id.imgItemPic);
            imgOpenMap = itemView.findViewById(R.id.imgOpenMap);
            tvPersonName = itemView.findViewById(R.id.tvPersonName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            linearLayout = itemView.findViewById(R.id.card_lin_layout);
        }
    }
}
