package br.com.jdscaram.covid.ui.countries.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.jdscaram.core.model.CountryUiModel
import br.com.jdscaram.covid.databinding.CountryItemBinding

class CountriesAdapter(private val listener: CountryListener): RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {

    private var data = mutableListOf<CountryUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryViewHolder(
            CountryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    fun updateData(newData: List<CountryUiModel>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class CountryViewHolder(private val binding: CountryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CountryUiModel, listener: CountryListener) {
            binding.countryName.text = model.country
            binding.root.setOnClickListener {
                listener.onCountryClick(model)
            }
        }
    }
}

interface CountryListener {
    fun onCountryClick(item: CountryUiModel)
}