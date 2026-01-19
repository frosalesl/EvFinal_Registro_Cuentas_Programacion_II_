package com.example.evfinal_cuentas_programacion_ii

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.evfinal_cuentas_programacion_ii.Medicion.MedicionViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FormularioScreen(navController: NavController, viewModel: MedicionViewModel) {
    // Definición de los estados reactivos con el fin de capturar la entrada del usuario
    var valor by remember { mutableStateOf("") }

    // Estado para almacenar el ID del recurso de texto seleccionado (Manejo de bilingüismo)
    var tipoSeleccionadoRes by remember { mutableIntStateOf(R.string.tipo_agua) }

    // Lista de recursos de strings para las opciones del RadioButton
    val opciones = listOf(
        R.string.tipo_agua,
        R.string.tipo_luz,
        R.string.tipo_gas
    )

    val context = LocalContext.current
    // Obtención y formateo de la fecha actual del sistema para lograr el registro
    val fechaHoy = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        Text(
            text = stringResource(id = R.string.titulo_registro),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        // Contenedor del formulario
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE7E0EC), RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(stringResource(id = R.string.label_valor), style = MaterialTheme.typography.labelSmall, color = Color.Gray)

            // Campo de texto con validación que solo permite caracteres numéricos
            TextField(
                value = valor,
                onValueChange = { if (it.all { char -> char.isDigit() }) valor = it },
                placeholder = { Text(stringResource(id = R.string.placeholder_medidor)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                singleLine = true
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            // Visualización de la fecha
            Text(stringResource(id = R.string.label_fecha), style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(text = fechaHoy, modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sección de selección de tipo de medidor
        Text(stringResource(id = R.string.label_tipo), modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.titleMedium)

        // Iteración sobre la lista de opciones para generar dinámicamente los RadioButtons
        opciones.forEach { opcionResId ->
            Row(
                Modifier.fillMaxWidth().selectable(
                    selected = (opcionResId == tipoSeleccionadoRes),
                    onClick = { tipoSeleccionadoRes = opcionResId }
                ).padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (opcionResId == tipoSeleccionadoRes),
                    onClick = { tipoSeleccionadoRes = opcionResId },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF6750A4))
                )
                // Uso de stringResource de soporte multiidioma en la interfaz
                Text(
                    text = stringResource(id = opcionResId),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botón de acción para persistir los datos (validación de longitud mínima)
        Button(
            modifier = Modifier.fillMaxWidth(0.7f).height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)),
            enabled = valor.length >= 3,
            onClick = {
                // Normalización del tipo de dato para almacenamiento consistente en la Base de Datos
                val tipoParaDB = when (tipoSeleccionadoRes) {
                    R.string.tipo_agua -> "Agua"
                    R.string.tipo_luz -> "Luz"
                    else -> "Gas"
                }

                // Llamada al ViewModel (corrutinas)
                viewModel.guardarMedicion(tipoParaDB, valor, fechaHoy)
                Toast.makeText(context, context.getString(R.string.msg_exito), Toast.LENGTH_SHORT).show()
                navController.popBackStack() // Retorno a la pantalla anterior tras guardar
            }
        ) {
            Text(stringResource(id = R.string.btn_save), color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón para cancelar la operación y regresar al listado
        TextButton(onClick = { navController.popBackStack() }) {
            Text(stringResource(id = R.string.btn_cancel), color = Color(0xFF6750A4))
        }
    }
}