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
import com.example.ishaycena.tabfragments.MapService.LocationActivity;
import com.example.ishaycena.tabfragments.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private class ItemTypes {
        private static final int NORMAL = 0, FOOTER = 1;
    }

    private static final String TAG = "RecyclerViewAdapter";

    // vars
    private Context context;
    public ArrayList<Found> mLstFounds;
    private int lastItemPosition = -1;

    @Override
    public int getItemViewType(int position) {
        if (mLstFounds.get(position) != null) {
            return ItemTypes.NORMAL;
        } else {
            return ItemTypes.FOOTER;
        }
    }

    public RecyclerViewAdapter(Context context, ArrayList<Found> founds) {
        this.context = context;
        this.mLstFounds = founds;
    }

    public void addItem(Found found){
        mLstFounds.add(found);
        notifyDataSetChanged();
    }

    public void addFetchedItem(List<com.example.ishaycena.tabfragments.FoundService.Found> lstFounds) {
        for (com.example.ishaycena.tabfragments.FoundService.Found found : lstFounds) {
            final Found adapterFound = new Found();
            // add item picture
            Glide.with(context)
                    .asBitmap()
                    .load(found.getmFoundImageUrl())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            adapterFound.setImgItem(resource);
                        }
                    });
            // profile pic
            Glide.with(context)
                    .asBitmap()
                    .load(mLstFounds.get(0).getImgProfileUrl())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            adapterFound.setImgProfileUrl(resource);
                        }
                    });
            // badge
            Glide.with(context)
                    .asBitmap()
                    .load(mLstFounds.get(0).getImgProfileUrl())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            adapterFound.setImgProfileUrl(resource);
                        }
                    });
            // desc
            adapterFound.setDescription(found.getmFoundDescription());
            // username
            adapterFound.setPersonName(found.getmUserName());
            adapterFound.setCustomLatLong(found.getmLatLong());

            // at last, add the item to the list
            mLstFounds.add(adapterFound);
        }
        notifyDataSetChanged();
    }

    public void addItemWithHandler(Found found) {
        mLstFounds.add(found);
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        if (getItemViewType(position) == ItemTypes.NORMAL) {
            //#region normal found item

            ViewHolder mHolder = (ViewHolder) holder;
            // profile pic
            Glide.with(context)
                    .asBitmap()
                    .load(mLstFounds.get(position).getImgProfileUrl())
                    .into(mHolder.imgProfilePic);
            // badge
            Glide.with(context)
                    .asBitmap()
                    .load(mLstFounds.get(position).getImgBadgeUrl())
                    .into(mHolder.imgBadgePic);
            // found item
            Glide.with(context)
                    .asBitmap()
                    .load(mLstFounds.get(position).getImgItem())
                    .into(mHolder.imgItemPic);
            // open map
            Glide.with(context)
                    .load(R.drawable.ic_map)
                    .into(mHolder.imgOpenMap);

            // person name
            mHolder.tvPersonName.setText(mLstFounds.get(position).getPersonName());

            // description
            mHolder.tvDescription.setText(mLstFounds.get(position).getDescription());

            // start the slide-in animation
//         setAnimation(holder.itemView, position);
            Animation animation = AnimationUtils.loadAnimation(context,
                    (position > lastItemPosition) ? R.anim.up_from_bottom
                            : R.anim.down_from_top);
            holder.itemView.startAnimation(animation);
            lastItemPosition = position;

            mHolder.imgOpenMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent locationIntent = new Intent(context, LocationActivity.class);
                    locationIntent.putExtra("LOCATION", mLstFounds.get(position).getCustomLatLong());
                    context.startActivity(locationIntent);
                }
            });

            mHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: clicked item");

                    Toast.makeText(context, "clicked:\n" + mLstFounds.get(position).toString(), Toast.LENGTH_SHORT).show();
                }
            });
            //#endregion
        } else {
            // TODO add a new view holder static class of a progressbar
            if (holder instanceof FooterViewHolder) {
                FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
                footerViewHolder.progressBar.setIndeterminate(true);
                lastItemPosition = position;
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder != null && holder instanceof ViewHolder) {
            //((ViewHolder)holder).clearAnimation();
        }
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
        return mLstFounds.size();
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.footerProgressBar);
        }
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
