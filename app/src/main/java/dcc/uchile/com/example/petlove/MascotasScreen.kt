package dcc.uchile.com.example.petlove

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import dcc.uchile.com.example.petlove.data.model.Pet


@Composable
fun MascotasScreen() {

    var searchText by remember {
        mutableStateOf("")
    }

    val pets = listOf(
        Pet(
            id = 1,
            name = "Firulais",
            species = "Perro",
            age = 3
        ),
        Pet(
            id = 2,
            name = "Michi",
            species = "Gato",
            age = 2
        ),
        Pet(
            id = 3,
            name = "Luna",
            species = "Perro",
            age = 5
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // --------------------------------
        // X + BUSCADOR + CAMPANA
        // --------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar"
                )
            }

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                placeholder = {
                    Text("Buscar productos...")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar"
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(20.dp)
            )

            IconButton(
                onClick = {
                    println("Abrir notificaciones")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificaciones"
                )
            }
        }

        Spacer(
            modifier = Modifier.size(16.dp)
        )

        // --------------------------------
        // SALUDO
        // --------------------------------

        Text(
            text = "¡Hola, Joaquín!",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier.size(16.dp)
        )

        // --------------------------------
        // TÍTULO + PERRO Y GATO
        // --------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Mis mascotas",
                style = MaterialTheme.typography.headlineLarge
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "🐶",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(
                    modifier = Modifier.size(8.dp)
                )

                Text(
                    text = "🐱",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Spacer(
            modifier = Modifier.size(16.dp)
        )

        // --------------------------------
        // LISTA DE MASCOTAS
        // --------------------------------

        pets.forEach { pet ->

            MascotaBanner(
                pet = pet,

                onView = {
                    println("Ver mascota: ${pet.id}")
                },

                onEdit = {
                    println("Editar mascota: ${pet.id}")
                },

                onDelete = {
                    println("Eliminar mascota: ${pet.id}")
                },

                onAdd = {
                    println("Agregar mascota")
                }
            )

            Spacer(
                modifier = Modifier.size(16.dp)
            )
        }
    }
}


// ==================================================
// CARD DE MASCOTA
// ==================================================

@Composable
fun MascotaBanner(
    pet: Pet,
    onView: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onAdd: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        // --------------------------------
        // BOTÓN +
        // --------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

            IconButton(
                onClick = onAdd
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar mascota"
                )
            }
        }

        // --------------------------------
        // CARD
        // --------------------------------

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {

                // --------------------------------
                // MITAD IZQUIERDA
                // MASCOTA
                // --------------------------------

                Box(
                    modifier = Modifier
                        .weight(0.8f)
                        .height(260.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = if (pet.species == "Perro") {
                            "🐶"
                        } else {
                            "🐱"
                        },
                        style = MaterialTheme.typography.displayLarge
                    )
                }

                Spacer(
                    modifier = Modifier.size(12.dp)
                )

                // --------------------------------
                // MITAD DERECHA
                // INFORMACIÓN + BOTONES
                // --------------------------------

                Column(
                    modifier = Modifier
                        .weight(1.2f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // NOMBRE
                    Text(
                        text = "Nombre de la mascota",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text(
                        text = pet.name,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(
                        modifier = Modifier.size(8.dp)
                    )

                    // GÉNERO
                    Text(
                        text = "Género",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text(
                        text = if (pet.species == "Perro") {
                            "Macho"
                        } else {
                            "Hembra"
                        }
                    )

                    Spacer(
                        modifier = Modifier.size(16.dp)
                    )

                    // --------------------------------
                    // VER MASCOTA
                    // --------------------------------

                    Button(
                        onClick = onView,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Ver mascota")
                    }

                    Spacer(
                        modifier = Modifier.size(6.dp)
                    )

                    // --------------------------------
                    // EDITAR
                    // --------------------------------

                    Button(
                        onClick = onEdit,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Editar")
                    }

                    Spacer(
                        modifier = Modifier.size(6.dp)
                    )

                    // --------------------------------
                    // ELIMINAR
                    // --------------------------------

                    Button(
                        onClick = onDelete,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}