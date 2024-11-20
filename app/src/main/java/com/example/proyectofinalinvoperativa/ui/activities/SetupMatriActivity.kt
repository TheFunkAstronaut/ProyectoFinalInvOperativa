package com.example.proyectofinalinvoperativa.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinalinvoperativa.R
import com.example.proyectofinalinvoperativa.databinding.ActivitySetupMatriBinding
import com.example.proyectofinalinvoperativa.ui.adapters.CounterAdapter
import com.example.proyectofinalinvoperativa.ui.viewmodels.SetupViewmodel
import kotlin.math.log


class SetupMatriActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetupMatriBinding
    private var filas = 1
    private var columnas = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupMatriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRowCounter()
        setupColumnCounter()
        setupEventListeners()
    }

    private fun setupRowCounter() {
        binding.decrementRowButton.setOnClickListener {
            if (filas > 1) {
                filas--
                binding.rowCountText.text = filas.toString()
            }
        }
        binding.incrementRowButton.setOnClickListener {
            filas++
            binding.rowCountText.text = filas.toString()
        }
    }

    private fun setupColumnCounter() {
        binding.decrementColumnButton.setOnClickListener {
            if (columnas > 1) {
                columnas--
                binding.columnCountText.text = columnas.toString()
            }
        }
        binding.incrementColumnButton.setOnClickListener {
            columnas++
            binding.columnCountText.text = columnas.toString()
        }
    }

    private fun setupEventListeners() {
        binding.btnContinue.setOnClickListener {
            val intent = Intent(this, MatrixActivity::class.java).apply {
                putExtra("Filas", filas)
                putExtra("Columnas", columnas)
            }
            startActivity(intent)
        }
    }
}
