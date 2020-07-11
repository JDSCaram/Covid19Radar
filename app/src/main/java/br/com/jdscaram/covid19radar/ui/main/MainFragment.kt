package br.com.jdscaram.covid19radar.ui.main

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.jdscaram.covid.ui.countries.model.CountriesDialog
import br.com.jdscaram.covid19radar.databinding.MainFragmentBinding
import br.com.jdscaram.covid19radar.util.AnimationUtils
import br.com.jdscaram.covid19radar.util.LocationUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val mainViewModel by viewModel<MainViewModel>()

    private lateinit var binding: MainFragmentBinding
    private var isFabRotate = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = MainFragmentBinding.inflate(inflater, container, false).apply {
        binding = this
        map.init(this@MainFragment)
        setCurrentLocation()
        initAnimations(this)
        initClickListeners(this)
    }.root

    private fun initClickListeners(binding: MainFragmentBinding) {
        with(binding) {
            fab.setOnClickListener {
                isFabRotate = AnimationUtils.rotateFab(it, !isFabRotate)
                if (isFabRotate) {
                    AnimationUtils.showIn(country)
                } else {
                    AnimationUtils.showOut(country)
                }
            }
            country.setOnClickListener {
                CountriesDialog.showCountriesDialog(childFragmentManager)
            }
        }
    }


    private fun setCurrentLocation() {
        val locationManager =
            context?.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        val location =
            LocationUtils.getMyLocation(locationManager)

        location?.let {
            binding.map.setCurrentLocation(location)
        }
    }

    private fun initAnimations(binding: MainFragmentBinding) {
        AnimationUtils.init(binding.country)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}