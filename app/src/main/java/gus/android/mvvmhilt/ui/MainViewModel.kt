package gus.android.mvvmhilt.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import gus.android.mvvmhilt.model.Blog
import gus.android.mvvmhilt.repository.MainRepository
import gus.android.mvvmhilt.utlil.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel
@ViewModelInject
constructor(
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>> get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when(mainStateEvent) {
                is MainStateEvent.GetBlogEvents -> {
                    mainRepository.getBlogs()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.None -> {

                }

            }
        }
    }
}

sealed class MainStateEvent {

    object GetBlogEvents : MainStateEvent()

    object None : MainStateEvent()
}