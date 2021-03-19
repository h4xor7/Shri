package com.pandey.shri.ui.main.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandey.shri.databinding.FragmentDetailBinding
import com.pandey.shri.ui.main.adapter.DetailAdapter
import com.pandey.shri.ui.main.adapter.MonthAdapter
import com.pandey.shri.ui.main.viewmodel.DetailViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var detailBinding: FragmentDetailBinding? = null
    private val binding get() = detailBinding!!

    private var detailAdapter = DetailAdapter()
    private var monthAdapter = MonthAdapter()
    private lateinit var detailViewModel: DetailViewModel


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
        detailBinding?.rvDetail?.adapter = detailAdapter

/*
        val mLayoutManager = LinearLayoutManager(view.context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        detailBinding?.rvMonth?.layoutManager = mLayoutManager
        detailBinding?.rvMonth?.adapter = monthAdapter*/

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
        detailViewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)

        detailViewModel.allExpense.observe(viewLifecycleOwner, { entry ->


            if (!entry.isEmpty()) {
                entry?.let {

                    detailAdapter.addData(it)
                }
            }

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
}