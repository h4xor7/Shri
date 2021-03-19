package com.pandey.shri.ui.main.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.pandey.shri.R
import com.pandey.shri.databinding.FragmentHomeBinding
import com.pandey.shri.ui.main.adapter.CategoryAdapter
import com.pandey.shri.utils.Utils
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var pieEntries: ArrayList<PieEntry>? = null


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


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



        showPieChart()
        setDashboardDate()

    }

    fun setDashboardDate() {
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

            datePickerDialog?.datePicker?.maxDate = c.timeInMillis
            datePickerDialog?.show()
        }





    }


    private fun showPieChart() {
        pieEntries = ArrayList()
        pieEntries?.add(PieEntry(2f, "Fare"))
        pieEntries?.add(PieEntry(4f, "Recharge"))
        pieEntries?.add(PieEntry(2f, "Cloths"))
        pieEntries?.add(PieEntry(2f, "Fast food"))
        pieEntries?.add(PieEntry(2f, "Tuition Fee"))
        pieEntries?.add(PieEntry(2f, "Vegetables"))
        pieEntries?.add(PieEntry(2f, "Electric"))
        pieEntries?.add(PieEntry(2f, "Others"))


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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
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

    }


}