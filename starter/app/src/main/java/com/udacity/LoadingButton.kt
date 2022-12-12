package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private val valueAnimator = ValueAnimator()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        //TODO Pass color as attribute
        color = context.getColor(R.color.colorPrimary)
    }
    //TODO Pass text as attribute
    private val text = "Download"
    //TODO Pass color text as attribute
    private val colorText = context.getColor(R.color.white)


    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

    }



    init {

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
        canvas?.drawRect(0f,0f,widthSize.toFloat(),heightSize.toFloat(),paint)
        canvas?.restore()
        canvas?.save()
        /** add paint.textSize to height half to center text vertically  **/
        canvas?.translate((0.5*widthSize).toFloat(),(0.5*(heightSize+paint.textSize)).toFloat())
        paint.color = colorText
        canvas?.drawText(text,0f,0f,paint)
        canvas?.restore()


    }



}