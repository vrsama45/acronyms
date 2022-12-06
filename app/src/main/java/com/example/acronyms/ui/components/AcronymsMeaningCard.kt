package com.example.acronyms.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.acronyms.data.AcronymMeaning

@Composable
fun AcronymsMeaningCard(acronymMeaning: AcronymMeaning) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
        elevation = 10.dp,
        shape = RectangleShape
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = acronymMeaning.lf ?: "",
                fontSize = 18.sp,
                color = Color.Black,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Frequency of Usage = ${acronymMeaning.freq ?: 0}",
                fontSize = 16.sp,
                color = Color.Black,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            Divider(modifier = Modifier.padding(top = 12.dp))
        }
    }
}