
package dcc.uchile.com.example.petlove

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class ExamenMedico(
    val id: Long = System.nanoTime(),
    val nombre: String,
    val fecha: String,
    val centro: String,
    val descripcion: String
)

@Composable
fun ExamenesMedicosScreen(
    onBackClick: () -> Unit = {},
    onAgregarClick: () -> Unit = {}
) {
    val examenes = remember {
        mutableStateListOf<ExamenMedico>()
    }

    var busqueda by remember { mutableStateOf("") }
    var menuId by remember { mutableStateOf<Long?>(null) }
    var examenEliminar by remember { mutableStateOf<ExamenMedico?>(null) }
    var examenDetalle by remember { mutableStateOf<ExamenMedico?>(null) }

    val filtrados = examenes.filter {
        it.nombre.contains(busqueda, true) ||
                it.centro.contains(busqueda, true) ||
                it.fecha.contains(busqueda, true) ||
                it.descripcion.contains(busqueda, true)
    }

    Scaffold(
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
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
                        Icons.Default.Close,
                        contentDescription = "Volver"
                    )
                }

                Box(
                    Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Exámenes médicos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                IconButton(
                    onClick = onAgregarClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Agregar examen"
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Buscar examen") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Surface(
                    modifier = Modifier.size(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant
                    ),
                    color = Color.White
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.Tune,
                            contentDescription = "Filtros"
                        )
                    }
                }
            }

            Spacer(Modifier.height(18.dp))

            Text(
                "Exámenes médicos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(12.dp))

            if (filtrados.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        if (busqueda.isBlank()) {
                            "Todavía no hay exámenes registrados."
                        } else {
                            "No se encontraron exámenes."
                        },
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filtrados, key = { it.id }) { examen ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            border = BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outlineVariant
                            ),
                            elevation = CardDefaults.cardElevation(0.dp)
                        ) {
                            Column(
                                Modifier.padding(14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        Modifier.weight(1f)
                                    ) {
                                        Text(
                                            examen.nombre,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.SemiBold
                                        )

                                        Spacer(Modifier.height(5.dp))

                                        Text(examen.descripcion)
                                    }

                                    Box {
                                        IconButton(
                                            onClick = {
                                                menuId = examen.id
                                            }
                                        ) {
                                            Icon(
                                                Icons.Default.MoreVert,
                                                contentDescription = "Opciones"
                                            )
                                        }

                                        DropdownMenu(
                                            expanded = menuId == examen.id,
                                            onDismissRequest = {
                                                menuId = null
                                            }
                                        ) {
                                            DropdownMenuItem(
                                                text = {
                                                    Text("Ver información")
                                                },
                                                onClick = {
                                                    menuId = null
                                                    examenDetalle = examen
                                                }
                                            )

                                            DropdownMenuItem(
                                                text = {
                                                    Text("Eliminar")
                                                },
                                                onClick = {
                                                    menuId = null
                                                    examenEliminar = examen
                                                }
                                            )
                                        }
                                    }
                                }

                                Spacer(Modifier.height(10.dp))

                                Text(
                                    "Centro: ${examen.centro}",
                                    style = MaterialTheme.typography.bodySmall
                                )

                                Spacer(Modifier.height(4.dp))

                                Text(
                                    "Fecha: ${examen.fecha}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    examenDetalle?.let { examen ->
        AlertDialog(
            onDismissRequest = {
                examenDetalle = null
            },
            title = {
                Text("Información del examen")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Examen: ${examen.nombre}")
                    Text("Fecha: ${examen.fecha}")
                    Text("Centro médico: ${examen.centro}")
                    Text("Descripción: ${examen.descripcion}")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        examenDetalle = null
                    }
                ) {
                    Text("Cerrar")
                }
            }
        )
    }

    examenEliminar?.let { examen ->
        AlertDialog(
            onDismissRequest = {
                examenEliminar = null
            },
            title = {
                Text("Eliminar examen")
            },
            text = {
                Text("¿Deseas eliminar este examen médico?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        examenes.removeAll {
                            it.id == examen.id
                        }
                        examenEliminar = null
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        examenEliminar = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
