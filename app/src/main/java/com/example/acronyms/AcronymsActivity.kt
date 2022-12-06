package com.example.acronyms

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.acronyms.ui.layout.LandingLayoutUI
import com.example.acronyms.viewmodel.AcronymsViewModel
import com.example.acronyms.viewstate.AcronymMeaningState
import kotlinx.coroutines.flow.Flow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: AcronymsViewModel by viewModels()
        setContent {

            // Remember viewmodel's stateflow which will be a state-hodler observable flow, persist all the emits/updates to its collector
            val state =
                rememberViewModelStateFlow(viewModel.uiState).collectAsState(initial = AcronymMeaningState())

            // Supply viewmodel's stateflow's value to the composable UI components
            LandingLayoutUI(state.value) { searchString ->
                hideKeyPad()
                viewModel.getAcronymMeanings(searchString)
            }
        }
    }

    private fun hideKeyPad() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}

@Composable
fun <T> rememberViewModelStateFlow(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(lifecycle = lifecycle, minActiveState = minActiveState)
}