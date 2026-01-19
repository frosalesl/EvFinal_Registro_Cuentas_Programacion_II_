package com.example.evfinal_cuentas_programacion_ii.Medicion

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicionViewModel(private val dao: MedicionDao) : ViewModel() {

    // Aquí se actualiza automáticamente al insertar o borrar.
    val listaMediciones: LiveData<List<Medicion>> = dao.getAll().asLiveData()

    // Esta es la función para insertar registros
    fun guardarMedicion(tipo: String, valor: String, fecha: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Se maneja el valor como Double para la base de datos
            val valorNumerico = valor.toDoubleOrNull() ?: 0.0
            val nuevaMedicion = Medicion(
                tipo = tipo,
                valor = valorNumerico,
                fecha = fecha
            )
            dao.insert(nuevaMedicion)
        }
    }

    // Esta es la función para borrar registros (conectada al basurero azul)
    fun borrarMedicion(medicion: Medicion) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(medicion)
        }
    }
}