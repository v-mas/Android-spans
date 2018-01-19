package com.concisesoftware.text.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.SpannableStringBuilder
import android.text.style.ReplacementSpan


class TrailingSpaceSpan: ReplacementSpan() {
    companion object {
        const val SPACE = " "
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        val space = paint.measureText(SPACE)
        val size = paint.measureText(text, start, end)
        return (space + size + 0.5f).toInt()
    }

    override fun draw(canvas: Canvas,
                      text: CharSequence,
                      start: Int,
                      end: Int,
                      x: Float,
                      top: Int,
                      y: Int,
                      bottom: Int,
                      paint: Paint) {
        val textWithSpace = SpannableStringBuilder(text, start, end).append(SPACE)
        canvas.drawText(textWithSpace, 0, textWithSpace.length, x.toFloat(), y.toFloat(), paint)
    }
}