package br.com.jdscaram.webservice.model.mapper

import br.com.jdscaram.core.mapper.Mapper
import br.com.jdscaram.core.model.CountryUiModel
import br.com.jdscaram.webservice.model.CountryResponse

class CountryResponseToUiModelMapper : Mapper<List<CountryResponse>?, List<CountryUiModel>> {
    override fun mapFrom(from: List<CountryResponse>?): List<CountryUiModel> = from?.map {
        CountryUiModel(country = it.country, slug = it.slug)
    }?.sortedBy {
        it.country
    } ?: emptyList()
}
