package com.jm.filerecovery.videorecovery.photorecovery.model

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import com.jm.filerecovery.videorecovery.photorecovery.R
import kotlin.math.max
import kotlin.math.sqrt

open class RippleImageCustom :AppCompatImageView {

    constructor(context: Context) : super(context) {
        initAttrs(null)
    }

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        initAttrs(attributes)
    }
    private var rippleColor = Color.parseColor("#4DFFFFFF")
    protected var onClick:(()->Unit)? = null
    protected var instanClick:(()->Unit)? = null

    private var radiusCurrent = 0

    private var radiusMax = 0f
    private var mCurrentX = 0f
    private var mCurrentY = 0f

    private var isPress = false

    private var radiusConner = 0f


    private val mFillPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private fun initAttrs(attrs:AttributeSet?) {
        if(attrs == null) return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageRippleStyle)
        radiusConner = typedArray.getDimension(R.styleable.RippleView_cornerRadius, 0f)
        rippleColor = typedArray.getColor(R.styleable.ImageRippleStyle_rippleColor, Color.parseColor("#4DFFFFFF"))
        typedArray.recycle()

        mFillPaint.color = rippleColor
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if(event?.action == MotionEvent.ACTION_DOWN || event?.action == MotionEvent.ACTION_POINTER_DOWN) {
            instanClick?.invoke()
            mCurrentX = event.x
            mCurrentY = event.y
            radiusCurrent = 0
            radiusMax = max((sqrt(mCurrentX*mCurrentX+mCurrentY*mCurrentY)), sqrt((width-mCurrentX) *(width-mCurrentX)+(height-mCurrentY)*(height-mCurrentY))) + 100f
            drawRipple()
        }



        return true
    }

    fun getClipPath(): Path {
        val path = Path()
        path.reset()
        path.addRoundRect(RectF(0f,0f,width.toFloat(), height.toFloat()), radiusConner, radiusConner, Path.Direction.CW)
        path.close()
        return path
    }


    override fun onDraw(canvas: Canvas?) {
        canvas?.clipPath(getClipPath())
        super.onDraw(canvas)

        if(isPress) {
            canvas?.drawCircle(mCurrentX, mCurrentY, radiusCurrent.toFloat(), mFillPaint)
        }
    }

    private fun drawRipple() {
        isPress = true
        val animator = ValueAnimator.ofFloat(0f,radiusMax)
        animator.addUpdateListener {
            it.animatedFraction
            radiusCurrent = (radiusMax*it.animatedFraction).toInt()
            invalidate()
        }
        animator.duration = 200
        animator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {

                isPress = false
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationStart(animation: Animator) {

            }

        })
        animator.start()
    }

    fun setClick(onClick:()->Unit) {
        this.onClick = onClick
    }
    fun setInstanceClick(onClick:()->Unit) {
        this.instanClick = onClick
    }
}