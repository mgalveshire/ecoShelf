package edu.arizona.cast.mollyalveshire.ecoshelf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val _itemCount = MutableLiveData<Int>(0)
    val itemCount: LiveData<Int> get() = _itemCount

    fun incrementItemCount() {
        _itemCount.value = (_itemCount.value ?: 0) + 1
    }
}
