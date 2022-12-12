package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private var loadingProgress: Float = 0f

    private val valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 4000
        interpolator = LinearInterpolator()
        addUpdateListener {
            loadingProgress = it.animatedValue as Float
            if(loadingProgress == 1.0f )
            {
                buttonState = ButtonState.Completed
            }
            invalidate()
        }
    }


    private var text = context.getString(R.string.download)
    private var colorText = 0
    private var colorBackground = 0
    private val darkBackgroundColor = context.getColor(R.color.colorPrimaryDark)
    private var loading = false
    private val circularIndicatorColor = context.getColor(R.color.colorAccent)
    private val circularProgressRadius = 30f



    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

        when (new) {
            ButtonState.Completed -> {
                valueAnimator.cancel()
                text = context.getString(R.string.download)
                loading = false
            }
            ButtonState.Clicked -> {
                valueAnimator.start()
                text = context.getString(R.string.we_are_loading)
                loading = true
            }
            ButtonState.Loading -> {
                text = context.getString(R.string.we_are_loading)
                loading = true
            }
        }


    }


    init {
        /** enable performClick**/
        isClickable = true

        /** initialize custom attributes**/
        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
            colorText = getColor(R.styleable.LoadingButton_LoadingButtonTextColor, 0)
            colorBackground = getColor(R.styleable.LoadingButton_LoadingButtonBackgroundColor, 0)

        }

    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        color = colorBackground
    }

    override fun performClick(): Boolean {
        /** the call super.performClick() must happen first **/
        super.performClick()
        /** actions to performed on click **/
        buttonState = ButtonState.Clicked
        /** invalidate changes**/
        invalidate()

        return true
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    /** calculate  size variables only on Size Changed, instead of each time in onDraw() method  **/
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        widthSize = w
        heightSize = h
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.save()
        paint.color = colorBackground
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), paint)
        canvas?.restore()
        canvas?.save()
        if(loading){
        paint.color = darkBackgroundColor
        canvas?.drawRect(
            0f,
            0f,
            loadingProgress * widthSize.toFloat(),
            heightSize.toFloat(),
            paint
        )
        canvas?.restore()
        canvas?.save()
        }
        /** text shifted upwards by text size value
         * -> add paint.textSize to height half to center text vertically **/
        canvas?.translate(
            (0.5 * widthSize).toFloat(),
            (0.5 * (heightSize + paint.textSize)).toFloat()
        )
        paint.color = colorText
        canvas?.drawText(text, 0f, 0f, paint)
        canvas?.restore()
        canvas?.save()
        if (loading) {
            /** add text size to x so  that circle locate after text **/
            canvas?.translate(
                (0.5 * widthSize + paint.measureText(text)).toFloat(),
                (0.5 * (heightSize) + 10f).toFloat()
            )
            paint.color = Color.GRAY
            val drawCircle = canvas?.drawCircle(0f, 0f, circularProgressRadius, paint)
            canvas?.restore()
            canvas?.save()

            canvas?.translate(
                (0.5 * widthSize + paint.measureText(text)).toFloat(),
                (0.5 * (heightSize) + 10f).toFloat()
            )
            paint.color = circularIndicatorColor
            canvas?.drawArc( -circularProgressRadius,-circularProgressRadius,circularProgressRadius,circularProgressRadius,0f,360*loadingProgress,true,paint)
            canvas?.restore()
            canvas?.save()

        }


    }


}