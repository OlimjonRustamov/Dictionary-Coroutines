package uz.olimjon_rustamov.dictionarycoroutines.retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.ApiHelperImpl
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.RetrofitBuilder
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.models.WordResponse
import uz.olimjon_rustamov.dictionarycoroutines.retrofit.models.WordResponseItem
import uz.olimjon_rustamov.dictionarycoroutines.utils.Resource

class SingleNetworkCallViewModel() : ViewModel() {

    private val wordResponseItem = MutableLiveData<Resource<WordResponseItem>>()

    fun fetchWord(word: String) {
        viewModelScope.launch {
            wordResponseItem.postValue(Resource.loading(null))
            try {
                val apiHelper=ApiHelperImpl(RetrofitBuilder.apiservice)
                val wordFromApi = apiHelper.getWord(word)[0]
                wordResponseItem.postValue(Resource.success(wordFromApi))
            } catch (e: Exception) {
                wordResponseItem.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getWordResponse():LiveData<Resource<WordResponseItem>> {
        return wordResponseItem
    }
}