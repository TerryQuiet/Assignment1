package tk.quietdev.level1.ui.custom

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.PaintDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.children
import tk.quietdev.level1.R
import tk.quietdev.level1.databinding.CustomButtonLayoutBinding


class CustomButtonComponentView(
    context: Context,
    attributesSet: AttributeSet?,
    defStyleAttr: Int
) : LinearLayoutCompat(context, attributesSet, defStyleAttr) {

    private val binding: CustomButtonLayoutBinding

    var text: String = ""
        set(value) {
            field = value
            bindValues()
        }

    var textSize: Int = 12
        set(value) {
            field = value
            bindValues()
        }

    var icon: Int? = null
        set(value) {
            field = value
            bindValues()
        }

    var backColor: Int = Color.WHITE
        set(value) {
            field = value
            bindValues()
        }

    var cornerRadius: Int = 12
        set(value) {
            field = value
            bindValues()
        }

    var textColor: Int = Color.BLACK
        set(value) {
            field = value
            bindValues()
        }


    //   if global style is defined in app theme by using customButtonComponentView attribute ->
    //   all customButtonComponent views in the project will use that style
    constructor(context: Context, attributesSet: AttributeSet?) : this(
        context,
        attributesSet,
        R.attr.CustomButtonComponentViewStyle
    )

    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.custom_button_layout, this, true)
        binding = CustomButtonLayoutBinding.bind(this)
        initAttributes(attributesSet, defStyleAttr)
        bindValues()
        onClickListener()
    }

    private fun bindValues() {
        with(binding) {
            label.text = text
            label.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                textSize.toFloat()
            )
            label.setTextColor(textColor)
            icon?.let {
                image.setImageResource(it)
            }
        }
        gravity = Gravity.CENTER
        val backGround = PaintDrawable(backColor).apply {
            setCornerRadius(cornerRadius.toFloat())

        }
        background = backGround
    }

    private fun initAttributes(attributesSet: AttributeSet?, defStyleAttr: Int) {
        context.obtainStyledAttributes(
            attributesSet,
            R.styleable.CustomButtonComponentView,
            defStyleAttr,
            0
        ).apply {
            text = getString(R.styleable.CustomButtonComponentView_text) ?: ""
            textSize = getDimensionPixelSize(R.styleable.CustomButtonComponentView_textSize, 12)
            icon = getResourceId(R.styleable.CustomButtonComponentView_icon, 0)
            backColor = getColor(R.styleable.CustomButtonComponentView_backgroundColor, Color.WHITE)
            cornerRadius =
                getDimensionPixelSize(R.styleable.CustomButtonComponentView_cornerRadius, 12)
            textColor = getColor(R.styleable.CustomButtonComponentView_textColor, Color.BLACK)
            recycle()
        }
    }

    // Right now it works like toggle
    fun changeIconPositon() {
        val list = mutableListOf<View>()
        for (child in children) {
            list.add(child)
        }
        removeAllViews()
        list.reverse()
        for (view in list) {
            addView(view)
        }
    }

    private fun onClickListener() {
        setOnClickListener {
            background.alpha = 1
            it.background.alpha = 1
        }
    }


}