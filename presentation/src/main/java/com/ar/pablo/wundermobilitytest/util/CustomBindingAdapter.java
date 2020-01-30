package com.ar.pablo.wundermobilitytest.util;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.ar.pablo.wundermobilitytest.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Singleton;

@Singleton
public class CustomBindingAdapter {
    @BindingAdapter({"image_url"})
    public static void loadImage(ImageView imageView, String url) {
        Glide
                .with(imageView.getContext())
                .load(url)
                .apply(
                        new RequestOptions()
                                .placeholder(R.drawable.loading_animation)
                                .error(R.drawable.ic_broken_image)
                )
                .into(imageView);
    }

    @BindingAdapter({"text"})
    public static void setText(TextView textView, String text) {
        if (text != null && !text.trim().isEmpty()) {
            textView.setText(text);
        } else {
            textView.setText(R.string.no_data);
        }
    }
}
