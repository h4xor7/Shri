package com.pandey.shri.ui.main.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.pandey.shri.databinding.FragmentDetailBinding
import com.pandey.shri.ui.main.adapter.NewDetailAdapter
import com.pandey.shri.ui.main.viewmodel.DetailDateViewModel
import com.pandey.shri.ui.main.viewmodel.DetailViewModel
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


    private lateinit var detailViewModel: DetailViewModel
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
        //  detailAdapter = DetailAdapter()
        detailBinding?.rvDetail?.adapter = newDetailAdapter
        //  detailBinding?.rvDetail?.adapter = detailAdapter

        /*
        val mLayoutManager = LinearLayoutManager(view.context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        detailBinding?.rvMonth?.layoutManager = mLayoutManager
        detailBinding?.rvMonth?.adapter = monthAdapter*/
        getMonthByCheckedPosition()

        observeViewModel()

    }


    private fun observeViewModel() {


        /*   val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
               override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                   return activity?.application?.let {
                       DetailViewModel(

                           it
                       )
                   } as T
               }

           }


           detailViewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)

           detailViewModel.allExpense.observe(viewLifecycleOwner, { entry ->

               newDetailAdapter.setExpense(entry)
               Log.d(TAG, "observeViewModel: savedDate ${entry[0].date}")

               Log.d(TAG, "observeViewModel: MyFirstDate ${Utils.getFirstOffSetDateOfMonth(3, 2021)}")

               Log.d(
                   TAG,
                   "observeViewModel: MyLastDate ${Utils.getLastOffsetDateOfMonth(3, 2021, 30)}"
               )


           })
   */

        val detailViewModelFactory = DetailViewModelFactory(
            requireActivity().application,
            Utils.getFirstOffSetDateOfMonth(monthValue, yearValue),
            Utils.getLastOffsetDateOfMonth(
                monthValue, yearValue, Utils.getLastDateOFMonth(monthValue)
            )
        )

        /*    val detailViewModelFactory = DetailViewModelFactory(
                requireActivity().application,
                Utils.getFirstOffSetDateOfMonth(2, 2021),
                Utils.getLastOffsetDateOfMonth(
                    2, yearValue, Utils.getLastDateOFMonth(2)
                )
            )*/

        mdetailViewModel =
            ViewModelProvider(this, detailViewModelFactory).get(DetailDateViewModel::class.java)
/*        mdetailViewModel.dataByMonth.observe(viewLifecycleOwner, { entry ->

            Log.d(
                TAG,
                "observeViewModel: query" + "SELECT * FROM entries WHERE date BETWEEN : ${
                    Utils.getFirstOffSetDateOfMonth(
                        monthValue,
                        yearValue
                    )
                } AND : ${
                    Utils.getLastOffsetDateOfMonth(
                        monthValue,
                        yearValue,
                        Utils.getLastDateOFMonth(monthValue)
                    )
                } ORDER BY date ASC"
            )
            newDetailAdapter.setExpense(entry)

            Log.d(TAG, "observeViewModel: $entry")
        })*/

        observeFilterData()

    }

    private fun observeFilterData() {
        mdetailViewModel.getFilterData(
            Utils.getFirstOffSetDateOfMonth(monthValue, yearValue), Utils.getLastOffsetDateOfMonth(
                monthValue, yearValue, Utils.getLastDateOFMonth(monthValue)
            )
        ).observe(viewLifecycleOwner, { entry ->
            newDetailAdapter.setExpense(entry)

            Log.d(TAG, "observeViewModel: $entry")
        })

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