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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun AgendaScreen() {

    var selectedDay by remember {
        mutableStateOf<Int?>(null)
    }

    // --------------------------------------------------
    // DÍAS DEL CALENDARIO
    // --------------------------------------------------

    val weeks = listOf(
        listOf("", "", "", "", "", "1", "2"),
        listOf("3", "4", "5", "6", "7", "8", "9"),
        listOf("10", "11", "12", "13", "14", "15", "16"),
        listOf("17", "18", "19", "20", "21", "22", "23"),
        listOf("24", "25", "26", "27", "28", "29", "30"),
        listOf("31", "", "", "", "", "", "")
    )

    // Días que tienen una cita
    val appointmentDays = setOf(29, 31)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // --------------------------------------------------
        // X PARA CERRAR LA AGENDA
        // --------------------------------------------------

        IconButton(
            onClick = {
                // Por ahora no hace nada.
                // Después podemos hacer que vuelva a Inicio.
            },
            modifier = Modifier.size(40.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar agenda"
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // --------------------------------------------------
        // TÍTULO
        // --------------------------------------------------

        Text(
            text = "Mi Agenda",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // --------------------------------------------------
        // MES
        // --------------------------------------------------

        Text(
            text = "Agosto 2026",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // --------------------------------------------------
        // DÍAS DE LA SEMANA
        // --------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            listOf(
                "LUN",
                "MAR",
                "MIÉ",
                "JUE",
                "VIE",
                "SÁB",
                "DOM"
            ).forEach { day ->

                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // --------------------------------------------------
        // CUADRÍCULA DEL CALENDARIO
        // --------------------------------------------------

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            weeks.forEach { week ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    week.forEach { dayString ->

                        val day =
                            dayString.toIntOrNull()

                        val hasAppointment =
                            day != null && day in appointmentDays

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme
                                        .colorScheme
                                        .outlineVariant
                                )
                                .background(
                                    color =
                                        if (hasAppointment) {
                                            MaterialTheme
                                                .colorScheme
                                                .primaryContainer
                                        } else {
                                            MaterialTheme
                                                .colorScheme
                                                .surface
                                        }
                                )
                                .clickable(
                                    enabled = day != null
                                ) {

                                    if (day != null) {
                                        onDaySelected(
                                            day = day,
                                            appointmentDays = appointmentDays,
                                            onSelect = {
                                                selectedDay = it
                                            }
                                        )
                                    }
                                },
                            contentAlignment = Alignment.TopStart
                        ) {

                            if (day != null) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp)
                                ) {

                                    Text(
                                        text = day.toString(),
                                        style = MaterialTheme
                                            .typography
                                            .bodyMedium
                                    )

                                    if (hasAppointment) {

                                        Spacer(
                                            modifier = Modifier.height(6.dp)
                                        )

                                        Text(
                                            text = "● Cita",
                                            style = MaterialTheme
                                                .typography
                                                .labelSmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // --------------------------------------------------
    // VENTANA DE DETALLES
    // --------------------------------------------------

    selectedDay?.let { day ->

        val patient =
            if (day == 29) {
                "Firulais"
            } else {
                "Michi"
            }

        val veterinarian =
            if (day == 29) {
                "Dr. González"
            } else {
                "Dra. Martínez"
            }

        val consultation =
            if (day == 29) {
                "Control veterinario"
            } else {
                "Vacunación"
            }

        val dateTime =
            if (day == 29) {
                "29/08/2026 - 10:30"
            } else {
                "31/08/2026 - 16:00"
            }

        val price =
            if (day == 29) {
                "$25.000"
            } else {
                "$20.000"
            }

        AlertDialog(

            onDismissRequest = {
                selectedDay = null
            },

            // TÍTULO + X
            title = {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Detalles de la consulta",
                        style = MaterialTheme.typography.titleLarge
                    )

                    IconButton(
                        onClick = {
                            selectedDay = null
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar"
                        )
                    }
                }
            },

            // INFORMACIÓN
            text = {

                Column {

                    Text(
                        text = "Paciente: $patient"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Veterinario: $veterinarian"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Tipo de consulta: $consultation"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Fecha y hora: $dateTime"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Lugar: Clínica Veterinaria PetLove"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Valor a pagar: $price"
                    )
                }
            },

            // REAGENDAR
            confirmButton = {

                Button(
                    onClick = {

                        println(
                            "Reagendar cita del día $day"
                        )
                    }
                ) {

                    Text(
                        text = "Reagendar"
                    )
                }
            },

            // ANULAR
            dismissButton = {

                TextButton(
                    onClick = {

                        println(
                            "Anular cita del día $day"
                        )
                    }
                ) {

                    Text(
                        text = "Anular hora"
                    )
                }
            }
        )
    }
}


// --------------------------------------------------
// FUNCIÓN PARA SELECCIONAR UN DÍA
// --------------------------------------------------

fun onDaySelected(
    day: Int,
    appointmentDays: Set<Int>,
    onSelect: (Int) -> Unit
) {

    if (day in appointmentDays) {
        onSelect(day)
    }
}