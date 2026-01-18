package com.example.evfinal_cuentas_programacion_ii

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.evfinal_cuentas_programacion_ii.Medicion.MedicionViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FormularioScreen(navController: NavController, viewModel: MedicionViewModel) {
    var valor by remember { mutableStateOf("") }
    var tipoSeleccionado by remember { mutableStateOf("Agua") }
    val opciones = listOf("Agua", "Luz", "Gas")

    // Fecha automática para mostrar en el formulario
    val fechaHoy = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título centralizado
        Text(
            text = "Registro Medidor",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        // Contenedor de campos con fondo gris suave (como tu imagen)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE7E0EC), RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text("Medidor", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            TextField(
                value = valor,
                onValueChange = { valor = it },
                placeholder = { Text("Ej: 10500") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Black
                ),
                singleLine = true
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            Text("Fecha", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(
                text = fechaHoy,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sección RadioButtons
        Text(
            text = "Medidor de:",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium
        )

        opciones.forEach { opcion ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (opcion == tipoSeleccionado),
                        onClick = { tipoSeleccionado = opcion }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (opcion == tipoSeleccionado),
                    onClick = { tipoSeleccionado = opcion },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF6750A4))
                )
                Text(text = opcion, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Empuja el botón hacia abajo

        // Botón Lila estilizado
        Button(
            modifier = Modifier
                .fillMaxWidth(0.7f) // No ocupa todo el ancho, igual que la foto
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)),
            onClick = {
                if (valor.isNotBlank()) {
                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    viewModel.guardarMedicion(tipoSeleccionado, valor, sdf.format(Date()))
                    navController.popBackStack()
                }
            }
        ) {
            Text("Registrar medición", color = Color.White)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FormularioPreview() {
    // Aquí verás el diseño idéntico al ejemplo de Registro Medidor
    Surface(color = Color.White) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Registro Medidor", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))

            // Caja gris de datos
            Column(modifier = Modifier.fillMaxWidth().background(Color(0xFFE7E0EC), RoundedCornerShape(4.dp)).padding(12.dp)) {
                Text("Medidor", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                Text("10500", style = MaterialTheme.typography.bodyLarge)
                HorizontalDivider(Modifier.padding(vertical = 4.dp))
                Text("Fecha", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                Text("2024-01-18", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Medidor de:", modifier = Modifier.fillMaxWidth())

            listOf("Agua", "Luz", "Gas").forEachIndexed { i, t ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    RadioButton(selected = i == 0, onClick = {})
                    Text(t)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4))) {
                Text("Registrar medición")
            }
        }
    }
}