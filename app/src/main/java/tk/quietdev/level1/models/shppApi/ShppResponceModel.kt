package tk.quietdev.level1.models.shppApi

data class ShppResponceModel(
    val code: Int,
    val `data`: Data,
    val message: String,
    val status: String
)