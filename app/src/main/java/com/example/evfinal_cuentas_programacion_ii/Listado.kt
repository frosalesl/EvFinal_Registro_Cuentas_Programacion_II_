package com.example.evfinal_cuentas_programacion_ii

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.evfinal_cuentas_programacion_ii.Medicion.MedicionViewModel
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoScreen(navController: NavController, viewModel: MedicionViewModel) {
    // Observamos la lista real. Al inicio estará vacía.
    val registros by viewModel.listaMediciones.observeAsState(emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("formulario") },
                containerColor = Color(0xFFEADDFF), // Lila suave de la referencia
                contentColor = Color.Black
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (registros.isEmpty()) {
                // Mensaje cuando no hay datos (Punto 2 de tu solicitud)
                Text(
                    text = "No existen mediciones registradas",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(registros) { registro ->
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp, horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // CARGA DESDE DRAWABLE: Asegúrate que existan ic_agua, ic_luz, ic_gas
                                val iconRes = when (registro.tipo) {
                                    "Agua" -> R.drawable.ic_agua
                                    "Luz" -> R.drawable.ic_luz
                                    else -> R.drawable.ic_gas
                                }

                                Icon(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Unspecified // Mantiene colores originales si es PNG
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = registro.tipo.uppercase(),
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Text(
                                    text = String.format("%.3f", registro.valor),
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End,
                                    fontWeight = FontWeight.Medium
                                )

                                Text(
                                    text = registro.fecha.split(" ")[0],
                                    modifier = Modifier.weight(1.3f),
                                    textAlign = TextAlign.End,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                        }
                    }
                }
            }
        }
    }
}

// --- VISTA PREVIA CARGANDO DESDE DRAWABLE ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListadoPreviewConDrawable() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Simulamos una fila para ver si carga el icono del drawable
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Intento de carga directa de drawable en Preview
            Icon(
                painter = painterResource(id = R.drawable.ic_agua),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text("AGUA (PREVIEW)", modifier = Modifier.weight(1f))
            Text("1.900", modifier = Modifier.weight(1f), textAlign = TextAlign.End)
            Text("2024-01-13", modifier = Modifier.weight(1.3f), textAlign = TextAlign.End, color = Color.Gray)
        }
        HorizontalDivider(thickness = 0.5.dp)

        // Espacio para mostrar el mensaje de "vacío" también en la misma preview
        Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
            Text("No existen mediciones registradas", color = Color.Black)
        }
    }
}