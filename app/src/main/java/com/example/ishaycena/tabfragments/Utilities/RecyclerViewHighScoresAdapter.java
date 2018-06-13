package com.example.ishaycena.tabfragments.Utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ishaycena.tabfragments.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewHighScoresAdapter extends RecyclerView.Adapter<RecyclerViewHighScoresAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewHighScoresA";

    // vars
    private Context context;
    private ArrayList<HighScoresItem> lstHighScores;
    private int lastItemPosition = -1;

    public RecyclerViewHighScoresAdapter(Context context, ArrayList<HighScoresItem> founds) {
        this.context = context;
        this.lstHighScores = founds;
    }

    public void addItem(HighScoresItem highScoresItem){
        lstHighScores.add(highScoresItem);
        notifyDataSetChanged();
    }

    public void addMultipleItems(HighScoresItem... items){
        lstHighScores.addAll(Arrays.asList(items));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_highscoreitem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        // profile pic
        Glide.with(context)
                .asBitmap()
                .load(lstHighScores.get(position).getImgProfileUrl())
                .into(holder.imgProfilePic);
        // badge
        Glide.with(context)
                .asBitmap()
                .load(lstHighScores.get(position).getImgBadgeUrl())
                .into(holder.imgBadgePic);


        // person name
        holder.tvPersonName.setText(lstHighScores.get(position).getPersonName());
        holder.tvFounds.setText(MessageFormat.format("Founds: {0}", lstHighScores.get(position).getFoundsCount()));
        holder.tvLosts.setText(MessageFormat.format("Losts: {0}", lstHighScores.get(position).getLostsCount()));

        // start the slide-in animation
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastItemPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastItemPosition = position;

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked item");

                Toast.makeText(context, "clicked:\n" + lstHighScores.get(position).toString(), Toast.LENGTH_SHORT).show();
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
        return lstHighScores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgProfilePic;
        ImageView imgBadgePic;
        TextView tvPersonName, tvFounds, tvLosts;
        RelativeLayout parentLayout; // to attach onClickListener for the entire item
        LinearLayout linearLayout;

        public void clearAnimation()
        {
            linearLayout.clearAnimation();
        }

        public ViewHolder(View itemView) {
            super(itemView);
            imgProfilePic = itemView.findViewById(R.id.hs_imgProfilePic);
            imgBadgePic = itemView.findViewById(R.id.hs_imgBadgePic);

            tvFounds = itemView.findViewById(R.id.hs_tvUserFounds);
            tvLosts = itemView.findViewById(R.id.hs_tvUserLosts);
            tvPersonName = itemView.findViewById(R.id.hs_tvPersonName);
            parentLayout = itemView.findViewById(R.id.hs_parent_layout);
            linearLayout = itemView.findViewById(R.id.hs_card_lin_layout);
        }
    }
}
