package dcc.uchile.com.example.petlove

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dcc.uchile.com.example.petlove.ui.theme.PetLoveTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PetLoveTheme {
                PetLoveApp()
            }
        }
    }
}


@Composable
fun PetLoveApp() {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(

        bottomBar = {

            NavigationBar {

                // INICIO
                NavigationBarItem(
                    selected = currentRoute == "home",
                    onClick = {
                        navController.navigate("home")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Inicio"
                        )
                    },
                    label = {
                        Text(
                            text = "Inicio",
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                )

                // SERVICIO VETERINARIO
                NavigationBarItem(
                    selected = currentRoute == "veterinario",
                    onClick = {
                        navController.navigate("veterinario")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.MedicalServices,
                            contentDescription = "Servicio Veterinario"
                        )
                    },
                    label = {
                        Text(
                            text = "Servicio\nVeterinario",
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )

                // AGENDA
                NavigationBarItem(
                    selected = currentRoute == "agenda",
                    onClick = {
                        navController.navigate("agenda")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Agenda"
                        )
                    },
                    label = {
                        Text(
                            text = "Agenda",
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                )

                // MIS MASCOTAS
                NavigationBarItem(
                    selected = currentRoute == "mascotas",
                    onClick = {
                        navController.navigate("mascotas")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Pets,
                            contentDescription = "Mis Mascotas"
                        )
                    },
                    label = {
                        Text(
                            text = "Mis Mascotas",
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            softWrap = false,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                letterSpacing = (-0.3).sp
                            )
                        )
                    }
                )

                // CUENTA
                NavigationBarItem(
                    selected = currentRoute == "cuenta",
                    onClick = {
                        navController.navigate("cuenta")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Cuenta"
                        )
                    },
                    label = {
                        Text(
                            text = "Cuenta",
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                )
            }
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {

            // INICIO
            composable("home") {
                HomeScreen()
            }

            // SERVICIO VETERINARIO
            composable("veterinario") {
                ServicioVeterinarioScreen()
            }

            // AGENDA
            composable("agenda") {
                AgendaScreen()
            }

            // MIS MASCOTAS
            composable("mascotas") {
                MascotasScreen()
            }

            // CUENTA
            composable("cuenta") {
                CuentaScreen()
            }

            // HISTORIAL
            composable("historial") {
                HistorialScreen()
            }

            // RECORDATORIOS
            composable("recordatorios") {
                RecordatoriosScreen()
            }
        }
    }
}


@Composable
fun HistorialScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Historial",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Control veterinario",
            style = MaterialTheme.typography.titleMedium
        )

        Text("Fecha: 10/08/2026")
        Text("Motivo: Control general")
        Text("Estado: Realizado")

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Vacunación",
            style = MaterialTheme.typography.titleMedium
        )

        Text("Fecha: 05/07/2026")
        Text("Vacuna: Antirrábica")
        Text("Estado: Realizado")
    }
}


@Composable
fun RecordatoriosScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Recordatorios",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Vacuna próxima",
            style = MaterialTheme.typography.titleMedium
        )

        Text("Firulais")
        Text("Fecha: 20/08/2026")

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Control veterinario",
            style = MaterialTheme.typography.titleMedium
        )

        Text("Michi")
        Text("Fecha: 25/08/2026")
    }
}