package com.concisesoftware.text.span

import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher


class CreditCardTextWatcher: TextWatcher {
    private val everyPos = 4

    override fun afterTextChanged(s: Editable) {
        val allSpans = s.getSpans(0, s.length, TrailingSpaceSpan::class.java).toMutableList()
        val end = s.length
        var i = everyPos
        while (i < end) {
            val spans = s.getSpans(i - 1, i, TrailingSpaceSpan::class.java)
            if (spans.isEmpty()) {
                s.setSpan(TrailingSpaceSpan(), i - 1, i, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else {
                allSpans.removeAll(spans)
            }

            i += everyPos
        }
        allSpans.forEach {
            s.removeSpan(it)
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // nop.
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        // nop.
    }
}