package com.example.wulidee.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wulidee.data.local.Person
import com.example.wulidee.ui.PersonViewModel

@Composable
fun UserScreen(personViewModel: PersonViewModel) {
    val mainPerson by personViewModel.mainPerson.collectAsState(initial = null)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)) {
        Text(
            text = "Benutzer Settings",
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp),
            style = MaterialTheme.typography.titleLarge)
        Text(
            text = "Hauptnutzer ändern",
            modifier = Modifier
                .clickable {
                    if (mainPerson != null && mainPerson!!.name.isNotEmpty()) {
                        personViewModel.updatePerson(Person(id = mainPerson!!.id, name = "", mainPerson = true))
                    }
                }
                .padding(end = 16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
