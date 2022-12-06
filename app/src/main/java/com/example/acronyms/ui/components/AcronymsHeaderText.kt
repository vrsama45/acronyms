package com.example.acronyms.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.acronyms.ui.theme.AcronymsTheme

@Composable
fun AcronymsHeaderText(name: String) {
    Text(
        text = "$name!",
        fontSize = 24.sp,
        color = Color.Black,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.SansSerif,
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun AcronymsHeaderTextPreview() {
    AcronymsTheme {
        AcronymsHeaderText("Android")
    }
}