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

        // 1. Database initialization
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "medidores_db"
        ).build()

        // 2. ViewModel initialization
        val viewModel = MedicionViewModel(db.medicionDao())

        setContent {
            // 3. Navigation controller
            val navController = rememberNavController()

            // 4. NavHost linking Listado.kt and Formulario.kt
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