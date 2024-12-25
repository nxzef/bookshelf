package com.example.bookshelf.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookshelf.ui.component.CurrentState
import com.example.bookshelf.viewmodel.AppUIState
import com.example.bookshelf.viewmodel.BookshelfViewModel


@Composable
fun BookshelfApp(
    modifier: Modifier = Modifier,
    viewModel: BookshelfViewModel = hiltViewModel()
) {

    val uiState: AppUIState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar()
        },
        modifier = modifier
    ) {
        when (uiState) {
            is AppUIState.Success -> Text(
                (uiState as AppUIState.Success).data.totalItems.toString(),
                modifier = Modifier.padding(it)
            )

            is AppUIState.Loading -> CurrentState {
                CircularProgressIndicator()
            }

            is AppUIState.Error -> CurrentState {
                Text((uiState as AppUIState.Error).msg)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text("Bookshelf") },
        modifier = modifier
    )
}