package com.example.acronyms.ui.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.acronyms.R
import com.example.acronyms.data.AcronymMeaning
import com.example.acronyms.ui.components.AcronymsHeaderText
import com.example.acronyms.ui.components.AcronymsMeaningCard
import com.example.acronyms.ui.components.AcronymsMediumText
import com.example.acronyms.ui.components.AcronymsOutlinedTextField
import com.example.acronyms.ui.theme.AcronymsTheme
import com.example.acronyms.viewstate.AcronymMeaningState

@Composable
fun LandingLayoutUI(
    state: AcronymMeaningState,
    search: (String) -> Unit
) {
    AcronymsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AcronymsHeaderText(stringResource(id = R.string.landing_screen_title))
                AcronymsOutlinedTextField(search = {
                    search(it)
                }, !state.showLoading)

                Divider(modifier = Modifier.padding(top = 12.dp, bottom = 12.dp))
                if (state.showLoading) {
                    CircularProgressIndicator()
                } else if (state.isError) {
                    AcronymsMediumText(stringResource(id = R.string.landing_screen_error_msg))
                } else if (state.acronymMeaningList != null) {
                    if (state.acronymMeaningList.isEmpty()) {
                        AcronymsMediumText(stringResource(R.string.landing_screen_no_meanings_found))
                    } else {
                        LazyColumn {
                            items(state.acronymMeaningList) { item: AcronymMeaning ->
                                AcronymsMeaningCard(acronymMeaning = item)
                            }
                        }
                    }
                }
            }

        }
    }
}