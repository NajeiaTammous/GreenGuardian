package com.greeners.greenguardian.presentation.feature.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.greeners.greenguardian.domain.GreenGuardianRepository
import com.greeners.greenguardian.presentation.feature.base.BaseViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val repository: GreenGuardianRepository
) : BaseViewModel<SearchUiState, SearchUiEffect>(SearchUiState()), SearchInteractionListener {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch {
            searchQuery.debounce(500).collect { query ->
                    updateState {
                        it.copy(
                            isSearching = true,
                            isInit = false
                        )
                    }
                if (query.isNotEmpty()) {
                    tryToExecute(
                        function = {
                            repository.findPlantByName(plantName = query, limit = 20)
                        },
                        onError = {
                            Log.d("TAG", "onSearchTextChange: eeeeeeeeeeeee")
                        },
                        onSuccess = { plants ->
                            updateState {
                                it.copy(
                                    isLoading = false,
                                    error = "",
                                    searchResults = plants.entities,
                                    isSearching = false
                                )
                            }
                        }
                    )
                } else {
                    updateState {
                        it.copy(
                            searchResults = emptyList(),
                            isSearching = false,
                            isInit = true
                        )
                    }
                }
            }
        }
    }

    override fun onSearchTextChange(query: String) {
        _searchQuery.value = query
    }

    override fun onPlantSelected(plantId: String) {
        sendNewEffect(SearchUiEffect.OnClickPlantCard(plantId))
    }
}