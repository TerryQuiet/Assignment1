package tk.quietdev.level1.utils.ext


import android.net.Uri
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

fun ImageView.loadImage(url: Uri) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .into(this)
}

/*fun ImageView.loadImage(user: User) {
    val isLocal = user.isPictureLocal
    Glide.with(this)
        .load(if (isLocal) user.picture.toUri() else user.picture)
        .circleCrop()
        .into(this)
}*/




