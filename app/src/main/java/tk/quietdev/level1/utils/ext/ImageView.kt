package tk.quietdev.level1.utils.ext


import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .into(this)
}


fun ImageView.loadImage(resource: Int) {
    Glide.with(this)
        .load(resource)
        .circleCrop()
        .into(this)
}



