package com.example.harrypotterapp.presentation.listScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ListScreenViewModel
    @Inject
    constructor(
        private val repository: CharacterRepository,
    ) : ViewModel() {
        private var showLoadingScreen = true
        private var showErrorScreen = true

        fun triggerRefresh() {
            showLoadingScreen = false
            viewModelScope.launch {
                triggerChannel.send(Unit)
            }
        }

        private val _searchText = MutableStateFlow("")

        @OptIn(FlowPreview::class)
        val searchText =
            _searchText
                .asStateFlow()
                .debounce(1000)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = "",
                )

        fun onSearchTextChange(searchText: String) {
            _searchText.value = searchText
        }

        private var _toastMessage = MutableStateFlow<String?>(null)
        var toastMessage = _toastMessage.asStateFlow()

        private val triggerChannel = Channel<Unit>(Channel.CONFLATED)

        // Initial flow
        @OptIn(ExperimentalCoroutinesApi::class)
        val listScreenState: Flow<Resource<List<CharacterModel>>> =
            triggerChannel
                .receiveAsFlow()
                .onStart {
                    triggerChannel.send(Unit)
                }.flatMapLatest {
                    repository.getCharacterData()
                }.onEach { screen ->
                    when (screen) {
                        is Resource.Error -> _toastMessage.value = screen.error
                        is Resource.Success -> showErrorScreen = false
                        is Resource.Loading -> {}
                    }
                }.filterNot { !showErrorScreen && it == Resource.Error("") }
                .filterNot { !showLoadingScreen && it == Resource.Loading }
                .flowOn(Dispatchers.IO)

        // Search filtered flow
        // Hot flow to emit the current screen before a network request.
        // Stops loading spinners showing in pages that wont update often
        @OptIn(ExperimentalCoroutinesApi::class)
        val filteredListScreenState: Flow<Resource<List<CharacterModel>>> =
            searchText
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        listScreenState
                    } else {
                        repository.searchCharacters(query)
                    }
                }.stateIn(
                    scope = this.viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = Resource.Loading,
                )

        fun clearToast() {
            _toastMessage.value = null
        }
    }
