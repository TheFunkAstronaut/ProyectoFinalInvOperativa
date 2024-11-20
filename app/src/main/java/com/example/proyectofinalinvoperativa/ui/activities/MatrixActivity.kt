package com.example.proyectofinalinvoperativa.ui.activities

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofinalinvoperativa.R
import com.example.proyectofinalinvoperativa.databinding.ActivityMatrixBinding

class MatrixActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatrixBinding
    private lateinit var matrizOriginal: Array<Array<Int>>
    private lateinit var matrizIntermedia: Array<Array<Int>>
    private var pasoActual = 0
    private val pasos = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatrixBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filas = intent.getIntExtra("Filas", 1)
        val columnas = intent.getIntExtra("Columnas", 1)

        setupMatrix(filas, columnas)

        binding.btnCalculate.setOnClickListener {
            matrizOriginal = recolectarDatos(filas, columnas)
            iniciarCalculo()
        }
    }

    private fun setupMatrix(filas: Int, columnas: Int) {
        binding.gridLayout.columnCount = columnas

        for (i in 0 until filas) {
            for (j in 0 until columnas) {
                val editText = EditText(this).apply {
                    hint = "(${i + 1}, ${j + 1})"
                    gravity = Gravity.CENTER
                    inputType = InputType.TYPE_CLASS_NUMBER
                    setPadding(8, 8, 8, 8) // Añade relleno interno
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 150 // Tamaño mínimo
                        height = 150
                        setMargins(8, 8, 8, 8) // Márgenes entre elementos
                    }
                }
                binding.gridLayout.addView(editText)
            }
        }
    }

    private fun recolectarDatos(filas: Int, columnas: Int): Array<Array<Int>> {
        val matriz = Array(filas) { Array(columnas) { 0 } }
        var index = 0
        for (i in 0 until filas) {
            for (j in 0 until columnas) {
                val editText = binding.gridLayout.getChildAt(index) as EditText
                val value = editText.text.toString().toIntOrNull() ?: 0
                matriz[i][j] = value
                index++
            }
        }
        return matriz
    }

    private fun iniciarCalculo() {
        matrizIntermedia = matrizOriginal.map { it.clone() }.toTypedArray()

        pasos.clear()
        pasos.add("Reducción de filas: Restar el mínimo de cada fila.")
        pasos.add("Reducción de columnas: Restar el mínimo de cada columna.")
        pasos.add("Cubrir ceros con líneas mínimas.")
        pasos.add("Ajustar matriz para obtener más ceros.")
        pasos.add("Resultados finales.")

        pasoActual = 0
        mostrarPaso()
    }

    private fun mostrarPaso() {
        if (pasoActual < pasos.size) {
            binding.btnCalculate.text = "Siguiente Paso"
            binding.btnCalculate.setOnClickListener { avanzarPaso() }

            binding.textExplanation.text = pasos[pasoActual]
            actualizarVistaMatriz(matrizIntermedia)
        } else {
            binding.textExplanation.text = "¡Método completado! Optimización lograda."
            binding.btnCalculate.isEnabled = false
        }
    }

    private fun avanzarPaso() {
        when (pasoActual) {
            0 -> reducirFilas(matrizIntermedia)
            1 -> reducirColumnas(matrizIntermedia)
            2 -> cubrirCeros(matrizIntermedia)
            3 -> ajustarMatriz(matrizIntermedia)
        }
        pasoActual++
        mostrarPaso()
    }

    private fun reducirFilas(matriz: Array<Array<Int>>) {
        for (i in matriz.indices) {
            val min = matriz[i].minOrNull() ?: 0
            for (j in matriz[i].indices) {
                matriz[i][j] -= min
            }
        }
    }

    private fun reducirColumnas(matriz: Array<Array<Int>>) {
        for (j in matriz[0].indices) {
            val min = matriz.minOf { it[j] }
            for (i in matriz.indices) {
                matriz[i][j] -= min
            }
        }
    }

    private fun cubrirCeros(matriz: Array<Array<Int>>) {
        // Ejemplo de lógica para cubrir filas y columnas con líneas
        val filasCubiertas = setOf(0, 2) // Ejemplo: filas a cubrir
        val columnasCubiertas = setOf(1) // Ejemplo: columnas a cubrir

        binding.textExplanation.text = "Cubriendo filas: $filasCubiertas y columnas: $columnasCubiertas"
        marcarFilasYColumnas(matriz, filasCubiertas, columnasCubiertas)
    }

    private fun ajustarMatriz(matriz: Array<Array<Int>>) {
        // Aquí implementaremos lógica para ajustar la matriz
    }

    private fun actualizarVistaMatriz(matriz: Array<Array<Int>>) {
        binding.gridLayout.removeAllViews()
        for (i in matriz.indices) {
            for (j in matriz[i].indices) {
                val textView = TextView(this).apply {
                    text = matriz[i][j].toString()
                    gravity = Gravity.CENTER
                    setPadding(8, 8, 8, 8) // Añade relleno interno
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 150 // Tamaño mínimo
                        height = 150
                        setMargins(8, 8, 8, 8) // Márgenes entre elementos
                    }
                    setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray)) // Fondo gris claro
                }
                binding.gridLayout.addView(textView)
            }
        }
    }

    private fun marcarFilasYColumnas(matriz: Array<Array<Int>>, filasCubiertas: Set<Int>, columnasCubiertas: Set<Int>) {
        val totalElementos = matriz.size * matriz[0].size
        for (i in 0 until totalElementos) {
            val fila = i / matriz[0].size
            val columna = i % matriz[0].size

            val textView = binding.gridLayout.getChildAt(i) as TextView

            // Cambiar color si está cubierto
            if (fila in filasCubiertas || columna in columnasCubiertas) {
                textView.setBackgroundColor(ContextCompat.getColor(this, R.color.purple)) // Color de marcado
                textView.setTextColor(ContextCompat.getColor(this, android.R.color.white)) // Texto blanco
            } else {
                textView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent)) // Fondo transparente
                textView.setTextColor(ContextCompat.getColor(this, android.R.color.black)) // Texto negro
            }
        }
    }

}

