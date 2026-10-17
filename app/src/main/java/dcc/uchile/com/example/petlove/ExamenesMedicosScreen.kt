
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
    val tipo: String,
    val fecha: String,
    val centro: String
)

@Composable
fun ExamenesMedicosScreen(
    examenes: List<ExamenMedico>,
    onBackClick: () -> Unit = {},
    onAgregarClick: () -> Unit = {},
    onEliminarClick: (Long) -> Unit = {}
) {
    var busqueda by remember { mutableStateOf("") }
    var mostrarFiltros by remember { mutableStateOf(false) }
    var filtro by remember { mutableStateOf("Todos") }
    var menuId by remember { mutableStateOf<Long?>(null) }
    var examenEliminar by remember { mutableStateOf<ExamenMedico?>(null) }
    var examenDetalle by remember { mutableStateOf<ExamenMedico?>(null) }

    val filtrados = examenes.filter { examen ->
        val coincideBusqueda =
            examen.nombre.contains(busqueda, ignoreCase = true) ||
                    examen.tipo.contains(busqueda, ignoreCase = true) ||
                    examen.centro.contains(busqueda, ignoreCase = true) ||
                    examen.fecha.contains(busqueda, ignoreCase = true)

        val coincideFiltro = when (filtro) {
            "Tipo de examen" ->
                examen.tipo.contains(busqueda, ignoreCase = true) ||
                        busqueda.isBlank()

            "Clínica" ->
                examen.centro.contains(busqueda, ignoreCase = true) ||
                        busqueda.isBlank()

            else -> true
        }

        coincideBusqueda && coincideFiltro
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
                        imageVector = Icons.Default.Close,
                        contentDescription = "Volver"
                    )
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Exámenes médicos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                IconButton(
                    onClick = onAgregarClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
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
                    placeholder = {
                        Text("Buscar examen")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                Box {
                    Surface(
                        modifier = Modifier.size(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.outlineVariant
                        ),
                        color = Color.White,
                        onClick = {
                            mostrarFiltros = true
                        }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Filtros"
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = mostrarFiltros,
                        onDismissRequest = {
                            mostrarFiltros = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Todos") },
                            onClick = {
                                filtro = "Todos"
                                mostrarFiltros = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Tipo de examen") },
                            onClick = {
                                filtro = "Tipo de examen"
                                mostrarFiltros = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Clínica") },
                            onClick = {
                                filtro = "Clínica"
                                mostrarFiltros = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            if (filtrados.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (examenes.isEmpty()) {
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
                    items(
                        items = filtrados,
                        key = { it.id }
                    ) { examen ->
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
                                modifier = Modifier.padding(14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = examen.nombre,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }

                                    Box {
                                        IconButton(
                                            onClick = {
                                                menuId = examen.id
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.MoreVert,
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

                                Spacer(Modifier.height(8.dp))

                                Text(
                                    text = "Tipo de examen: ${examen.tipo}",
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Spacer(Modifier.height(4.dp))

                                Text(
                                    text = "Fecha: ${examen.fecha}",
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Spacer(Modifier.height(4.dp))

                                Text(
                                    text = "Nombre de la clínica: ${examen.centro}",
                                    style = MaterialTheme.typography.bodyMedium
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
                    Text("Tipo de examen: ${examen.tipo}")
                    Text("Fecha: ${examen.fecha}")
                    Text("Nombre de la clínica: ${examen.centro}")
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
                        onEliminarClick(examen.id)
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
