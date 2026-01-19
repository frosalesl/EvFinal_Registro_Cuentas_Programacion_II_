package com.example.evfinal_cuentas_programacion_ii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.evfinal_cuentas_programacion_ii.Medicion.AppDatabase
import com.example.evfinal_cuentas_programacion_ii.Medicion.MedicionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de Base de Datos Room
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "medidores_db"
        ).build()

        // Inicialización del ViewModel con el DAO correspondiente
        val viewModel = MedicionViewModel(db.medicionDao())

        setContent {
            val navController = rememberNavController()

            // Control de navegación entre pantallas
            NavHost(navController = navController, startDestination = "listado") {
                composable("listado") {
                    ListadoScreen(navController, viewModel)
                }
                composable("formulario") {
                    FormularioScreen(navController, viewModel)
                }
            }
        }
    }
}