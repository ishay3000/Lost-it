package com.example.ishaycena.tabfragments.Utilities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ishaycena.tabfragments.FoundService.Found;
import com.example.ishaycena.tabfragments.MapService.LocationActivity;
import com.example.ishaycena.tabfragments.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoundRecyclerViewAdapter
        extends AbsRecyclerViewAdapter<Found, AdapterFound> {

    private static final String TAG = "FoundRecyclerViewAdapter";

    public FoundRecyclerViewAdapter(Context context, ArrayList<AdapterFound> data) {
        super(context, data);
    }

    @Override
    public void addFetchedItem(List<Found> lstData) {
        for (Found found : lstData) {
            final AdapterFound adapterFound = new AdapterFound();
            // add item picture
            Glide.with(context)
                    .asBitmap()
                    .load(found.getmItemImageUrl())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            adapterFound.setImgItem(resource);
                        }
                    });
            // profile pic
            Glide.with(context)
                    .asBitmap()
                    .load(found.getmProfileImageUrl())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            adapterFound.setImgProfileUrl(resource);
                        }
                    });
            //#region badge - to be added, maybe (?)
//            Glide.with(context)
//                    .asBitmap()
//                    .load(super.mLstData.get(0).getImgProfileUrl())
//                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            adapterFound.setImgProfileUrl(resource);
//                        }
//                    });
            //#endregion

            // desc
            adapterFound.setDescription(found.getmItemDescription());
            // username
            adapterFound.setPersonName(found.getmUserName());
            adapterFound.setCustomLatLong(found.getmLatLong());

            // at last, add the item to the list
            super.mLstData.add(adapterFound);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ItemTypes.NORMAL) {
            view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.layout_listitem, parent, false);
        } else {
            //TODO add inflating a progress bar layout
            // view = ... R.layout.progress_footer
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer, parent, false);
        }
        return new RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        if (getItemViewType(position) == ItemTypes.NORMAL) {
            //#region normal found item

            RecyclerViewAdapter.ViewHolder mHolder = (RecyclerViewAdapter.ViewHolder) holder;
            // profile pic
            Glide.with(context)
                    .asBitmap()
                    .load(mLstData.get(position).getImgProfileUrl())
                    .into(mHolder.imgProfilePic);
            // badge
            Glide.with(context)
                    .asBitmap()
                    .load(mLstData.get(position).getImgBadgeUrl())
                    .into(mHolder.imgBadgePic);
            // found item
            Glide.with(context)
                    .asBitmap()
                    .load(mLstData.get(position).getImgItem())
                    .into(mHolder.imgItemPic);
            // open map
            Glide.with(context)
                    .load(R.drawable.ic_map)
                    .into(mHolder.imgOpenMap);

            // person name
            mHolder.tvPersonName.setText(mLstData.get(position).getPersonName());

            // description
            mHolder.tvDescription.setText(mLstData.get(position).getDescription());

            // start the slide-in animation
            Animation animation = AnimationUtils.loadAnimation(context,
                    (position > lastItemPosition) ? R.anim.up_from_bottom
                            : R.anim.down_from_top);
            holder.itemView.startAnimation(animation);
            lastItemPosition = position;

            mHolder.imgOpenMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent locationIntent = new Intent(context, LocationActivity.class);
                    locationIntent.putExtra("LOCATION", mLstData.get(position).getCustomLatLong());
                    context.startActivity(locationIntent);
                }
            });

            mHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: clicked item");

                    Toast.makeText(context, "clicked:\n" + mLstData.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
            });
            //#endregion
        } else {
            // TODO add a new view holder static class of a progressbar
            if (holder instanceof RecyclerViewAdapter.FooterViewHolder) {
                RecyclerViewAdapter.FooterViewHolder footerViewHolder = (RecyclerViewAdapter.FooterViewHolder) holder;
                footerViewHolder.progressBar.setIndeterminate(true);
                lastItemPosition = position;
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder != null && holder instanceof RecyclerViewAdapter.ViewHolder) {
            //((ViewHolder)holder).clearAnimation();
        }
    }

    //#region backup animation
    private void setAnimation(View viewToAnimate, int position) {
        // this code works, uncomment if your animation doesn't show
//        if (position > lastItemPosition){
//            Animation animation = AnimationUtils.loadAnimation(context, R.anim.left_to_right);
//            viewToAnimate.startAnimation(animation);
//            lastItemPosition = position;
//        }
    }
    //#endregion

    @Override
    public int getItemCount() {
        return mLstData.size();
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.footerProgressBar);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgProfilePic;
        ImageView imgBadgePic, imgItemPic, imgOpenMap;
        TextView tvPersonName, tvDescription;
        RelativeLayout parentLayout; // to attach onClickListener for the entire item
        LinearLayout linearLayout;

        public void clearAnimation() {
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
