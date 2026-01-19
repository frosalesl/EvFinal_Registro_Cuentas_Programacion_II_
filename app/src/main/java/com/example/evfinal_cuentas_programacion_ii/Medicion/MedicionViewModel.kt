package com.example.evfinal_cuentas_programacion_ii.Medicion

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicionViewModel(private val dao: MedicionDao) : ViewModel() {

    // 1. Observador de la lista: Se actualiza automáticamente al insertar o borrar
    // Requiere la dependencia: androidx.lifecycle:lifecycle-livedata-ktx
    val listaMediciones: LiveData<List<Medicion>> = dao.getAll().asLiveData()

    // 2. Función para insertar registros
    fun guardarMedicion(tipo: String, valor: String, fecha: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Manejamos el valor como Double para la base de datos
            val valorNumerico = valor.toDoubleOrNull() ?: 0.0
            val nuevaMedicion = Medicion(
                tipo = tipo,
                valor = valorNumerico,
                fecha = fecha
            )
            dao.insert(nuevaMedicion)
        }
    }

    // 3. Función para borrar registros (conectada al basurero azul)
    fun borrarMedicion(medicion: Medicion) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(medicion)
        }
    }
}