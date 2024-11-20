package com.example.proyectofinalinvoperativa.ui.activities

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofinalinvoperativa.R
import com.example.proyectofinalinvoperativa.databinding.ActivityMatrixBinding

class MatrixActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatrixBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatrixBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filas = intent.getIntExtra("Filas", 1)
        val columnas = intent.getIntExtra("Columnas", 1)

        setupMatrix(filas, columnas)
    }

    private fun setupMatrix(filas: Int, columnas: Int) {
        binding.gridLayout.columnCount = columnas

        for (i in 0 until filas) {
            for (j in 0 until columnas) {
                val editText = EditText(this).apply {
                    hint = "(${i + 1}, ${j + 1})"
                    gravity = Gravity.CENTER
                    inputType = InputType.TYPE_CLASS_NUMBER
                }
                binding.gridLayout.addView(editText)
            }
        }
    }
}

