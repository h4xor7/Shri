package com.pandey.shri.ui.main.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.pandey.shri.R
import com.pandey.shri.data.model.CategoryModel
import com.pandey.shri.data.model.Entry
import com.pandey.shri.databinding.FragmentNewEntryBinding
import com.pandey.shri.ui.main.adapter.CategoryAdapter
import com.pandey.shri.ui.main.viewmodel.NewEntryViewModel
import com.pandey.shri.utils.Utils
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "NewEntryFragment"


class NewEntryFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var entryBinding: FragmentNewEntryBinding? = null
    private val binding get() = entryBinding!!
    private val categoryAdapter = CategoryAdapter()
    private var backOffsetDateTime:OffsetDateTime? =null

    private lateinit var newEntryViewModel: NewEntryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        entryBinding = FragmentNewEntryBinding.inflate(inflater, container, false)


        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val defMonth = Utils.getMonthName(month)
        backOffsetDateTime = OffsetDateTime.now(ZoneId.systemDefault())

        val datePickerDialog =
            activity?.let { it1 -> DatePickerDialog(it1, this, year, month, day) }

        datePickerDialog?.datePicker?.maxDate = c.timeInMillis


        entryBinding?.txtFilterDate?.text = "$day $defMonth $year"
        entryBinding?.txtFilterDate?.setOnClickListener {

            datePickerDialog?.show()
        }

        val layoutManager = LinearLayoutManager(view.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        entryBinding?.rvCategory?.layoutManager = layoutManager
        addCategory()

        observeViewModel()

        entryBinding?.saveFab?.setOnClickListener {
            saveData()
        }
    }


    private fun addCategory() {
        val categoryList: ArrayList<CategoryModel> = ArrayList<CategoryModel>()

        categoryList.add(CategoryModel("Recharge", R.drawable.ic_cell_phone))
        categoryList.add(CategoryModel("Vegetable", R.drawable.ic_vegetables))
        categoryList.add(CategoryModel("Cloth", R.drawable.ic_cloths))
        categoryList.add(CategoryModel("Electric", R.drawable.ic_electrical_appliances))
        categoryList.add(CategoryModel("Fare", R.drawable.ic_cab))
        categoryList.add(CategoryModel("Tuition Fee", R.drawable.ic_open_book))
        categoryList.add(CategoryModel("Fast Food", R.drawable.ic_fast_food))
        categoryList.add(CategoryModel("Other", R.drawable.ic_rupee))

        categoryAdapter.addData(categoryList)
        entryBinding?.rvCategory?.adapter = categoryAdapter

        categoryAdapter.setEventlistener(object : CategoryAdapter.EventListener {
            override fun onItemClick(
                data: ArrayList<CategoryModel>,
                holder: CategoryAdapter.CategoryViewHolder,
                position: Int
            ) {
                entryBinding?.txtSelected?.text = data[position].categoryName
                entryBinding?.imgCategorySelected?.setImageResource(data[position].imageResourceId)
            }

        })


    }


    private fun observeViewModel() {

        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.application?.let {
                    NewEntryViewModel(

                        it
                    )
                } as T
            }

        }
        newEntryViewModel = ViewModelProvider(this, factory).get(NewEntryViewModel::class.java)

/*
        newEntryViewModel.allExpense.observe(viewLifecycleOwner, { entry ->


            if (!entry.isEmpty()) {
                entry?.let {
                    Toast.makeText(context, it[0].itemName, Toast.LENGTH_SHORT).show()
                }
            }

        })
*/

    }


    private fun saveData() {
        val itemPrice = Integer.parseInt(entryBinding?.edtItemPrice?.text.toString())
        val itemName = entryBinding?.edtItemName?.text.toString()
        val categoryName = entryBinding?.txtSelected?.text.toString()
      //  val offsetDateTime = OffsetDateTime.of(2021, 2, 18, 0, 0, 0, 0, ZoneOffset.UTC)


        Log.d(TAG, "saveData:$backOffsetDateTime ")

        val entry = backOffsetDateTime?.let { Entry(it, categoryName, itemName, itemPrice) }

        entry?.let { newEntryViewModel.insert(it) }

        resetToDefault()


    }


    override fun onDestroyView() {
        super.onDestroyView()
        entryBinding = null
    }

    private fun resetToDefault() {

        entryBinding?.parentContainer?.let {
            Snackbar.make(it, "Successfully Saved", Snackbar.LENGTH_SHORT).show()

            entryBinding?.edtItemName?.setText("")
            entryBinding?.edtItemPrice?.setText("")
            entryBinding?.imgCategorySelected?.setImageResource(R.drawable.ic_rupee)
            entryBinding?.txtSelected?.text ="Other"
            entryBinding?.edtItemName?.requestFocus()
        }


    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewEntryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        Log.d(TAG, "onDateSet: $dayOfMonth $month $year")
        val exactMonth =month+1
        showTimePicker( year, exactMonth, dayOfMonth)

        val strMonth = Utils.getMonthName(month)
        entryBinding?.txtFilterDate?.text = "$dayOfMonth $strMonth $year"
    }


    private fun showTimePicker(year: Int, month: Int, dayOfMonth: Int) {


        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(10)
                .setTitleText("Select Time")
                .build()

        picker.addOnPositiveButtonClickListener {
            val hour = picker.hour
            val minute = picker.minute
            val time = "$hour:$minute"

            Log.d(TAG, "showTimePicker: $time")
             backOffsetDateTime = OffsetDateTime.of(year, month, dayOfMonth, 0, 0, 0, 0, ZoneOffset.UTC)
        }

        picker.addOnCancelListener {
            picker.dismiss()
        }

        picker.addOnNegativeButtonClickListener {
            picker.dismiss()
        }
        picker.addOnDismissListener {
            picker.dismiss()
        }


        activity?.supportFragmentManager?.let { picker.show(it, picker.tag) }
    }




}