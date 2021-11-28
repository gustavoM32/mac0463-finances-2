package com.gustavodmcarlos.finances.ui

import android.content.Context
import android.graphics.Color

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.gustavodmcarlos.finances.databinding.TransactionBinding
import kotlin.math.abs

class TransactionsAdapter(context: Context, transactions: Array<Transaction>) :
    ArrayAdapter<Transaction?>(context, 0, transactions) {

    private lateinit var binding: TransactionBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val transaction: Transaction? = getItem(position)
        val inflater = LayoutInflater.from(context)

//        var convertView: View? = convertView
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.transaction, parent, false)
//        }

        binding = TransactionBinding.inflate(inflater, parent, false)

        if (transaction != null) {
            binding.icon.setImageResource(transaction.icon)
            binding.icon.setImageResource(transaction.icon)
            binding.title.text = transaction.title
            binding.description.text = transaction.description
            binding.date.text = transaction.date
            binding.value.text = formatMoney(transaction.value)
            if (transaction.value < 0) {
                binding.value.setTextColor(Color.parseColor("#de0d0d"))
            } else {
                binding.value.setTextColor(Color.parseColor("#03ad12"))
            }
        }

        return binding.root
//        return convertView!!
    }

    private fun formatMoney(value : Double): String {
        var prefix = ""
        if (value < 0) {
            prefix += "-"
        }
        return "%s$%.2f".format(prefix, abs(value))
    }
}

