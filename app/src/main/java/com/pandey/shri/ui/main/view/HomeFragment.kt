package com.pandey.shri.ui.main.view

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
import androidx.navigation.Navigation
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.pandey.shri.R
import com.pandey.shri.data.model.PieModel
import com.pandey.shri.databinding.FragmentHomeBinding
import com.pandey.shri.ui.main.viewmodel.HomeViewModel
import com.pandey.shri.utils.HomePrefrences
import com.pandey.shri.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "HomeFragment"


class HomeFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private var monthValue = Utils.getCurrentMonth()
    private var yearValue = Calendar.getInstance().get(Calendar.YEAR);
    private var dateValue = 1
    lateinit var homePerferences: HomePrefrences
     var pieEntries: ArrayList<PieEntry>? = null
    val catlist = listOf<String>("Recharge", "Vegetable", "Cloth","Electric","Fare","Tuition Fee","Fast Food","Other")


    private var grandTotalExpense: Int = 0



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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.fab?.setOnClickListener {

            view.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_homeFragment_to_newEntryFragment)
            }

        }

        homePerferences = context?.let { HomePrefrences(it) }!!


        observeViewModel()

        setDashboardDate()
      //  showPieChart()



    }

    private fun observeViewModel() {

        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.application?.let {
                    HomeViewModel(
                        it
                    )
                } as T
            }
        }


        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        observeFilterData()


    }


    private fun setDashboardDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val defMonth = Utils.getMonthName(month)
        _binding?.txtDate?.text = day.toString()
        _binding?.txtmonth?.text = defMonth

        _binding?.filterDateContainer?.setOnClickListener {
            val datePickerDialog =
                activity?.let { it1 -> DatePickerDialog(it1, this, year, month, day) }

            //yha default data call hoga for current month
            datePickerDialog?.datePicker?.maxDate = c.timeInMillis
            datePickerDialog?.show()

        }


    }

    private fun observeFilterData() {

        //to get all data of this month
        homeViewModel.getFilterData(
            Utils.getFirstOffSetDateOfMonth(monthValue, yearValue),
            Utils.getLastOffsetDateOfMonth(
                monthValue,
                yearValue,
                Utils.getLastDateOFMonth(monthValue)
            )
        ).observe(viewLifecycleOwner) { entry ->
           
            Utils.grandTotal(entry)
            getAllExpense(Utils.grandTotal(entry))

        }
    }


    private fun getAllExpense(grandTotal: Int) {
        _binding?.txtTotalExpenseValue?.text = grandTotal.toString()

        grandTotalExpense = grandTotal

        CoroutineScope(Dispatchers.Main).launch {
            doPieChartOperations()

        }



    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val strMonth = Utils.getMonthName(month)
        _binding?.txtDate?.text = dayOfMonth.toString()
        _binding?.txtmonth?.text = strMonth

        //  yha filter wala data  call hoga
        dateValue = dayOfMonth
        yearValue = year
        monthValue = month + 1

        observeFilterData()


    }

    fun calculatePercentage(grandTotal: Int, categoryTotal: Int): Float {

        val percentage = (categoryTotal / grandTotalExpense) * 100
        return percentage.toFloat()

    }

    private suspend fun doPieChartOperations() {
         val pieArr: MutableList<PieModel>  = mutableListOf()

       val job = CoroutineScope(Dispatchers.IO).launch{

           for (categoryName in catlist){
            val listEntry =   homeViewModel.getDataByCategory(
                   categoryName,
                   Utils.getFirstOffSetDateOfMonth(monthValue, yearValue),
                   Utils.getLastOffsetDateOfMonth(
                       monthValue,
                       yearValue,
                       Utils.getLastDateOFMonth(monthValue)
                   )
               )

               val catTotal = Utils.grandTotal(listEntry)
               Log.d(TAG, "doPieChartOperations: ${catTotal} $categoryName")
               if (catTotal!=0){
                   pieArr.add(PieModel(categoryName,catTotal))
               }


           }
       }

        job.join()
        Log.d(TAG, "doPieChartOperations: Pie Arr Size ${pieArr.size} ")
        showPieChart(pieArr)

    }



    private fun showPieChart(pieArr:MutableList<PieModel>) {


        pieEntries = ArrayList()

        for (category in pieArr){
            pieEntries?.add(PieEntry(category.categoryTotal.toFloat(),category.categoryName))
        }

        val dataSet = PieDataSet(pieEntries, "What ever you want")

        val data = PieData(dataSet)
        dataSet.valueTextSize = 12f

        //PieData(year, dataSet)
        _binding?.pieChart?.data = data
        _binding?.pieChart?.description?.isEnabled = false
        //  _binding?.pieChart?.isDrawHoleEnabled = false
        val chartColorList = mutableListOf<Int>()
        chartColorList.add(ColorTemplate.rgb("#dc1b59"))
        chartColorList.add(ColorTemplate.rgb("#81a3f0"))
        chartColorList.add(ColorTemplate.rgb("#c28efa"))
        chartColorList.add(ColorTemplate.rgb("#81d8f0"))
        chartColorList.add(ColorTemplate.rgb("#8dba57"))
        dataSet.setColors(chartColorList)

        _binding?.pieChart?.animateXY(2000, 2000)


    }






}