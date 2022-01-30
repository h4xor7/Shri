package com.pandey.shri.ui.main.view


import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.pandey.shri.R
import com.pandey.shri.databinding.FragmentDetailBinding
import com.pandey.shri.ui.main.adapter.NewDetailAdapter
import com.pandey.shri.ui.main.viewmodel.DetailViewModel
import com.pandey.shri.ui.main.viewmodel.DetailViewModelFactory
import com.pandey.shri.ui.main.viewmodel.NewEntryViewModel
import com.pandey.shri.utils.Utils
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "DetailFragment"


class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var detailBinding: FragmentDetailBinding? = null
    private val binding get() = detailBinding!!
    private var monthValue = Utils.getCurrentMonth()
    var counter: Int = 0
    private var yearValue = Calendar.getInstance().get(Calendar.YEAR);


    private lateinit var detailViewModel: DetailViewModel


    lateinit var newDetailAdapter: NewDetailAdapter

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

        detailBinding = FragmentDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(view.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        detailBinding?.rvDetail?.layoutManager = layoutManager
        newDetailAdapter = NewDetailAdapter()
        detailBinding?.rvDetail?.adapter = newDetailAdapter



        observeViewModel()

    }


    private fun observeViewModel() {

        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.application?.let {
                    DetailViewModel(
                        it
                    )
                } as T
            }

        }

        detailViewModel = ViewModelProvider(this,factory).get(DetailViewModel::class.java)

        /*detailViewModel.getDataByDate(MaterialDatePicker.thisMonthInUtcMilliseconds(),
            MaterialDatePicker.todayInUtcMilliseconds()).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                entries -> newDetailAdapter.setExpense(entries)
            Log.d(TAG, "observeViewModel:  entries $entries")

        })*/


        detailViewModel.allExpense.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                entries -> newDetailAdapter.setExpense(entries)

        })



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_menu,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.menu_date_picker -> {
                showDateRangePicker()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detailBinding = null
    }

    private fun showDateRangePicker(){
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .setSelection(
                  Pair(   MaterialDatePicker.thisMonthInUtcMilliseconds(),
                      MaterialDatePicker.todayInUtcMilliseconds())
                )
                .build()

        dateRangePicker.show(activity?.supportFragmentManager!!,"Date Range Tag")

        dateRangePicker.addOnPositiveButtonClickListener {

            it.first?.let { it1 ->
                it.second?.let { it2 ->
                    detailViewModel.getDataByDate(
                        it1,
                        it2
                    ).observe(viewLifecycleOwner, androidx.lifecycle.Observer { entries -> newDetailAdapter.setExpense(entries)
                        Log.d(TAG, "observeViewModel:  entries $entries")

                    })
                }
            }
            detailBinding?.tvDateRange?.visibility  = View.VISIBLE
            detailBinding?.tvDateRange?.text = dateRangePicker.headerText

        }

        dateRangePicker.addOnNegativeButtonClickListener { dateRangePicker.dismiss() }

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}