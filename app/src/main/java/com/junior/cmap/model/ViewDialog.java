package com.junior.cmap.model;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.junior.cmap.R;

public class ViewDialog {

    private Activity activity;
    private Dialog dialog;

    public ViewDialog(Activity activity) {
        this.activity = activity;
    }

    public void showDialog(String t, String d) {
        dialog  = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_loading_layout);
        TextView title = dialog.findViewById(R.id.title);
        TextView description = dialog.findViewById(R.id.description);

        title.setText(t);
        if(d.isEmpty()){
            description.setVisibility(View.GONE);
        }else{
            description.setVisibility(View.VISIBLE);
            description.setText(d);
        }
        ImageView gifImageView = dialog.findViewById(R.id.custom_loading_imageView);

        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifImageView);
        Glide.with(activity)
                .load(R.drawable.loading)
                .placeholder(R.drawable.loading)
                .centerCrop()
                .crossFade()
                .into(imageViewTarget);
        dialog.show();
    }

    public void hideDialog(){
        dialog.dismiss();
    }

}