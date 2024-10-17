package com.example.harrypotterapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningReduce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ListScreenViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private var showLoadingScreen = true
    private var showErrorScreen = true
    fun triggerRefresh() {
        showLoadingScreen = false
        showErrorScreen = false
        viewModelScope.launch {
            triggerChannel.send(Unit)
        }
    }

    private val _searchText = MutableStateFlow("")


    @OptIn(FlowPreview::class)
    private val searchText =
        _searchText.asStateFlow().debounce(1000).onStart { emit(_searchText.toString()) }

    fun onSearchTextChange(searchText: String) {
        _searchText.value = searchText
    }

    private var _toastMessage = MutableStateFlow<String?>(null)
    var toastMessage = _toastMessage.asStateFlow()

    private val triggerChannel = Channel<Unit>(Channel.CONFLATED)


    @OptIn(ExperimentalCoroutinesApi::class)
    val listScreenState: StateFlow<Resource<List<CharacterModel>>> =
        triggerChannel.receiveAsFlow().onStart {
            triggerChannel.send(Unit)
        }.flatMapLatest {
            repository.getCharacterData()
        }.onEach { screen ->
            when(screen){
                is Resource.Error -> _toastMessage.value = screen.error
                is Resource.Success,
                is Resource.Loading -> {}
            }
        }.filterNot {
            !showLoadingScreen && it == Resource.Loading
        }.filterNot {
            !showErrorScreen && it is Resource.Error
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 1), Resource.Loading
        )




    // Filtered flow for the view
    val filteredListScreenState: StateFlow<Resource<List<CharacterModel>>> = combine(
        listScreenState, searchText
    ) { listState, query ->
        when (listState) {
            is Resource.Success -> {
                val filteredList = if (query.isBlank()) {
                    listState.data
                } else {
                    listState.data.filter { it.searchCharacterInfo(query) }
                }
                Resource.Success(filteredList)
            }

            is Resource.Loading -> Resource.Loading
            is Resource.Error -> Resource.Error(listState.error)
        }
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 1000), Resource.Loading
    )

    fun clearToast() {
        _toastMessage.value = null
    }
}