package com.pandey.shri.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.time.OffsetDateTime

class DetailViewModelFactory(
    private val application: Application, private val startDate: OffsetDateTime,
    private val endDate: OffsetDateTime
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DetailDateViewModel::class.java)) {
            return DetailDateViewModel(application, startDate, endDate) as T

        }
        throw IllegalArgumentException("Unknown class name")

    }


}