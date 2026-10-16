
package dcc.uchile.com.example.petlove

import android.content.ContentValues
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.ByteArrayOutputStream

@Composable
fun DetalleHistorialMedicoScreen(
    registro: RegistroHistorial,
    onBackClick: () -> Unit,
    onEditarClick: () -> Unit,
    onEliminarClick: () -> Unit
) {
    val context = LocalContext.current
    var pdfPendiente by remember { mutableStateOf<ByteArray?>(null) }
    var mensaje by remember { mutableStateOf<String?>(null) }
    var confirmarEliminacion by remember { mutableStateOf(false) }

    val selectorPDF = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/pdf")
    ) { uri ->
        val contenido = pdfPendiente
        if (uri != null && contenido != null) {
            try {
                context.contentResolver.openOutputStream(uri)?.use {
                    it.write(contenido)
                } ?: throw IllegalStateException("No se pudo abrir el archivo")

                mensaje = "PDF exportado correctamente."
            } catch (e: Exception) {
                mensaje = "No se pudo guardar el PDF."
            }
        }
        pdfPendiente = null
    }

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.Black
                    )
                }

                Text(
                    text = "Historial médico",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Información del registro",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline
                ),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Registro médico",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )

                    CampoDetalle("Clínica", registro.clinica)
                    CampoDetalle("Veterinario", registro.veterinario)
                    CampoDetalle("Fecha", registro.fecha)
                    CampoDetalle("Descripción", registro.descripcion)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    pdfPendiente = generarPDFHistorial(registro)
                    selectorPDF.launch("Historial_medico_${registro.id}.pdf")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.PictureAsPdf,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text("Exportar en PDF")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onEditarClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Editar", fontSize = 14.sp)
                }

                Button(
                    onClick = { confirmarEliminacion = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Eliminar", fontSize = 14.sp)
                }
            }
        }
    }

    if (confirmarEliminacion) {
        AlertDialog(
            onDismissRequest = { confirmarEliminacion = false },
            title = { Text("Eliminar registro") },
            text = {
                Text("¿Quieres eliminar este historial médico?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        confirmarEliminacion = false
                        onEliminarClick()
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { confirmarEliminacion = false }
                ) {
                    Text("Cancelar")
                }
            },
            containerColor = Color.White
        )
    }

    if (mensaje != null) {
        AlertDialog(
            onDismissRequest = { mensaje = null },
            title = { Text("Exportación PDF") },
            text = { Text(mensaje.orEmpty()) },
            confirmButton = {
                TextButton(onClick = { mensaje = null }) {
                    Text("Aceptar")
                }
            },
            containerColor = Color.White
        )
    }
}

@Composable
private fun CampoDetalle(
    titulo: String,
    valor: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = valor,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

private fun generarPDFHistorial(
    registro: RegistroHistorial
): ByteArray {
    val documento = PdfDocument()
    val salida = ByteArrayOutputStream()

    val ancho = 595
    val alto = 842
    val margen = 48f
    var numeroPagina = 1
    var pagina = documento.startPage(
        PdfDocument.PageInfo.Builder(ancho, alto, numeroPagina).create()
    )
    var canvas = pagina.canvas
    var y = 65f

    val titulo = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 23f
        isFakeBoldText = true
    }

    val subtitulo = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 15f
        isFakeBoldText = true
    }

    val texto = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 13f
    }

    fun nuevaPagina() {
        documento.finishPage(pagina)
        numeroPagina++
        pagina = documento.startPage(
            PdfDocument.PageInfo.Builder(ancho, alto, numeroPagina).create()
        )
        canvas = pagina.canvas
        y = 65f
    }

    fun escribirLinea(linea: String, paint: Paint) {
        if (y > alto - 55f) nuevaPagina()
        canvas.drawText(linea, margen, y, paint)
        y += paint.textSize + 10f
    }

    fun escribirCampo(nombre: String, valor: String) {
        escribirLinea(nombre, subtitulo)

        val palabras = valor.split(Regex("\\s+"))
        var linea = ""

        for (palabra in palabras) {
            val candidata = if (linea.isEmpty()) palabra else "$linea $palabra"

            if (texto.measureText(candidata) > ancho - margen * 2) {
                escribirLinea(linea, texto)
                linea = palabra
            } else {
                linea = candidata
            }
        }

        if (linea.isNotEmpty()) escribirLinea(linea, texto)
        y += 12f
    }

    escribirLinea("PetLove", titulo)
    y += 12f
    escribirLinea("Historial médico", subtitulo)
    y += 20f
    escribirCampo("Clínica", registro.clinica)
    escribirCampo("Veterinario", registro.veterinario)
    escribirCampo("Fecha", registro.fecha)
    escribirCampo("Descripción", registro.descripcion)

    documento.finishPage(pagina)
    documento.writeTo(salida)
    documento.close()

    return salida.toByteArray()
}
