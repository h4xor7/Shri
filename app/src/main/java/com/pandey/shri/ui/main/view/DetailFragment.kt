package com.pandey.shri.ui.main.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.pandey.shri.databinding.FragmentDetailBinding
import com.pandey.shri.ui.main.adapter.NewDetailAdapter
import com.pandey.shri.ui.main.viewmodel.DetailDateViewModel
import com.pandey.shri.ui.main.viewmodel.DetailViewModelFactory
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


    private lateinit var mdetailViewModel: DetailDateViewModel


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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(view.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        detailBinding?.rvDetail?.layoutManager = layoutManager
        newDetailAdapter = NewDetailAdapter()
        detailBinding?.rvDetail?.adapter = newDetailAdapter
        detailBinding?.rvDetail?.setHasFixedSize(true)
        onSwipeDeleteExpense()


        getMonthByCheckedPosition()

        observeViewModel()

    }

    private fun onSwipeDeleteExpense() {
        val itemCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val expense = newDetailAdapter.getExpenseAtPosition(position)
                mdetailViewModel.deleteExpense(expense)
                newDetailAdapter.notifyItemChanged(position)
                Toast.makeText(context, "${expense.itemName} Deleted", Toast.LENGTH_SHORT).show()

            }
        }

        val itemTouchHelper = ItemTouchHelper(itemCallback)
        itemTouchHelper.attachToRecyclerView(detailBinding?.rvDetail)

    }

    private fun observeViewModel() {

        val detailViewModelFactory = DetailViewModelFactory(
            requireActivity().application)

        mdetailViewModel = ViewModelProvider(this, detailViewModelFactory).get(DetailDateViewModel::class.java)

        observeFilterData()

    }

    private fun observeFilterData() {
        mdetailViewModel.getFilterData(
            Utils.getFirstOffSetDateOfMonth(monthValue, yearValue), Utils.getLastOffsetDateOfMonth(
                monthValue, yearValue, Utils.getLastDateOFMonth(monthValue)
            )
        ).observe(viewLifecycleOwner) { entry ->
            newDetailAdapter.setExpense(entry)

            Log.d(TAG, "observeViewModel: $entry")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        detailBinding = null
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

    private fun getMonthByCheckedPosition() {
        when (Utils.getCurrentMonth()) {
            1 -> {
                detailBinding?.janChip?.isChecked = true
            }
            2 -> {
                detailBinding?.febChip?.isChecked = true
            }
            3 -> {
                detailBinding?.marChip?.isChecked = true
            }
            4 -> {
                detailBinding?.aprChip?.isChecked = true
            }
            5 -> {
                detailBinding?.mayChip?.isChecked = true
            }
            6 -> {
                detailBinding?.junChip?.isChecked = true
            }
            7 -> {
                detailBinding?.julChip?.isChecked = true
            }
            8 -> {
                detailBinding?.augChip?.isChecked = true
            }
            9 -> {
                detailBinding?.sepChip?.isChecked = true
            }
            10 -> {
                detailBinding?.octChip?.isChecked = true
            }
            11 -> {
                detailBinding?.novChip?.isChecked = true
            }
            else -> {
                detailBinding?.decChip?.isChecked = true
            }
        }


        detailBinding?.chipGroup?.setOnCheckedChangeListener { chipGroup, checkedId ->

            when (chipGroup.findViewById<Chip>(checkedId)?.text) {
                "JAN" -> {
                    monthValue = 1
                    observeFilterData()

                }
                "FEB" -> {
                    monthValue = 2
                    observeFilterData()
                }
                "MAR" -> {
                    monthValue = 3
                    observeFilterData()

                }
                "APR" -> {
                    monthValue = 4
                    observeFilterData()

                }
                "MAY" -> {
                    monthValue = 5
                    observeFilterData()

                }
                "JUN" -> {
                    monthValue = 6
                    observeFilterData()

                }
                "JUL" -> {
                    monthValue = 7
                    observeFilterData()

                }
                "AUG" -> {
                    monthValue = 8

                    observeFilterData()

                }
                "SEP" -> {
                    monthValue = 9
                    observeFilterData()

                }
                "OCT" -> {
                    monthValue = 10
                    observeFilterData()

                }
                "NOV" -> {
                    monthValue = 11
                    observeFilterData()

                }
                else -> {
                    monthValue = 12
                    observeFilterData()

                }
            }


        }
    }

}