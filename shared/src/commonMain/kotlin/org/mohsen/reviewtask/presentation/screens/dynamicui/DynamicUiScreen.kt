package org.mohsen.reviewtask.presentation.screens.dynamicui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.mohsen.reviewtask.presentation.components.ComponentRenderer
import reviewtask.shared.generated.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicUiScreen(
    viewModel: DynamicUiViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing = uiState is UiState.Loading && (uiState as UiState.Loading).isRefresh

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            viewModel.fetchData(isRefresh = true)
        },
        modifier = Modifier.fillMaxSize()
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (val state = uiState) {
                is UiState.Loading -> {
                    if (!state.isRefresh) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        state.message?.let {
                            Text(
                                text = stringResource(Res.string.error_prefix, it),
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        Button(onClick = { viewModel.fetchData() }) {
                            Text(stringResource(Res.string.retry))
                        }
                    }
                }

                is UiState.Success -> {
                    // Pass the ViewModel's update function to the renderer
                    ComponentRenderer(
                        component = state.component,
                        onComponentValueChange = viewModel::updateUiComponent // Correctly passing the lambda
                    )
                }
            }
        }
    }
}
