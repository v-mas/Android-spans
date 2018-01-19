package com.concisesoftware.text.span

import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.IntRange
import android.text.SpannableStringBuilder
import android.text.style.ReplacementSpan

/**
 * Span that inserts given [text] every given [everyPos] space
 */
class InsertTextSpan(private val text: CharSequence, @IntRange(from = 1) private val everyPos: Int): ReplacementSpan() {
    init {
        assert(everyPos > 0)
    }

    /**
     * count how many times [everyPos] is inside given range
     *
     * @param start the start index (inclusive).
     * @param end the end index (exclusive).
     */
    private fun countOccurrences(start: Int, end: Int): Int = (((end - 1) / everyPos) - (start / everyPos))

    /**
     * inserts [text] in target text
     *
     * @param text text to insert to
     * @param start the start index (inclusive).
     * @param end the end index (exclusive).
     */
    private fun insertText(text: CharSequence, start: Int, end: Int): CharSequence {
        val builder = StringBuilder()

        var i = start
        var next = ((start / everyPos) * everyPos) + everyPos
        while (i < end) {
            if (next < end) {
                builder.append(text.subSequence(i, next))
                builder.append(this.text)
            } else {
                builder.append(text.subSequence(i, end))
                break
            }
            i = next
            next += everyPos
        }
        return builder
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        val insertSize = paint.measureText(this.text, 0, this.text.length)
        val originalSize = paint.measureText(text, start, end)
        val timesOccurs = countOccurrences(start, end)
        return (originalSize + (insertSize * timesOccurs)).toInt()
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val wholeText = insertText(text, start, end)
        canvas.drawText(wholeText, 0, wholeText.length, x.toFloat(), y.toFloat(), paint)
    }
}