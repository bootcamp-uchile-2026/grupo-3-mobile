
package dcc.uchile.com.example.petlove

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun EditarHistorialMedicoScreen(
    registro: RegistroHistorial,
    onBackClick: () -> Unit,
    onGuardarClick: (RegistroHistorial) -> Unit
) {
    var clinica by remember(registro.id) { mutableStateOf(registro.clinica) }
    var veterinario by remember(registro.id) { mutableStateOf(registro.veterinario) }
    var fecha by remember(registro.id) { mutableStateOf(registro.fecha) }
    var descripcion by remember(registro.id) { mutableStateOf(registro.descripcion) }
    var mostrarError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendario = remember { Calendar.getInstance() }
    val naranja = MaterialTheme.colorScheme.primary

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .imePadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 4.dp,
                    bottom = 16.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Volver",
                        modifier = Modifier.size(22.dp),
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Editar historial médico",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Clínica", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))

            CampoEditarHistorial(
                valor = clinica,
                alCambiar = {
                    clinica = it
                    mostrarError = false
                },
                placeholder = "Nombre de la clínica"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Veterinario", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))

            CampoEditarHistorial(
                valor = veterinario,
                alCambiar = {
                    veterinario = it
                    mostrarError = false
                },
                placeholder = "Nombre del veterinario"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Fecha", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        val partes = fecha.split("/")
                        val dia = partes.getOrNull(0)?.toIntOrNull()
                            ?: calendario.get(Calendar.DAY_OF_MONTH)
                        val mes = (partes.getOrNull(1)?.toIntOrNull()?.minus(1))
                            ?: calendario.get(Calendar.MONTH)
                        val anio = partes.getOrNull(2)?.toIntOrNull()
                            ?: calendario.get(Calendar.YEAR)

                        DatePickerDialog(
                            context,
                            { _, year, month, day ->
                                fecha = "%02d/%02d/%04d".format(day, month + 1, year)
                                mostrarError = false
                            },
                            anio,
                            mes,
                            dia
                        ).show()
                    }
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = fecha.ifBlank { "Seleccionar fecha" },
                    modifier = Modifier.weight(1f),
                    fontSize = 14.sp,
                    color = if (fecha.isBlank()) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        Color.Black
                    }
                )

                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Seleccionar fecha",
                    modifier = Modifier.size(21.dp),
                    tint = naranja
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Descripción", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
            ) {
                BasicTextField(
                    value = descripcion,
                    onValueChange = {
                        descripcion = it
                        mostrarError = false
                    },
                    modifier = Modifier.fillMaxSize(),
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    decorationBox = { innerTextField ->
                        if (descripcion.isEmpty()) {
                            Text(
                                text = "Describe la consulta, diagnóstico o tratamiento...",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        innerTextField()
                    }
                )
            }

            if (mostrarError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Completa todos los campos para continuar.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onBackClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancelar", color = Color.Black, fontSize = 14.sp, maxLines = 1)
                }

                Button(
                    onClick = {
                        if (
                            clinica.isNotBlank() &&
                            veterinario.isNotBlank() &&
                            fecha.isNotBlank() &&
                            descripcion.isNotBlank()
                        ) {
                            onGuardarClick(
                                registro.copy(
                                    clinica = clinica.trim(),
                                    veterinario = veterinario.trim(),
                                    fecha = fecha,
                                    descripcion = descripcion.trim()
                                )
                            )
                        } else {
                            mostrarError = true
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = naranja,
                        contentColor = Color.White
                    )
                ) {
                    Text("Guardar cambios", color = Color.White, fontSize = 12.sp, maxLines = 1)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun CampoEditarHistorial(
    valor: String,
    alCambiar: (String) -> Unit,
    placeholder: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = valor,
            onValueChange = alCambiar,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            ),
            decorationBox = { innerTextField ->
                if (valor.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                innerTextField()
            }
        )
    }
}
