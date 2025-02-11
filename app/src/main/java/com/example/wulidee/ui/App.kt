package com.example.wulidee.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wulidee.data.local.Person

@Composable
fun App(personViewModel: PersonViewModel, ideaViewModel: IdeaViewModel) {
    val isInitialized by personViewModel.isInitialized.collectAsState()

    if (!isInitialized) {
        LoadingScreen()
    } else {
        val mainPerson = personViewModel.mainPerson.collectAsState(initial = null).value

        if (mainPerson == null || mainPerson.name.isEmpty()) {
            userAdd(personViewModel)
        } else {
            NavGraph(personViewModel, ideaViewModel)
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Daten werden geladen...")
        }
    }
}


@Composable
fun userAdd(personViewModel: PersonViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    val mainPerson by personViewModel.mainPerson.collectAsState(initial = null)
    val mainPersonName = mainPerson?.name

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (mainPersonName == "") {
            showDialog = true
        }

        if (showDialog) {
            NameInputDialog(
                onDismiss = { showDialog = false },
                onConfirm = { enteredName ->
                    personViewModel.updatePerson(Person(id = mainPerson!!.id, name = enteredName, mainPerson = true))
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun NameInputDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Gib deinen Namen ein") },
        text = {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") }
            )
        },
        confirmButton = {
            Button(onClick = { onConfirm(name) }) {
                Text("OK")
            }
        }
    )
}
