package tk.quietdev.level1.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tk.quietdev.level1.R

typealias OnClick = () -> Unit


class ComposeCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    private val _text = mutableStateOf("")
    var text: String
        get() = _text.value
        set(value) {
            _text.value = value
        }

    private val _textSize = mutableStateOf(12)
    var textSize: Int
        get() = _textSize.value
        set(value) {
            _textSize.value = value
        }

    private val _icon = mutableStateOf(12)
    var icon: Int
        get() = _icon.value
        set(value) {
            _icon.value = value
        }

    private val _backColorId = mutableStateOf(12)
    var backColorId: Int
        get() = _backColorId.value
        set(value) {
            _backColorId.value = value
        }

    private val _cornerRadius = mutableStateOf(12)
    var cornerRadius: Int
        get() = _cornerRadius.value
        set(value) {
            _cornerRadius.value = value
        }

    private val _textColor = mutableStateOf(12)
    var textColor: Int
        get() = _textColor.value
        set(value) {
            _textColor.value = value
        }

    private val _onClick = mutableStateOf({})
    var onClick: OnClick
        get() = _onClick.value
        set(value) {
            _onClick.value = value
        }

    @Composable
    override fun Content() {
        CustomComposeButton(
            backColorId = backColorId,
            cornerRadius = cornerRadius,
            text = text,
            textColor = textColor,
            textSize = textSize,
            icon = icon,
        ) {
            onClick()
        }
    }

    @Composable
    private fun CustomComposeButton(
        backColorId: Int,
        cornerRadius: Int,
        text: String,
        textColor: Int,
        textSize: Int,
        icon: Int,
        onClick: () -> Unit = { Log.d("TAG", "CustomComposeButton: clicked") }
    ) {
        val radius = with(LocalDensity.current) { cornerRadius.dp / density }
        Row(
            Modifier
                .clip(RoundedCornerShape(radius))
                .fillMaxWidth()
                .background(
                    colorResource(id = backColorId),
                    shape = RoundedCornerShape(radius),
                )
                .clickable {
                    onClick()
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.padding(vertical = 8.dp),
                painter = painterResource(id = icon),
                contentDescription = null
            )
            Text(
                text = text.uppercase(),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp),
                color = colorResource(id = textColor),
                style = TextStyle(
                    letterSpacing = 2.sp,
                    fontSize = with(LocalDensity.current) { textSize.sp / density },
                    fontFamily = FontFamily(Font(R.font.open_sans_bold))
                ),
            )

        }

    }


    init {
        initAttributes(attrs, defStyleAttr)
    }


    private fun initAttributes(attributesSet: AttributeSet?, defStyleAttr: Int) {
        context.obtainStyledAttributes(
            attributesSet,
            R.styleable.ComposeCustomView,
            defStyleAttr,
            0
        ).apply {
            text = getString(R.styleable.ComposeCustomView_text) ?: ""
            textSize = getDimensionPixelSize(R.styleable.CustomButtonComponentView_textSize, 12)
            icon = getResourceId(R.styleable.ComposeCustomView_icon, 0)
            backColorId = getResourceId(R.styleable.ComposeCustomView_backgroundColor, 0)
            cornerRadius =
                getDimensionPixelSize(R.styleable.CustomButtonComponentView_cornerRadius, 12)
            textColor = getResourceId(R.styleable.ComposeCustomView_textColor, 0)
            recycle()
        }
    }

}


@Preview
@Composable
fun Prev() {

}