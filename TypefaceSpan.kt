package com.concisesoftware.text.span

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

/**
 * Like [android.text.style.TypefaceSpan] but using user provided [Typeface] rather than using system
 * provided
 */
class TypefaceSpan(private val mTypeface: Typeface): MetricAffectingSpan() {
    override fun updateDrawState(ds: TextPaint) {
        apply(ds, mTypeface)
    }

    override fun updateMeasureState(paint: TextPaint) {
        apply(paint, mTypeface)
    }

    private fun apply(paint: Paint, tf: Typeface) {
        val oldStyle = paint.typeface?.style ?: 0

        val fake = oldStyle and tf.style.inv()

        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }

        paint.typeface = tf
    }
}
