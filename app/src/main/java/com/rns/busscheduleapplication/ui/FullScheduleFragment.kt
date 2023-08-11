package com.rns.busscheduleapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import com.rns.busscheduleapplication.app.BusScheduleApplication
import com.rns.busscheduleapplication.databinding.FullScheduleFragmentBinding
import com.rns.busscheduleapplication.viewModel.BusScheduleViewModel
import com.rns.busscheduleapplication.viewModel.BusScheduleViewModelFactory
import kotlinx.coroutines.launch

class FullScheduleFragment: Fragment() {
    private var _binding: FullScheduleFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BusScheduleViewModel by activityViewModels {
        BusScheduleViewModelFactory(
            (activity?.application as BusScheduleApplication).database.scheduleDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FullScheduleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val busStopAdapter = BusStopAdapter {
            val action = FullScheduleFragmentDirections.actionFullScheduleFragmentToStopScheduleFragment(
                    stopName = it.stopName
                )
            view.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = busStopAdapter

        lifecycle.coroutineScope.launch {
            viewModel.allSchedule().collect() {
                busStopAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
