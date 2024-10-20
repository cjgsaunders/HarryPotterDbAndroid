package com.example.harrypotterapp.presentation.detailScreen

import androidx.lifecycle.ViewModel
import com.example.harrypotterapp.domain.CharacterDomainModel
import com.example.harrypotterapp.domain.CharacterRepository
import com.example.harrypotterapp.domain.Resource
import com.example.harrypotterapp.domain.mappers.toCharacterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class DetailScreenViewModel
@Inject
constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    // flow alternative
    // needs launched effect
//    private val _singleCharacterScreenState = MutableStateFlow<Resource<CharacterModel>>(Resource.Loading)
//    val singleCharacterScreenState: StateFlow<Resource<CharacterModel>> = _singleCharacterScreenState
//
//    fun collectSelectedCharacter(characterId: String) {
//        viewModelScope.launch {
//            repository.getCharacterById(characterId).collect { resource ->
//                _singleCharacterScreenState.value = resource
//            }
//        }
//    }

    private val triggerChannel = Channel<Unit>(Channel.CONFLATED)

    @OptIn(ExperimentalCoroutinesApi::class)
    val singleCharacterScreenStat1e: (
        String
    ) -> Flow<Resource<CharacterDomainModel>> = { characterId ->
        triggerChannel
            .receiveAsFlow()
            .flatMapLatest {
                repository.getCharacterById(characterId)
            }.map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(resource.data.toCharacterModel())
                    is Resource.Error -> Resource.Error(resource.error)
                    is Resource.Loading -> Resource.Loading
                }
            }.onStart { triggerChannel.send(Unit) }
    }
}
