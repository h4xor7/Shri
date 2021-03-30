package com.pandey.shri.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.pandey.shri.data.local.db.ExpenseRoomDatabase
import com.pandey.shri.data.model.Entry
import com.pandey.shri.data.repository.ExpenseRepository
import java.time.OffsetDateTime

class HomeViewModel(application: Application) : AndroidViewModel(application) {



    private val repository: ExpenseRepository

    val allExpense: LiveData<List<Entry>>
  /*  val allRecharge: LiveData<List<Entry>>
    val allCloth: LiveData<List<Entry>>
    val allTuition: LiveData<List<Entry>>
    val allElectric: LiveData<List<Entry>>
    val allFare: LiveData<List<Entry>>
    val allFastFood: LiveData<List<Entry>>
    val allOther: LiveData<List<Entry>>
    val allVegetable: LiveData<List<Entry>>*/



    init {
        val expenseDao = ExpenseRoomDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)


        allExpense = repository.allExpense

    }

    fun getHomeFilterData( fromDateTime: OffsetDateTime,
                       toDateTime: OffsetDateTime):LiveData<List<Entry>>{
        return  repository.filterDatabase(fromDateTime,toDateTime)
    }


    fun allRecharge( fromDateTime: OffsetDateTime,
                           toDateTime: OffsetDateTime):LiveData<List<Entry>>{
        return  repository.allRecharge(fromDateTime,toDateTime)
    }


    fun allCloth( fromDateTime: OffsetDateTime,
                     toDateTime: OffsetDateTime):LiveData<List<Entry>>{
        return  repository.allCloth(fromDateTime,toDateTime)
    }


    fun allTuition( fromDateTime: OffsetDateTime,
                  toDateTime: OffsetDateTime):LiveData<List<Entry>>{
        return  repository.allTuition(fromDateTime,toDateTime)
    }

    fun allElectric( fromDateTime: OffsetDateTime,
                    toDateTime: OffsetDateTime):LiveData<List<Entry>>{
        return  repository.allElectric(fromDateTime,toDateTime)
    }

    fun allFare( fromDateTime: OffsetDateTime,
                     toDateTime: OffsetDateTime):LiveData<List<Entry>>{
        return  repository.allFare(fromDateTime,toDateTime)
    }

    fun allFastFood( fromDateTime: OffsetDateTime,
                 toDateTime: OffsetDateTime):LiveData<List<Entry>>{
        return  repository.allFastFood(fromDateTime,toDateTime)
    }

    fun allOther( fromDateTime: OffsetDateTime,
                     toDateTime: OffsetDateTime):LiveData<List<Entry>>{
        return  repository.allOther(fromDateTime,toDateTime)
    }

    fun allVegetable( fromDateTime: OffsetDateTime,
                  toDateTime: OffsetDateTime):LiveData<List<Entry>>{
        return  repository.allVegetable(fromDateTime,toDateTime)
    }

}