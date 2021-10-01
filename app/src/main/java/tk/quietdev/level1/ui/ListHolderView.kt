package tk.quietdev.level1.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.ListItemCustomBinding

typealias OnButtonClicked = () -> Unit


class ListHolderView(
    context: Context,
    attributesSet: AttributeSet?,
    defStyleAttr: Int,
) : MaterialCardView(context, attributesSet, defStyleAttr) {
    constructor(context: Context, attributesSet: AttributeSet?) : this(
        context,
        attributesSet,
        R.attr.materialCardViewStyle
    )

    constructor(context: Context) : this(context, null)

    private lateinit var binding: ListItemCustomBinding

    var holderType = HolderType.REMOVE
        set(value) {
            field = value
            updateType()
        }

    var buttonState = ButtonState.NORMAL
        set(value) {
            field = value
            updateButtonState()
        }

    var multiselectState = false
        set(value) {
            field = value
            updateMultiselect()
        }

    var isHolderChecked = false
        set(value) {
            field = value
            updateChecked()
        }

    private fun updateChecked() {
        binding.cbRemove.isChecked = isHolderChecked
        if (isHolderChecked) {
            binding.background.setBackgroundColor(Color.GRAY)
        } else {
            binding.background.setBackgroundColor(Color.WHITE)
        }
    }

    private fun updateMultiselect() {
        if (multiselectState) {
            binding.cbRemove.visibility = VISIBLE
        } else {
            binding.cbRemove.visibility = GONE
        }
    }

    val title by lazy { binding.tvName }

    val subtitle by lazy { binding.tvOccupation }

    val picture by lazy { binding.ivProfilePic }


    private fun updateButtonState() {
        with(binding) {
            when (buttonState) {
                ButtonState.NORMAL -> {
                    updateType()
                }
                ButtonState.SPINNER -> {
                    spinner.visibility = VISIBLE
                    groupAdd.visibility = GONE
                    imageBtnRemove.visibility = GONE
                    ivDone.visibility = GONE
                }
                ButtonState.DONE -> {
                    ivDone.visibility = VISIBLE
                    spinner.visibility = GONE
                    groupAdd.visibility = GONE
                    imageBtnRemove.visibility = GONE
                    Log.d("TAG", "updateButtonState: ")
                }
            }
        }
    }

    private fun updateType() {
        with(binding) {
            when (holderType) {
                HolderType.ADD -> {
                    groupAdd.visibility = VISIBLE
                    imageBtnRemove.visibility = GONE
                }
                HolderType.REMOVE -> {
                    imageBtnRemove.visibility = VISIBLE
                    groupAdd.visibility = GONE
                }
            }
        }
        initListener()
    }

    private var listener: OnButtonClicked? = null

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.list_item_custom, this, true)
        binding = ListItemCustomBinding.bind(this)
        initAttributes(attributesSet, defStyleAttr)
        initListener()
    }

    private fun initAttributes(attributesSet: AttributeSet?, defStyleAttr: Int) {
        //if (attributesSet == null) return
        val typedArray = context.obtainStyledAttributes(attributesSet, R.styleable.ListHolderView)
        title.setTextColor(Color.BLACK)
        subtitle.setTextColor(Color.BLACK)
        binding.background.setBackgroundColor(
            typedArray.getColor(
                R.styleable.ListHolderView_backgroundColor,
                Color.WHITE
            )
        )
        picture.setImageResource(
            typedArray.getResourceId(
                R.styleable.ListHolderView_holderPicture,
                R.drawable.ic_profile
            )
        )
        title.text = typedArray.getString(R.styleable.ListHolderView_holderTitle) ?: ""
        subtitle.text = typedArray.getString(R.styleable.ListHolderView_holderSubtitle) ?: ""
        holderType =
            HolderType.values()[typedArray.getInt(R.styleable.ListHolderView_holderType, 0)]
        updateType()
        typedArray.recycle()
    }

    fun setOnButtonClickListener(listener: OnButtonClicked) {
        this.listener = listener
    }


    private fun initListener() {
        with(binding) {
            when (holderType) {
                HolderType.ADD -> {
                    buttonAdd.setOnClickListener { listener?.invoke() }
                }
                HolderType.REMOVE -> {
                    imageBtnRemove.setOnClickListener { listener?.invoke() }
                }
            }
        }
    }

    enum class HolderType {
        ADD, REMOVE
    }

    enum class ButtonState {
        NORMAL, SPINNER, DONE
    }

}