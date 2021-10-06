package com.assignment.freeletics.presentation.shared.image

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.assignment.freeletics.R
import com.bumptech.glide.Glide

interface ImageLoader {

    fun load(imageView: ImageView, url: String?)

    class GlideImageLoader : ImageLoader {
        override fun load(imageView: ImageView, url: String?) {
            Glide.with(imageView)
                .load(url)
                .fallback(ColorDrawable(Color.GRAY))
                .placeholder(R.drawable.ic_android_black_24dp)
                .into(imageView)
        }

    }

}