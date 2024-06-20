package com.example.myfilms.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfilms.R
import com.example.myfilms.data.MainFilmUiState

@Composable
fun StartScreen(
    uiState: MainFilmUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = uiState.avatarId),
            contentDescription = "Avater image",
            modifier =  Modifier
                .size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = uiState.nickname
        )

    }
}

@Preview
@Composable
fun StartScreenPreview() {
    StartScreen(MainFilmUiState(R.drawable.basic_person_icon_foreground, "Default nickname"))
}
