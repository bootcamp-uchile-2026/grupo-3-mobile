package dcc.uchile.com.example.petlove

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import java.time.YearMonth
import java.time.format.TextStyle as JavaTextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen() {

    var selectedDay by remember {
        mutableStateOf<Int?>(null)
    }

    var selectedMonth by remember {
        mutableStateOf(YearMonth.of(2026, 9))
    }

    var searchText by remember {
        mutableStateOf("")
    }

    var showFilterSheet by remember {
        mutableStateOf(false)
    }

    var selectedFilters by remember {
        mutableStateOf(setOf<String>())
    }

    val filterColor = Color(0xFFDD5A21)

    val previousMonth = selectedMonth.minusMonths(1)
    val nextMonth = selectedMonth.plusMonths(1)

    val currentMonthName = selectedMonth.month
        .getDisplayName(
            JavaTextStyle.FULL,
            Locale("es", "ES")
        )
        .replaceFirstChar { it.uppercase() }

    val previousMonthName = previousMonth.month
        .getDisplayName(
            JavaTextStyle.SHORT,
            Locale("es", "ES")
        )
        .replace(".", "")
        .replaceFirstChar { it.uppercase() }

    val nextMonthName = nextMonth.month
        .getDisplayName(
            JavaTextStyle.SHORT,
            Locale("es", "ES")
        )
        .replace(".", "")
        .replaceFirstChar { it.uppercase() }

    val firstDayOfMonth = selectedMonth.atDay(1)

    val firstDayOffset =
        firstDayOfMonth.dayOfWeek.value - 1

    val daysInMonth = selectedMonth.lengthOfMonth()

    val totalCells =
        firstDayOffset + daysInMonth

    val numberOfWeeks =
        (totalCells + 6) / 7

    val weeks = mutableListOf<List<String>>()

    var currentDay = 1

    repeat(numberOfWeeks) {

        val week = mutableListOf<String>()

        repeat(7) { dayOfWeekIndex ->

            val cellIndex =
                weeks.size * 7 + dayOfWeekIndex

            if (
                cellIndex < firstDayOffset ||
                currentDay > daysInMonth
            ) {
                week.add("")
            } else {
                week.add(currentDay.toString())
                currentDay++
            }
        }

        weeks.add(week)
    }

    val appointmentDays =
        if (selectedMonth == YearMonth.of(2026, 8)) {
            setOf(29, 31)
        } else {
            emptySet()
        }

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
                contentDescription = "Cerrar agenda",
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = "Mi Agenda",
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
                    .padding(
                        horizontal = 12.dp
                    ),
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
                        value = searchText,
                        onValueChange = {
                            searchText = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme
                                .colorScheme
                                .onSurface
                        ),
                        decorationBox = { innerTextField ->

                            if (searchText.isEmpty()) {

                                Text(
                                    text = "Buscar...",
                                    fontSize = 14.sp,
                                    color = MaterialTheme
                                        .colorScheme
                                        .onSurfaceVariant
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
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(6.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable {

                        selectedMonth =
                            selectedMonth.minusMonths(1)

                        selectedDay = null
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Mes anterior",
                    modifier = Modifier.size(15.dp)
                )

                Spacer(
                    modifier = Modifier.width(3.dp)
                )

                Text(
                    text = previousMonthName,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight()
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = currentMonthName.uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable {

                        selectedMonth =
                            selectedMonth.plusMonths(1)

                        selectedDay = null
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = nextMonthName,
                    style = MaterialTheme.typography.labelSmall
                )

                Spacer(
                    modifier = Modifier.width(3.dp)
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Mes siguiente",
                    modifier = Modifier.size(15.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            verticalAlignment = Alignment.CenterVertically
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
            modifier = Modifier.height(2.dp)
        )

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
                            day != null &&
                                    day in appointmentDays

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
                                            appointmentDays =
                                                appointmentDays,
                                            onSelect = {
                                                selectedDay = it
                                            }
                                        )
                                    }
                                },
                            contentAlignment =
                                Alignment.TopStart
                        ) {

                            if (day != null) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(6.dp)
                                ) {

                                    Text(
                                        text = day.toString(),
                                        style = MaterialTheme
                                            .typography
                                            .bodySmall
                                    )

                                    if (hasAppointment) {

                                        Spacer(
                                            modifier = Modifier
                                                .height(3.dp)
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

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Button(
            onClick = {
                println("Agregar recordatorio")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
        ) {

            Text(
                text = "+ Agregar recordatorio",
                fontSize = 13.sp
            )
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
                    horizontalArrangement =
                        Arrangement.Start
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
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        text = "Filtro",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme
                            .typography
                            .titleMedium
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
                    style = MaterialTheme
                        .typography
                        .titleSmall
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
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    FilterChip(
                        selected =
                            "Tratamiento" in selectedFilters,
                        onClick = {

                            selectedFilters =
                                toggleFilter(
                                    selectedFilters,
                                    "Tratamiento"
                                )
                        },
                        label = {

                            Text(
                                text = "Tratamiento",
                                maxLines = 1,
                                softWrap = false,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        },
                        modifier = Modifier.width(112.dp),
                        colors =
                            androidx.compose.material3.FilterChipDefaults
                                .filterChipColors(
                                    containerColor =
                                        filterColor,
                                    labelColor =
                                        Color.White,
                                    selectedContainerColor =
                                        filterColor,
                                    selectedLabelColor =
                                        Color.White
                                )
                    )

                    FilterChip(
                        selected =
                            "Vacuna" in selectedFilters,
                        onClick = {

                            selectedFilters =
                                toggleFilter(
                                    selectedFilters,
                                    "Vacuna"
                                )
                        },
                        label = {

                            Text(
                                text = "Vacuna",
                                maxLines = 1,
                                softWrap = false,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        },
                        modifier = Modifier.width(82.dp),
                        colors =
                            androidx.compose.material3.FilterChipDefaults
                                .filterChipColors(
                                    containerColor =
                                        filterColor,
                                    labelColor =
                                        Color.White,
                                    selectedContainerColor =
                                        filterColor,
                                    selectedLabelColor =
                                        Color.White
                                )
                    )

                    FilterChip(
                        selected =
                            "Cita médica" in selectedFilters,
                        onClick = {

                            selectedFilters =
                                toggleFilter(
                                    selectedFilters,
                                    "Cita médica"
                                )
                        },
                        label = {

                            Text(
                                text = "Cita médica",
                                maxLines = 1,
                                softWrap = false,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        },
                        modifier = Modifier.width(105.dp),
                        colors =
                            androidx.compose.material3.FilterChipDefaults
                                .filterChipColors(
                                    containerColor =
                                        filterColor,
                                    labelColor =
                                        Color.White,
                                    selectedContainerColor =
                                        filterColor,
                                    selectedLabelColor =
                                        Color.White
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
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {

                    Text(
                        text = "Aplicar"
                    )
                }
            }
        }
    }

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

            title = {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.SpaceBetween,
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        text = "Detalles de la consulta",
                        style = MaterialTheme
                            .typography
                            .titleLarge
                    )

                    IconButton(
                        onClick = {
                            selectedDay = null
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Close,
                            contentDescription =
                                "Cerrar"
                        )
                    }
                }
            },

            text = {

                Column {

                    Text(
                        text = "Paciente: $patient"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "Veterinario: $veterinarian"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "Tipo de consulta: $consultation"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "Fecha y hora: $dateTime"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "Lugar: Clínica Veterinaria PetLove"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "Valor a pagar: $price"
                    )
                }
            },

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

fun onDaySelected(
    day: Int,
    appointmentDays: Set<Int>,
    onSelect: (Int) -> Unit
) {

    if (day in appointmentDays) {
        onSelect(day)
    }
}

fun toggleFilter(
    currentFilters: Set<String>,
    filter: String
): Set<String> {

    return if (filter in currentFilters) {
        currentFilters - filter
    } else {
        currentFilters + filter
    }
}