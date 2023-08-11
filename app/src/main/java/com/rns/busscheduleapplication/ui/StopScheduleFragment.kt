package com.rns.busscheduleapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.rns.busscheduleapplication.app.BusScheduleApplication
import com.rns.busscheduleapplication.databinding.StopScheduleFragmentBinding
import com.rns.busscheduleapplication.viewModel.BusScheduleViewModel
import com.rns.busscheduleapplication.viewModel.BusScheduleViewModelFactory
import kotlinx.coroutines.launch

class StopScheduleFragment : Fragment() {
    companion object {
        var STOP_NAME = "stopName"
    }

    private var _binding: StopScheduleFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var stopName: String

    private val viewModel: BusScheduleViewModel by activityViewModels {
        BusScheduleViewModelFactory(
            (activity?.application as BusScheduleApplication).database.scheduleDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            stopName = it.getString(STOP_NAME).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = StopScheduleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val busStopAdapter = BusStopAdapter {}

        binding.recyclerView.adapter = busStopAdapter
        lifecycle.coroutineScope.launch {
            viewModel.scheduleForStopName(stopName).collect() {
                busStopAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
