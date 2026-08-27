package dcc.uchile.com.example.petlove

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Close
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


@Composable
fun CuentaScreen() {

    var searchText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // --------------------------------
        // X + BUSCADOR + CARRITO
        // --------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // X
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar"
                )
            }

            // BARRA DE BÚSQUEDA
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

            // CARRITO
            IconButton(
                onClick = {
                    println("Abrir carrito")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Carrito"
                )
            }
        }

        Spacer(
            modifier = Modifier.size(20.dp)
        )

        // --------------------------------
        // TÍTULO
        // --------------------------------

        Text(
            text = "Mi cuenta",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.size(16.dp)
        )

        // --------------------------------
        // OPCIONES
        // --------------------------------

        CuentaOption(
            icon = Icons.Default.AccountCircle,
            title = "Mi cuenta"
        )

        CuentaOption(
            icon = Icons.Default.Pets,
            title = "Mis mascotas"
        )

        CuentaOption(
            icon = Icons.Default.LocalShipping,
            title = "Seguimiento de pedidos"
        )

        CuentaOption(
            icon = Icons.Default.Notifications,
            title = "Notificaciones"
        )

        CuentaOption(
            icon = Icons.Default.AccountCircle,
            title = "Detalles de la cuenta"
        )

        CuentaOption(
            icon = Icons.Default.Palette,
            title = "Apariencia"
        )

        CuentaOption(
            icon = Icons.Default.SupportAgent,
            title = "Soporte técnico"
        )

        CuentaOption(
            icon = Icons.Default.CreditCard,
            title = "Métodos de pago"
        )

        CuentaOption(
            icon = Icons.Default.History,
            title = "Historial de compras"
        )

        CuentaOption(
            icon = Icons.Default.LocalShipping,
            title = "Direcciones de envío"
        )

        CuentaOption(
            icon = Icons.Default.Logout,
            title = "Cerrar sesión"
        )
    }
}


// ------------------------------------------
// OPCIÓN DE CUENTA
// ------------------------------------------

@Composable
fun CuentaOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable {
                println("Seleccionado: $title")
            },
        shape = RoundedCornerShape(12.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(26.dp)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}