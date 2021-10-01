package tk.quietdev.level1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import tk.quietdev.level1.databinding.ActivityTestBinding
import tk.quietdev.level1.ui.ListHolderView

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding.myHolder) {
            holderType = ListHolderView.HolderType.ADD
            title.text = "WTF"
            Log.d("TAG", "onCreate: ${title.textColors}")
            subtitle.text = "WTF2"
            setOnButtonClickListener {
                holderType =
                    if (holderType == ListHolderView.HolderType.ADD) ListHolderView.HolderType.REMOVE else ListHolderView.HolderType.ADD
            }
        }
    }
}