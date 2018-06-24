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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ishaycena.tabfragments.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private class ItemTypes {
        private static final int NORMAL = 0, FOOTER = 1;
    }

    private static final String TAG = "RecyclerViewAdapter";

    // vars
    private Context context;
    public ArrayList<Found> lstFounds;
    private int lastItemPosition = -1;

    @Override
    public int getItemViewType(int position) {
        if (lstFounds.get(position) != null) {
            return ItemTypes.NORMAL;
        } else {
            return ItemTypes.FOOTER;
        }
    }

    public RecyclerViewAdapter(Context context, ArrayList<Found> founds) {
        this.context = context;
        this.lstFounds = founds;
    }

    public void addItem(Found found){
        lstFounds.add(found);
        notifyDataSetChanged();
    }

    public void addItemWithHandler(Found found) {
        lstFounds.add(found);
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
                    .load(lstFounds.get(position).getImgProfileUrl())
                    .into(mHolder.imgProfilePic);
            // badge
            Glide.with(context)
                    .asBitmap()
                    .load(lstFounds.get(position).getImgBadgeUrl())
                    .into(mHolder.imgBadgePic);
            // found item
            Glide.with(context)
                    .asBitmap()
                    .load(lstFounds.get(position).getImgItem())
                    .into(mHolder.imgItemPic);
            // open map
            Glide.with(context)
                    .load(R.drawable.ic_map)
                    .into(mHolder.imgOpenMap);

            // person name
            mHolder.tvPersonName.setText(lstFounds.get(position).getPersonName());

            // description
            mHolder.tvDescription.setText(lstFounds.get(position).getDescription());

            // start the slide-in animation
//         setAnimation(holder.itemView, position);
            Animation animation = AnimationUtils.loadAnimation(context,
                    (position > lastItemPosition) ? R.anim.up_from_bottom
                            : R.anim.down_from_top);
            holder.itemView.startAnimation(animation);
            lastItemPosition = position;

            mHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: clicked item");

                    Toast.makeText(context, "clicked:\n" + lstFounds.get(position).toString(), Toast.LENGTH_SHORT).show();
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
        return lstFounds.size();
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
