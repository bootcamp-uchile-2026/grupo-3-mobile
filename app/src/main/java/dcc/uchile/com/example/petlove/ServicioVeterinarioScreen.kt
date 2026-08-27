package dcc.uchile.com.example.petlove

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun ServicioVeterinarioScreen() {

    var busqueda by remember {
        mutableStateOf("")
    }

    var mascotaSeleccionada by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // --------------------------------------------------
        // X PARA CERRAR
        // --------------------------------------------------

        IconButton(
            onClick = {}
        ) {

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar"
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // --------------------------------------------------
        // TÍTULO
        // --------------------------------------------------

        Text(
            text = "Servicio veterinario",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // --------------------------------------------------
        // BARRA DE BÚSQUEDA
        // --------------------------------------------------

        OutlinedTextField(
            value = busqueda,
            onValueChange = {
                busqueda = it
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Buscar mascota")
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

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // --------------------------------------------------
        // TÍTULO SELECCIONAR MASCOTA
        // --------------------------------------------------

        Text(
            text = "Seleccionar la mascota",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // --------------------------------------------------
        // ÁREA DE LOS TRES HALF-BANNERS
        // --------------------------------------------------

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // ----------------------------------------------
            // PERRO + GATO
            // ----------------------------------------------

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // PERRO
                MascotaHalfBanner(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),

                    titulo = "Perro",

                    seleccionado =
                        mascotaSeleccionada == "Perro",

                    onClick = {
                        mascotaSeleccionada = "Perro"
                    }
                )

                // GATO
                MascotaHalfBanner(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),

                    titulo = "Gato",

                    seleccionado =
                        mascotaSeleccionada == "Gato",

                    onClick = {
                        mascotaSeleccionada = "Gato"
                    }
                )
            }

            // ----------------------------------------------
            // AGREGAR MASCOTA
            // ----------------------------------------------

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

                    seleccionado =
                        mascotaSeleccionada == "Agregar",

                    onClick = {
                        mascotaSeleccionada = "Agregar"
                    }
                )
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // --------------------------------------------------
        // BOTÓN SIGUIENTE
        // --------------------------------------------------

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = mascotaSeleccionada != null
        ) {

            Text(
                text = "Siguiente"
            )
        }
    }
}


// --------------------------------------------------
// HALF BANNER DE MASCOTA
// --------------------------------------------------

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

        // --------------------------------------------------
        // INDICADOR DE SELECCIÓN
        // --------------------------------------------------

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

            // --------------------------------------------------
            // SIGNO +
            // --------------------------------------------------

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar mascota",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(28.dp)
            )
        }

        // --------------------------------------------------
        // NOMBRE DEL BANNER
        // --------------------------------------------------

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