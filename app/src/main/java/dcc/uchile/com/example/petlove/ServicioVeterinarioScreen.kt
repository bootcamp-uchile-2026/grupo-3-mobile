
package dcc.uchile.com.example.petlove

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun ServicioVeterinarioScreen() {

    var busqueda by remember {
        mutableStateOf("")
    }

    var mascotaSeleccionada by remember {
        mutableStateOf<String?>(null)
    }

    var showFilterSheet by remember {
        mutableStateOf(false)
    }

    var selectedFilters by remember {
        mutableStateOf(setOf<String>())
    }

    val filterColor = Color(0xFFDD5A21)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 8.dp
            )
    ) {

        IconButton(
            onClick = {},
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar",
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = "Servicio veterinario",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    BasicTextField(
                        value = busqueda,
                        onValueChange = {
                            busqueda = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        decorationBox = { innerTextField ->

                            if (busqueda.isEmpty()) {
                                Text(
                                    text = "Buscar mascota",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            innerTextField()
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(6.dp)
            )

            IconButton(
                onClick = {
                    showFilterSheet = true
                },
                modifier = Modifier
                    .size(44.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = "Filtros",
                    modifier = Modifier.size(19.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Seleccionar la mascota",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                MascotaHalfBanner(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    titulo = "Perro",
                    seleccionado = mascotaSeleccionada == "Perro",
                    onClick = {
                        mascotaSeleccionada = "Perro"
                    }
                )

                MascotaHalfBanner(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    titulo = "Gato",
                    seleccionado = mascotaSeleccionada == "Gato",
                    onClick = {
                        mascotaSeleccionada = "Gato"
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.Start
            ) {

                MascotaHalfBanner(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(),
                    titulo = "Agregar mascota",
                    mostrarAgregar = true,
                    seleccionado = mascotaSeleccionada == "Agregar",
                    onClick = {
                        mascotaSeleccionada = "Agregar"
                    }
                )
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = {
                    mascotaSeleccionada = "Agregar"
                },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(8.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar mascota",
                    modifier = Modifier.size(20.dp)
                )

                Spacer(
                    modifier = Modifier.width(6.dp)
                )

                Text(
                    text = "Agregar mascota"
                )
            }

            Button(
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                enabled = mascotaSeleccionada != null,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.40f
                    ),
                    disabledContentColor = Color.White
                )
            ) {

                Text(
                    text = "Siguiente"
                )
            }
        }
    }

    if (showFilterSheet) {

        val sheetState =
            rememberModalBottomSheetState(
                skipPartiallyExpanded = false
            )

        ModalBottomSheet(
            onDismissRequest = {
                showFilterSheet = false
            },
            sheetState = sheetState,
            containerColor = Color.White,
            scrimColor = Color.Black.copy(
                alpha = 0.10f
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp
                        )
                    )
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 8.dp,
                        bottom = 28.dp
                    )
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {

                    IconButton(
                        onClick = {
                            showFilterSheet = false
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar filtros"
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Filtro",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium
                    )

                    TextButton(
                        onClick = {
                            selectedFilters = emptySet()
                        }
                    ) {

                        Text(
                            text = "Limpiar",
                            color = Color.Red
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = "Tipo",
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(
                            rememberScrollState()
                        ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    FilterChip(
                        selected = "Perro" in selectedFilters,
                        onClick = {
                            selectedFilters = toggleFilter(
                                selectedFilters,
                                "Perro"
                            )
                        },
                        label = {
                            Text(
                                text = "Perro",
                                maxLines = 1,
                                softWrap = false,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        },
                        modifier = Modifier.width(82.dp),
                        colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                            containerColor = filterColor,
                            labelColor = Color.White,
                            selectedContainerColor = filterColor,
                            selectedLabelColor = Color.White
                        )
                    )

                    FilterChip(
                        selected = "Gato" in selectedFilters,
                        onClick = {
                            selectedFilters = toggleFilter(
                                selectedFilters,
                                "Gato"
                            )
                        },
                        label = {
                            Text(
                                text = "Gato",
                                maxLines = 1,
                                softWrap = false,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        },
                        modifier = Modifier.width(82.dp),
                        colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                            containerColor = filterColor,
                            labelColor = Color.White,
                            selectedContainerColor = filterColor,
                            selectedLabelColor = Color.White
                        )
                    )
                }

                Spacer(
                    modifier = Modifier.height(32.dp)
                )

                Button(
                    onClick = {
                        println(
                            "Filtros seleccionados: $selectedFilters"
                        )
                        showFilterSheet = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )
                ) {

                    Text(
                        text = "Aplicar"
                    )
                }
            }
        }
    }
}

@Composable
fun MascotaHalfBanner(
    modifier: Modifier,
    titulo: String,
    seleccionado: Boolean,
    mostrarAgregar: Boolean = false,
    onClick: () -> Unit
) {

    val backgroundColor =
        if (seleccionado) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        }

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                onClick()
            }
            .padding(12.dp)
    ) {

        if (!mostrarAgregar) {

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(24.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(5.dp)
            ) {

                if (seleccionado) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(50)
                            )
                    )
                }
            }

        } else {

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar mascota",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(28.dp)
            )
        }

        Text(
            text = titulo,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

