package com.github.drewstephensdesigns.astrofeed.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.method.Touch
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat

class HTMLView : AppCompatTextView {

    private var linkHit = false
    private var consumeNonUrlClicks = false
    var plainText = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        linkHit = false

        val res = super.onTouchEvent(event)

        if (!consumeNonUrlClicks) return linkHit

        return res
    }

    fun setHtmlText(text: String) {
        val htmlText = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

        setText(if (!plainText) htmlText else htmlText.toString().replace("\n", " "))
    }

    override fun hasFocusable(): Boolean = false

}