package com.pandey.shri.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.time.OffsetDateTime

class DetailViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DetailDateViewModel::class.java)) {
            return DetailDateViewModel(application) as T

        }
        throw IllegalArgumentException("Unknown class name")

    }


}