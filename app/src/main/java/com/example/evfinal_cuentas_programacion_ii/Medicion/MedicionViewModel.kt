package com.example.evfinal_cuentas_programacion_ii.Medicion

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Importamos explícitamente la función de extensión para Flow
import androidx.lifecycle.asLiveData

class MedicionViewModel(private val dao: MedicionDao) : ViewModel() {

    // 1. Obtenemos los datos de Room y los convertimos a LiveData
    // Esto permite que la UI se actualice automáticamente (Punto 12 de la pauta)
    val listaMediciones: LiveData<List<Medicion>> = dao.getAll().asLiveData()

    // 2. Función para guardar que usa Corrutinas (Punto 11 de la pauta)
    // Usamos Dispatchers.IO para que la escritura en disco no trabe la pantalla
    fun guardarMedicion(tipo: String, valor: String, fecha: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val valorNum = valor.toDoubleOrNull() ?: 0.0
            val nueva = Medicion(
                tipo = tipo,
                valor = valorNum,
                fecha = fecha
            )
            dao.insert(nueva)
        }
    }
}