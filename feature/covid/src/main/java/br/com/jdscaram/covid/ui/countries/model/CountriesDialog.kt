package br.com.jdscaram.covid.ui.countries.model

import android.app.Dialog
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import br.com.jdscaram.core.model.CountryUiModel
import br.com.jdscaram.covid.databinding.CountriesDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class CountriesDialog : BottomSheetDialogFragment(), CountryListener {

    private val countriesViewModel by viewModel<CountriesViewModel>()
    private val countriesAdapter = CountriesAdapter(this)
    private lateinit var binding: CountriesDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            behavior.isHideable = false

            window?.windowManager?.defaultDisplay?.let {
                val size = Point()
                it.getSize(size)
                val middleOfScreen = size.y / 2
                behavior.peekHeight = middleOfScreen
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = CountriesDialogBinding.inflate(inflater, container, false).apply {
        lifecycleOwner = this@CountriesDialog
        binding = this
        viewModel = countriesViewModel
        loadCountries()
        initObservers()
    }.root


    private fun initObservers() {
        lifecycleScope.launch {
            countriesViewModel.viewAction.collect { value ->
                when (value) {
                    is CountriesViewModel.ViewAction.ShowLoading -> {
                        binding.progress.visibility = View.VISIBLE
                    }
                    is CountriesViewModel.ViewAction.HideLoading -> {
                        binding.progress.visibility = View.GONE
                    }
                    is CountriesViewModel.ViewAction.SetData -> {
                        binding.notFound.root.visibility = View.GONE
                        initAdapter(value.countriesUiModel)
                    }
                    is CountriesViewModel.ViewAction.ShowErrorMsg -> {
                        binding.notFound.root.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun initAdapter(countriesUiModel: List<CountryUiModel>) {
        binding.countries.adapter = countriesAdapter
        countriesAdapter.updateData(countriesUiModel)
    }

    private fun loadCountries() {
        countriesViewModel.loadCountries()
    }

    companion object {
        fun showCountriesDialog(fragmentManager: FragmentManager) =
            CountriesDialog().show(fragmentManager, CountriesDialog::class.java.name)
    }

    override fun onCountryClick(item: CountryUiModel) {
        Toast.makeText(context, item.country, Toast.LENGTH_LONG).show()
        dismiss()
    }
}