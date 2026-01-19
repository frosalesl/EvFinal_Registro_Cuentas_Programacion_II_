package com.example.evfinal_cuentas_programacion_ii

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.evfinal_cuentas_programacion_ii.Medicion.Medicion
import com.example.evfinal_cuentas_programacion_ii.Medicion.MedicionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoScreen(navController: NavController, viewModel: MedicionViewModel) {
    // Observación reactiva de la base de datos a través de LiveData
    val registros by viewModel.listaMediciones.observeAsState(emptyList())

    // Estados locales para la gestión del diálogo de confirmación de borrado
    var mostrarDialogo by remember { mutableStateOf(false) }
    var registroSeleccionado by remember { mutableStateOf<Medicion?>(null) }

    // ADICIONAL, se agrega opción de eliminar registros.
    // Implementa la validación de usuario antes de proceder con el borrado en ROOM
    if (mostrarDialogo && registroSeleccionado != null) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text(text = stringResource(id = R.string.dialog_titulo)) },
            text = { Text(text = stringResource(id = R.string.dialog_mensaje)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Llamada al ViewModel para eliminar registro mediante corrutinas
                        registroSeleccionado?.let { viewModel.borrarMedicion(it) }
                        mostrarDialogo = false
                    }
                ) {
                    Text(stringResource(id = R.string.btn_si), color = Color.Blue)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text(stringResource(id = R.string.btn_no), color = Color.Gray)
                }
            }
        )
    }

    Scaffold(
        // Botón flotante para navegación hacia el formulario de ingreso
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("formulario") },
                containerColor = Color(0xFFEADDFF),
                contentColor = Color.Black
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            // Validación de estado donde si la lista está vacía, se muestra mensaje
            if (registros.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.sin_datos),
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            } else {
                // Implementación de lista dinámica eficiente
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(registros) { registro ->
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp, horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Lógica de presentación con selección de ícono drawable según el tipo
                                // Traducción para mantener bilingüismo
                                val (iconRes, tipoTraducido) = when (registro.tipo) {
                                    "Agua" -> Pair(R.drawable.ic_agua, stringResource(id = R.string.tipo_agua))
                                    "Luz" -> Pair(R.drawable.ic_luz, stringResource(id = R.string.tipo_luz))
                                    else -> Pair(R.drawable.ic_gas, stringResource(id = R.string.tipo_gas))
                                }

                                Icon(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Unspecified
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                // Renderizado de datos del registro para diseño ordenado
                                Text(
                                    text = tipoTraducido.uppercase(),
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Text(
                                    text = registro.valor.toLong().toString(),
                                    modifier = Modifier.weight(0.8f),
                                    textAlign = TextAlign.End,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = registro.fecha.split(" ")[0],
                                    modifier = Modifier.weight(1.1f),
                                    textAlign = TextAlign.End,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )

                                // ELIMINACIÓN
                                IconButton(
                                    onClick = {
                                        // Se asigna el registro actual al estado para confirmación posterior
                                        registroSeleccionado = registro
                                        mostrarDialogo = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Borrar",
                                        tint = Color.Blue // Basurero en azul extra al requerimiento
                                    )
                                }
                            }
                            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                        }
                    }
                }
            }
        }
    }
}