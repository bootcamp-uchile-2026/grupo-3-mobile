
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dcc.uchile.com.example.petlove.ui.theme.PetLoveTheme

data class RegistroHistorial(
    val id: Long = System.nanoTime(),
    val clinica: String,
    val veterinario: String,
    val fecha: String,
    val descripcion: String
)

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

    val registrosHistorial = remember {
        mutableStateListOf<RegistroHistorial>()
    }

    val examenesMedicos = remember {
        mutableStateListOf<ExamenMedico>()
    }

    val rutasMascotas = listOf(
        "mascotas",
        "registrar_mascota",
        "mi_mascota",
        "historial_medico",
        "agregar_historial_medico",
        "historial",
        "recordatorios",
        "examenes_medicos",
        "agregar_examen_medico",
        "registro_vacunas",
        "detalle_historial_medico/{registroId}",
        "editar_historial_medico/{registroId}"
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == "home",
                    onClick = {
                        navController.navigate("home") {
                            launchSingleTop = true
                            popUpTo("home") {
                                inclusive = false
                            }
                        }
                    },
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Inicio"
                        )
                    },
                    label = {
                        Text(
                            "Inicio",
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                )

                NavigationBarItem(
                    selected = currentRoute == "veterinario",
                    onClick = {
                        navController.navigate("veterinario") {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            Icons.Default.MedicalServices,
                            contentDescription = "Servicio Veterinario"
                        )
                    },
                    label = {
                        Text(
                            "Servicio\nVeterinario",
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )

                NavigationBarItem(
                    selected = currentRoute == "agenda",
                    onClick = {
                        navController.navigate("agenda") {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            Icons.Default.CalendarMonth,
                            contentDescription = "Agenda"
                        )
                    },
                    label = {
                        Text(
                            "Agenda",
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                )

                NavigationBarItem(
                    selected = currentRoute in rutasMascotas,
                    onClick = {
                        navController.navigate("mascotas") {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            Icons.Default.Pets,
                            contentDescription = "Mis Mascotas"
                        )
                    },
                    label = {
                        Text(
                            "Mis Mascotas",
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

                NavigationBarItem(
                    selected = currentRoute == "cuenta",
                    onClick = {
                        navController.navigate("cuenta") {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Cuenta"
                        )
                    },
                    label = {
                        Text(
                            "Cuenta",
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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            composable("home") {
                HomeScreen()
            }

            composable("veterinario") {
                ServicioVeterinarioScreen()
            }

            composable("agenda") {
                AgendaScreen()
            }

            composable("mascotas") {
                MascotasScreen(
                    onAddMascota = {
                        navController.navigate("registrar_mascota")
                    }
                )
            }

            composable("registrar_mascota") {
                RegistrarMascotaScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onRegisterClick = {
                        navController.navigate("mi_mascota") {
                            popUpTo("registrar_mascota") {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable("mi_mascota") {
                MiMascotaScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onNotificationsClick = {
                        navController.navigate("notificaciones")
                    },
                    onHistorialClick = {
                        navController.navigate("historial_medico")
                    },
                    onExamenesClick = {
                        navController.navigate("examenes_medicos")
                    },
                    onVacunasClick = {
                        navController.navigate("registro_vacunas")
                    }
                )
            }

            composable("registro_vacunas") {
                RegistroVacunasScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onAgregarClick = {
                        navController.navigate("examenes_medicos")
                    }
                )
            }

            composable("examenes_medicos") {
                ExamenesMedicosScreen(
                    examenes = examenesMedicos,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onAgregarClick = {
                        navController.navigate("agregar_examen_medico")
                    },
                    onEliminarClick = { id ->
                        examenesMedicos.removeAll { it.id == id }
                    }
                )
            }

            composable("agregar_examen_medico") {
                AgregarExamenMedicoScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCancelarClick = {
                        navController.popBackStack()
                    },
                    onAgregarClick = { examen, tipo, clinica, fecha ->
                        examenesMedicos.add(
                            0,
                            ExamenMedico(
                                nombre = examen,
                                tipo = tipo,
                                fecha = fecha,
                                centro = clinica
                            )
                        )

                        navController.popBackStack()
                    }
                )
            }

            composable("historial_medico") {
                HistorialMedicoScreen(
                    registros = registrosHistorial,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onAgregarClick = {
                        navController.navigate("agregar_historial_medico")
                    },
                    onFiltrosClick = {},
                    onEliminarRegistro = { registro ->
                        registrosHistorial.remove(registro)
                    },
                    onVerInformacion = { registro ->
                        navController.navigate(
                            "detalle_historial_medico/${registro.id}"
                        )
                    },
                    onActualizarRegistro = { actualizado ->
                        val indice = registrosHistorial.indexOfFirst {
                            it.id == actualizado.id
                        }

                        if (indice != -1) {
                            registrosHistorial[indice] = actualizado
                        }
                    },
                    onEditarRegistro = { registro ->
                        navController.navigate(
                            "editar_historial_medico/${registro.id}"
                        )
                    }
                )
            }

            composable("agregar_historial_medico") {
                AgregarHistorialMedicoScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCancelarClick = {
                        navController.popBackStack()
                    },
                    onAgregarClick = { clinica, veterinario, fecha, descripcion ->
                        registrosHistorial.add(
                            0,
                            RegistroHistorial(
                                clinica = clinica,
                                veterinario = veterinario,
                                fecha = fecha,
                                descripcion = descripcion
                            )
                        )

                        navController.popBackStack()
                    }
                )
            }

            composable("historial") {
                HistorialMedicoScreen(
                    registros = registrosHistorial,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onAgregarClick = {
                        navController.navigate("agregar_historial_medico")
                    },
                    onFiltrosClick = {},
                    onEliminarRegistro = { registro ->
                        registrosHistorial.remove(registro)
                    },
                    onVerInformacion = { registro ->
                        navController.navigate(
                            "detalle_historial_medico/${registro.id}"
                        )
                    },
                    onActualizarRegistro = { actualizado ->
                        val indice = registrosHistorial.indexOfFirst {
                            it.id == actualizado.id
                        }

                        if (indice != -1) {
                            registrosHistorial[indice] = actualizado
                        }
                    },
                    onEditarRegistro = { registro ->
                        navController.navigate(
                            "editar_historial_medico/${registro.id}"
                        )
                    }
                )
            }

            composable(
                route = "detalle_historial_medico/{registroId}",
                arguments = listOf(
                    navArgument("registroId") {
                        type = NavType.LongType
                    }
                )
            ) { backStackEntry ->
                val registroId =
                    backStackEntry.arguments?.getLong("registroId") ?: -1L

                val registro = registrosHistorial.find {
                    it.id == registroId
                }

                if (registro != null) {
                    DetalleHistorialMedicoScreen(
                        registro = registro,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onEditarClick = {
                            navController.navigate(
                                "editar_historial_medico/${registro.id}"
                            )
                        },
                        onEliminarClick = {
                            registrosHistorial.removeAll {
                                it.id == registro.id
                            }
                            navController.popBackStack()
                        }
                    )
                } else {
                    Text("No se encontró el registro médico.")
                }
            }

            composable(
                route = "editar_historial_medico/{registroId}",
                arguments = listOf(
                    navArgument("registroId") {
                        type = NavType.LongType
                    }
                )
            ) { backStackEntry ->
                val registroId =
                    backStackEntry.arguments?.getLong("registroId") ?: -1L

                val registro = registrosHistorial.find {
                    it.id == registroId
                }

                if (registro != null) {
                    EditarHistorialMedicoScreen(
                        registro = registro,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onGuardarClick = { actualizado ->
                            val indice = registrosHistorial.indexOfFirst {
                                it.id == actualizado.id
                            }

                            if (indice != -1) {
                                registrosHistorial[indice] = actualizado
                            }

                            navController.popBackStack()
                        }
                    )
                } else {
                    Text("No se encontró el registro médico.")
                }
            }

            composable("cuenta") {
                CuentaScreen(
                    onNotificacionesClick = {
                        navController.navigate("notificaciones")
                    }
                )
            }

            composable("notificaciones") {
                NotificacionesScreen()
            }

            composable("recordatorios") {
                RecordatoriosScreen()
            }
        }
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Vacuna próxima",
            style = MaterialTheme.typography.titleMedium
        )
        Text("Firulais")
        Text("Fecha: 20/08/2026")

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Control veterinario",
            style = MaterialTheme.typography.titleMedium
        )
        Text("Michi")
        Text("Fecha: 25/08/2026")
    }
}
