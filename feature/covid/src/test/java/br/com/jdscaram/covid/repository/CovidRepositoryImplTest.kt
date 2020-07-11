package br.com.jdscaram.covid.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.jdscaram.core.CoroutinesTestRule
import br.com.jdscaram.webservice.api.CovidApi
import br.com.jdscaram.core.DispatcherProvider
import br.com.jdscaram.webservice.core.Webservice
import br.com.jdscaram.webservice.model.CountryResponse
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*

import retrofit2.Call
import java.lang.Exception

@ExperimentalCoroutinesApi
class CovidRepositoryImplTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val webservice = mockk<Webservice>()
    private val testDispatcher = TestCoroutineDispatcher()
    private val request = mockk<CovidApi>()
    private lateinit var covidRepository: CovidRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getCovidByCountry_returnSuccess() = runBlockingTest {
        val responses = listOf(CountryResponse("Brazil", "BR", "bra"))
        prepareScenario(responses, true)

        val result = covidRepository.getCovidByCountry()

        Assert.assertTrue(result.isSuccess)
        Assert.assertEquals("Brazil", result.getOrNull()?.first()?.country)
    }

    @Test
    fun getCovidByCountry_returnError() = runBlockingTest {
        prepareScenario()

        val result = covidRepository.getCovidByCountry()

        Assert.assertTrue(result.isFailure)
    }

    private fun prepareScenario(
        countriesResponse: List<CountryResponse> = emptyList(),
        isSuccess: Boolean = false
    ) {
        every { request.getCountries() } returns mockk()
        every { webservice.createRequest<CovidApi>(any()) } returns request

        every {
            webservice.executeRequest<List<CountryResponse>>(any())
        } returns if (isSuccess) Result.success(
            countriesResponse
        ) else Result.failure(Exception())

        covidRepository = CovidRepositoryImpl(webservice, coroutinesTestRule.testDispatcherProvider)
    }
}