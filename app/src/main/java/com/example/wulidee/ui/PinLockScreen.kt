package com.example.wulidee.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wulidee.R
import kotlinx.coroutines.delay

const val pinSize = 4

@Composable
fun PinLockScreen(
    password: Int,
    onPinSuccess: () -> Unit
) {
    val inputPin = remember { mutableStateListOf<Int>() }
    val error = remember { mutableStateOf("") }
    val showSuccess = remember { mutableStateOf(false) }

    if (inputPin.size == 4) {
        LaunchedEffect(true) {
            delay(300)
            if (inputPin.joinToString("").hashCode() == password) {
                showSuccess.value = true
                error.value = ""
                onPinSuccess()
            } else {
                inputPin.clear()
                error.value = "Falsche PIN, bitte erneut versuchen!"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_pinlock_wulidee),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )

                Text(
                    text = "Bitte PIN eingeben",
                    style = typography.h6,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(30.dp))

                if (showSuccess.value) {
                    LoadingScreen()
                } else {
                    Row {
                        (0 until pinSize).forEach {
                            Icon(
                                imageVector = if (inputPin.size > it) Icons.Filled.RadioButtonChecked else Icons.Filled.RadioButtonUnchecked,
                                contentDescription = it.toString(),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(30.dp),
                                tint = Color.Black
                            )
                        }
                    }
                }

                Text(
                    text = error.value,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.height(50.dp))
            }

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ) {
                (1..9).chunked(3).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        row.forEach { number ->
                            PinKeyItem(
                                onClick = { inputPin.add(number) }
                            ) {
                                Text(
                                    text = number.toString(),
                                    style = typography.h5,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                    )
                    PinKeyItem(
                        onClick = { inputPin.add(0) },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "0",
                            style = typography.h5,
                            modifier = Modifier.padding(4.dp),
                            color = Color.White
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Löschen",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                if (inputPin.isNotEmpty()) {
                                    inputPin.removeLast()
                                }
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun PinKeyItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier.padding(8.dp),
    shape: Shape = CircleShape,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = contentColorFor(backgroundColor = backgroundColor),
    elevation: Dp = 4.dp,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        tonalElevation = elevation
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides contentColor.alpha
        ) {
            ProvideTextStyle(
                MaterialTheme.typography.displayMedium
            ) {
                Box(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 64.dp, minHeight = 64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    }
}

