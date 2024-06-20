package com.example.myfilms.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfilms.R
import com.example.myfilms.data.MainFilmUiState

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStartScreen(
    goBack: ()->Unit,
    uiState: MainFilmUiState
) {
    var textInput by remember { mutableStateOf(TextFieldValue(uiState.nickname)) }

    val images = listOf(R.drawable.baseline_white_person_24, R.drawable.baseline_white_person_2_24, R.drawable.baseline_white_person_3_24, R.drawable.baseline_white_person_4_24, R.drawable.baseline_white_local_movies_24)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Przewijane obrazki do wyboru
        LazyHorizontalGrid(
            rows = GridCells.Adaptive(200.dp),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            items(images.size) { index ->
                Image(
                    painter = painterResource(id = images[index]),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clickable {
                            uiState.avatarId = images[index]
                        }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        var editedNickname by mutableStateOf(uiState.nickname)
        TextField(
            value = textInput.text,
            onValueChange = {
                textInput = textInput.copy(text = it)
                editedNickname = textInput.text
            },
            label = { Text("Enter nickname") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                uiState.nickname = textInput.text
                goBack()
            }
        ) {
            Icon(imageVector = Icons.Default.Save, contentDescription = null)
            Text(text = "Zapisz")
        }
    }
}

@Preview
@Composable
fun ImageSelectionScreenPreview() {
    //EditStartScreen(selectedImg = R.drawable.basic_person_icon_foreground, textIn = "Basic text", goBack = {})
}