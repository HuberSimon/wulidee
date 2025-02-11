package com.example.wulidee.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wulidee.ui.IdeaViewModel
import com.example.wulidee.ui.PersonViewModel

@Composable
fun ShopScreen(personViewModel: PersonViewModel, ideaViewModel: IdeaViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Hallo auf der Shop Seite")
    }
}