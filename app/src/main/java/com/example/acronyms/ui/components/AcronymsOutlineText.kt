package com.example.acronyms.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.acronyms.R

@Composable
fun AcronymsOutlinedTextField(search: (String) -> Unit, enabled: Boolean = true, inputFromState: String? = null) {
    var text by remember { mutableStateOf(inputFromState ?: "") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
        },
        singleLine = true,
        trailingIcon = @Composable {
            IconButton(
                onClick = {
                    if (text.isNotEmpty() && enabled) search(text)
                },
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search Button",
                    tint = Color.Black,
                )
            }
        },
        keyboardActions = KeyboardActions(
            onDone = { if (text.isNotEmpty() && enabled) search(text) },
            onSearch = { if (text.isNotEmpty() && enabled) search(text) }
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        label = { Text(stringResource(id = R.string.landing_screen_search_title)) },
        modifier = Modifier.padding(16.dp)
    )
}