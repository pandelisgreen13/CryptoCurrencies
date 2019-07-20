package com.pchasapis.cryptocurrency.common.extensions

import androidx.appcompat.widget.AppCompatImageView
import com.pchasapis.cryptocurrency.R
import com.squareup.picasso.Picasso


fun AppCompatImageView.loadUrl(url: String?) {
    if (url != null) {
        Picasso.get().load(url).placeholder(R.mipmap.ic_splash).into(this)
    } else {
        Picasso.get().load(R.mipmap.ic_splash).into(this)
    }
}

