package com.example.harrypotterapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harrypotterapp.data.CharacterDto
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.domain.models.CharacterModel
import com.example.harrypotterapp.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val triggerChannel = Channel<Unit>(Channel.CONFLATED)

    @OptIn(ExperimentalCoroutinesApi::class)
    val listScreenState : StateFlow<Resource<List<CharacterModel>>> = triggerChannel.receiveAsFlow().onStart {
        triggerChannel.send(Unit)
    }.flatMapLatest {  repository.getCharacterData() }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        Resource.Loading
    )
}