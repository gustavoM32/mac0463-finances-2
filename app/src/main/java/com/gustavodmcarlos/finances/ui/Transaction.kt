package com.gustavodmcarlos.finances.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import com.gustavodmcarlos.finances.R
import com.gustavodmcarlos.finances.databinding.ActivityMapsBinding
import com.gustavodmcarlos.finances.databinding.TransactionBinding
import kotlin.math.abs

class Transaction @JvmOverloads
    constructor(private val ctx: Context, private val attributeSet: AttributeSet? = null, private val defStyleAttr: Int = 0)
    : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    private lateinit var binding: TransactionBinding

    init {

        // get the inflater service from the android system
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // inflate the layout into "this" component
        binding = TransactionBinding.inflate(inflater, this)
    }

    fun load(transactionInfo : TransactionInfo) {
        val context = context as Context
        binding.icon.setImageResource(transactionInfo.icon)
        binding.title.text = transactionInfo.title
        binding.description.text = transactionInfo.description
        binding.date.text = transactionInfo.date
        binding.value.text = formatMoney(transactionInfo.value)
        if (transactionInfo.value < 0) {
            binding.value.setTextColor(Color.parseColor("#de0d0d"))
        } else {
            binding.value.setTextColor(Color.parseColor("#03ad12"))
        }
    }

    fun formatMoney(value : Double): String {
        var prefix = ""
        if (value < 0) {
            prefix += "-"
        }
        return "%s$%.2f".format(prefix, abs(value))
    }
}