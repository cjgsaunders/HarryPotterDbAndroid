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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
open class ListScreenViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    private val searchText = _searchText.asStateFlow()

    fun onSearchTextChange(searchText: String) {
        _searchText.value = searchText
    }

    private val triggerChannel = Channel<Unit>(Channel.CONFLATED)

    // load data from api
    @OptIn(ExperimentalCoroutinesApi::class)
    private val listScreenState: StateFlow<Resource<List<CharacterModel>>> =
        triggerChannel.receiveAsFlow().onStart {
            triggerChannel.send(Unit)
        }.flatMapLatest { repository.getCharacterData() }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            Resource.Loading
        )

    // Filtered flow for the view
    @OptIn(FlowPreview::class)
    val filteredListScreenState: StateFlow<Resource<List<CharacterModel>>> = combine(
        listScreenState, searchText.debounce(1000)
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
        viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), Resource.Loading
    )
}