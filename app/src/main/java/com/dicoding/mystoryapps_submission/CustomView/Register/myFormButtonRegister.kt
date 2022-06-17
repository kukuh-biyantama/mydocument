package com.dicoding.mystoryapps_submission.CustomView.Register

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.mystoryapps_submission.R


class myFormButtonRegister : AppCompatButton {
    private lateinit var enabledBackground: Drawable
    private lateinit var disabledBackground: Drawable
    private var txtColor: Int = 0
    //turunan kelas dari AppCompatButton
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    //kelas view untuk mengubah bentuk dari button
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //memasukkan background dan attribute
        background = if(isEnabled) enabledBackground else disabledBackground
        setTextColor(txtColor)
        textSize = 12f
        gravity = Gravity.CENTER
        text = if(isEnabled) "Register" else "Register"
    }

    //fungsi dari init
    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.backgroundbuttonsubregister) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.backgroundregisterdisable) as Drawable
    }
}