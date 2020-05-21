package br.com.jdscaram.covid19radar.ui.main

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.jdscaram.covid19radar.databinding.MainFragmentBinding
import br.com.jdscaram.covid19radar.util.LocationUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val mainViewModel by viewModel<MainViewModel>()

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = MainFragmentBinding.inflate(inflater, container, false).apply {
        binding = this
        map.init(this@MainFragment)
        setCurrentLocation()
    }.root

    private fun loadInfo() {
        mainViewModel.loadCountries()
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

    companion object {
        fun newInstance() = MainFragment()
    }
}
