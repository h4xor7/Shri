package com.pandey.shri.ui.main.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.pandey.shri.R
import com.pandey.shri.data.model.CategoryModel
import com.pandey.shri.data.model.Entry
import com.pandey.shri.databinding.FragmentNewEntryBinding
import com.pandey.shri.ui.main.adapter.CategoryAdapter
import com.pandey.shri.ui.main.viewmodel.NewEntryViewModel
import com.pandey.shri.utils.Constant
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


class NewEntryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var entryBinding: FragmentNewEntryBinding? = null
    private val binding get() = entryBinding!!
    private val categoryAdapter = CategoryAdapter()
    private  var longDate: Long? =null
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
    ): View? {
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
        longDate = c.timeInMillis
        val defMonth = Utils.getMonthName(month)

        entryBinding?.txtFilterDate?.text = "$day $defMonth $year"
        entryBinding?.txtFilterDate?.setOnClickListener {

           /* val datePickerDialog =
                activity?.let { it1 -> DatePickerDialog(it1, this, year, month, day) }

            datePickerDialog?.datePicker?.maxDate = c.timeInMillis
            datePickerDialog?.show()*/
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setTheme(R.style.ThemeOverlay_App_DatePicker)
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.show(activity?.supportFragmentManager!!,"DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                entryBinding?.txtFilterDate?.text = datePicker.headerText
                longDate = datePicker.selection
                Toast.makeText(context, "${datePicker.selection}", Toast.LENGTH_SHORT).show()
            }

            datePicker.addOnNegativeButtonClickListener { datePicker.dismiss()

            }





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

        categoryList.add(CategoryModel(Constant.RECHARGE, R.drawable.ic_cell_phone))
        categoryList.add(CategoryModel(Constant.VEGETABLES, R.drawable.ic_vegetables))
        categoryList.add(CategoryModel(Constant.CLOTH, R.drawable.ic_cloths))
        categoryList.add(CategoryModel(Constant.ELECTRIC, R.drawable.ic_electrical_appliances))
        categoryList.add(CategoryModel(Constant.FARE, R.drawable.ic_cab))
        categoryList.add(CategoryModel(Constant.TUITION, R.drawable.ic_open_book))
        categoryList.add(CategoryModel(Constant.FAST_FOOD, R.drawable.ic_fast_food))
        categoryList.add(CategoryModel(Constant.OTHER, R.drawable.ic_rupee))

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

    }


    private fun saveData() {
        val itemPrice = Integer.parseInt(entryBinding?.edtItemPrice?.text.toString())
        val itemName = entryBinding?.edtItemName?.text.toString()
        val categoryName = entryBinding?.txtSelected?.text.toString()
        val longTime = longDate


        val entry = longTime?.let { Entry(it, categoryName, itemName, itemPrice) }

        if (entry != null) {
            newEntryViewModel.insert(entry)
        }

        resetToDefault()


    }


    override fun onDestroyView() {
        super.onDestroyView()
        entryBinding = null;
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


}