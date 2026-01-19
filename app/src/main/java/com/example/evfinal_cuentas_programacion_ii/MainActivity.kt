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

// Entrada principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de la Base de Datos ROOM
        // Se utiliza el patrón Builder para construir la instancia de la base de datos SQLite
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "medidores_db"
        ).build()

        // Instanciación del ViewModel
        // Se le inyecta el DAO para permitir la comunicación entre la UI y la capa de datos
        val viewModel = MedicionViewModel(db.medicionDao())

        setContent {
            // Inicialización del controlador de navegación de Jetpack Compose
            val navController = rememberNavController()

            // Configuración del Grafo de Navegación (NavHost)
            // Define las rutas y vincula los componibles con el ViewModel
            NavHost(navController = navController, startDestination = "listado") {

                // Definición de la ruta para la pantalla de historial
                composable("listado") {
                    ListadoScreen(navController, viewModel)
                }

                // Definición de la ruta para la pantalla de ingreso de datos
                composable("formulario") {
                    FormularioScreen(navController, viewModel)
                }
            }
        }
    }
}