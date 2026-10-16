package dcc.uchile.com.example.petlove

import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotificacionesScreen() {

    var searchText by remember {
        mutableStateOf("")
    }

    var recompraEmail by remember { mutableStateOf(true) }
    var recompraCelular by remember { mutableStateOf(true) }

    var promocionesEmail by remember { mutableStateOf(true) }
    var promocionesCelular by remember { mutableStateOf(true) }

    var pedidoEmail by remember { mutableStateOf(true) }
    var pedidoCelular by remember { mutableStateOf(true) }

    var horaVeterinariaEmail by remember { mutableStateOf(true) }
    var horaVeterinariaCelular by remember { mutableStateOf(true) }

    var vacunasEmail by remember { mutableStateOf(true) }
    var vacunasCelular by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 8.dp
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            verticalAlignment = Alignment.CenterVertically
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

            Spacer(modifier = Modifier.width(4.dp))

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

                    Spacer(modifier = Modifier.width(8.dp))

                    BasicTextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        decorationBox = { innerTextField ->

                            if (searchText.isEmpty()) {
                                Text(
                                    text = "Buscar productos...",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            innerTextField()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            IconButton(
                onClick = {
                    println("Abrir carrito")
                },
                modifier = Modifier.size(44.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Carrito",
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Notificaciones",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Notificaciones de tienda",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Recordatorio de recompra:",
            style = MaterialTheme.typography.titleMedium
        )

        NotificationRow(
            text = "E-Mail.",
            checked = recompraEmail,
            onCheckedChange = {
                recompraEmail = it
            }
        )

        NotificationRow(
            text = "Celular",
            checked = recompraCelular,
            onCheckedChange = {
                recompraCelular = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Promociones personalizadas:",
            style = MaterialTheme.typography.titleMedium
        )

        NotificationRow(
            text = "E-Mail.",
            checked = promocionesEmail,
            onCheckedChange = {
                promocionesEmail = it
            }
        )

        NotificationRow(
            text = "Celular",
            checked = promocionesCelular,
            onCheckedChange = {
                promocionesCelular = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Pedido en camino:",
            style = MaterialTheme.typography.titleMedium
        )

        NotificationRow(
            text = "E-Mail.",
            checked = pedidoEmail,
            onCheckedChange = {
                pedidoEmail = it
            }
        )

        NotificationRow(
            text = "Celular",
            checked = pedidoCelular,
            onCheckedChange = {
                pedidoCelular = it
            }
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Recordatorios de tu mascota",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Recordatorio de hora veterinaria:",
            style = MaterialTheme.typography.titleMedium
        )

        NotificationRow(
            text = "E-Mail.",
            checked = horaVeterinariaEmail,
            onCheckedChange = {
                horaVeterinariaEmail = it
            }
        )

        NotificationRow(
            text = "Celular",
            checked = horaVeterinariaCelular,
            onCheckedChange = {
                horaVeterinariaCelular = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Recordatorio de vacunas:",
            style = MaterialTheme.typography.titleMedium
        )

        NotificationRow(
            text = "E-Mail.",
            checked = vacunasEmail,
            onCheckedChange = {
                vacunasEmail = it
            }
        )

        NotificationRow(
            text = "Celular",
            checked = vacunasCelular,
            onCheckedChange = {
                vacunasCelular = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun NotificationRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}