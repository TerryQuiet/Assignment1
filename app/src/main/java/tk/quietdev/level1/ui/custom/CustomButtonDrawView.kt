package tk.quietdev.level1.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

@Deprecated("Undone, Abandoned. May come back later.")
class CustomButtonDrawView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val drawTextCoordinate = Coordinate()

    var customText: String = "SSSS"
        set(value) {
            field = value
            invalidate()
        }

    var fontSize: Int = 30
        set(value) {
            field = value
            invalidate()
        }

    var paintText: Paint

    var textWidth = 0
    var textHeight = 0

    init {
        paintText = getBasePaint().apply {
            color = Color.BLACK
            textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                fontSize.toFloat(), resources.displayMetrics
            )
            style = Paint.Style.FILL
        }
    }

    private fun getBasePaint(): Paint {
        return Paint().apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(customText, 20f, 20f, paintText)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        // here we can submit our suggestion how large our view should be

        // min size of our view
        val minWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val minHeight = suggestedMinimumHeight + paddingTop + paddingBottom

        // calculating desired size of view
        val desiredHeight =
            paintText.measureText(customText)
        // submit view size
        setMeasuredDimension(
            resolveSize(30, widthMeasureSpec),
            resolveSize(desiredHeight.toInt(), heightMeasureSpec)
        )
    }


    class Coordinate(var x: Float = 0f, var y: Float = 0f)
}
