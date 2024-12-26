package com.example.bookshelf.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookshelf.ui.component.CurrentState
import com.example.bookshelf.ui.screen.HomeScreen
import com.example.bookshelf.viewmodel.AppUIState
import com.example.bookshelf.viewmodel.BookshelfViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(
    modifier: Modifier = Modifier,
    viewModel: BookshelfViewModel = hiltViewModel()
) {

    val uiState: AppUIState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            AppTopBar(
                scrollBehavior = scrollBehavior
            )
        },
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        when (uiState) {
            is AppUIState.Success -> HomeScreen(
                bookResponse = (uiState as AppUIState.Success).response,
                contentPadding = it
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
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text("Bookshelf") },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}