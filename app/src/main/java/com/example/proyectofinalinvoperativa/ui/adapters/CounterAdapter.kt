package com.example.proyectofinalinvoperativa.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalinvoperativa.databinding.ItemCounterBinding

class CounterAdapter(private val counters: MutableList<Int>, private val onCountChanged: (Int, Int) -> Unit) : RecyclerView.Adapter<CounterAdapter.CounterViewHolder>() {

    class CounterViewHolder(val binding: ItemCounterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterViewHolder {
        val binding = ItemCounterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CounterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CounterViewHolder, position: Int) {
        val counter = counters[position]
        with(holder.binding) {
            tvCounter.text = counter.toString()

            // Botón de decremento
            btnMinus.setOnClickListener {
                if (counters[position] > 0) {
                    counters[position]--
                    tvCounter.text = counters[position].toString()
                    onCountChanged(counters[0], counters[1])  // Llamar al callback con los valores actuales
                }
            }

            // Botón de incremento
            btnPlus.setOnClickListener {
                counters[position]++
                tvCounter.text = counters[position].toString()
                onCountChanged(counters[0], counters[1])  // Llamar al callback con los valores actuales
            }
        }
    }

    override fun getItemCount(): Int = counters.size
}


