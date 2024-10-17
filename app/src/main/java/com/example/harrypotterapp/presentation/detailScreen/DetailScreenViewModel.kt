package com.example.harrypotterapp.presentation.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {


    private val _singleCharacterScreenState = MutableStateFlow<Resource<CharacterModel>>(Resource.Loading)
    val singleCharacterScreenState: StateFlow<Resource<CharacterModel>> = _singleCharacterScreenState

    fun collectSelectedCharacter(characterId: String) {
        viewModelScope.launch {
            repository.getCharacterById(characterId).collect { resource ->
                _singleCharacterScreenState.value = resource
            }
        }
    }

    private val triggerChannel = Channel<Unit>(Channel.CONFLATED)

    @OptIn(ExperimentalCoroutinesApi::class)
    val singleCharacterScreenStat1e: (String ) -> StateFlow<Resource<CharacterModel>> ={
        characterId ->

        triggerChannel.receiveAsFlow().onStart {
            triggerChannel.send(Unit)
        }.flatMapLatest {
            repository.getCharacterById(characterId)
        }.onEach { resource ->
            when (resource) {
                is Resource.Error -> println()
                is Resource.Success,
                is Resource.Loading -> {}
            }
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 1), Resource.Loading
        )
    }
}
