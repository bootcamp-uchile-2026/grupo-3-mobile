
package dcc.uchile.com.example.petlove

import android.app.DatePickerDialog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun AgregarExamenMedicoScreen(
    onBackClick: () -> Unit = {},
    onAgregarClick: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onCancelarClick: () -> Unit = {}
) {
    var examen by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var clinica by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var mostrarError by remember { mutableStateOf(false) }
    var archivoAdjunto by remember { mutableStateOf("") }

    val context = androidx.compose.ui.platform.LocalContext.current
    val calendario = remember { Calendar.getInstance() }
    val naranja = MaterialTheme.colorScheme.primary

    val selectorArchivo = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            archivoAdjunto = uri.lastPathSegment
                ?.substringAfterLast('/')
                ?: "Archivo adjunto"
        }
    }

    Scaffold(containerColor = Color.White) { paddingValues ->
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

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Agregar examen médico",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(20.dp))

            Text(
                "Examen",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(Modifier.height(8.dp))
            CampoExamenMedico(
                examen,
                { examen = it; mostrarError = false },
                "Nombre del examen"
            )

            Spacer(Modifier.height(16.dp))

            Text(
                "Tipo",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(Modifier.height(8.dp))
            CampoExamenMedico(
                tipo,
                { tipo = it; mostrarError = false },
                "Tipo de examen"
            )

            Spacer(Modifier.height(16.dp))

            Text(
                "Clínica",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(Modifier.height(8.dp))
            CampoExamenMedico(
                clinica,
                { clinica = it; mostrarError = false },
                "Nombre de la clínica"
            )

            Spacer(Modifier.height(16.dp))

            Text(
                "Fecha",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        DatePickerDialog(
                            context,
                            { _, year, month, day ->
                                fecha = "%02d/%02d/%04d".format(
                                    day,
                                    month + 1,
                                    year
                                )
                                mostrarError = false
                            },
                            calendario.get(Calendar.YEAR),
                            calendario.get(Calendar.MONTH),
                            calendario.get(Calendar.DAY_OF_MONTH)
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

            if (mostrarError) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Completa todos los campos para continuar.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(20.dp))

            OutlinedButton(
                onClick = {
                    selectorArchivo.launch(
                        arrayOf("application/pdf", "image/*")
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = null,
                    modifier = Modifier.size(19.dp)
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = if (archivoAdjunto.isBlank()) {
                        "Adjuntar examen"
                    } else {
                        "Cambiar archivo adjunto"
                    },
                    fontSize = 14.sp,
                    maxLines = 1
                )
            }

            if (archivoAdjunto.isNotBlank()) {
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Archivo seleccionado: $archivoAdjunto",
                    style = MaterialTheme.typography.bodySmall,
                    color = naranja
                )
            }

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onCancelarClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outline
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Cancelar",
                        color = Color.Black,
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                }

                Button(
                    onClick = {
                        if (
                            examen.isNotBlank() &&
                            tipo.isNotBlank() &&
                            clinica.isNotBlank() &&
                            fecha.isNotBlank()
                        ) {
                            onAgregarClick(
                                examen.trim(),
                                tipo.trim(),
                                clinica.trim(),
                                fecha
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
                    Text(
                        "Agregar",
                        color = Color.White,
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun CampoExamenMedico(
    valor: String,
    alCambiar: (String) -> Unit,
    placeholder: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(8.dp)
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
