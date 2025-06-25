package learningprogramming.academy.reviewrabbit.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import learningprogramming.academy.reviewrabbit.data.company.model.CompanyResponse
import learningprogramming.academy.reviewrabbit.data.company.model.CompanyFilters
import learningprogramming.academy.reviewrabbit.data.company.CompanyApiResult
import learningprogramming.academy.reviewrabbit.data.company.CompanyRepository
import learningprogramming.academy.reviewrabbit.model.FilterTabs
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val companyRepository: CompanyRepository
) : ViewModel() {
    private val _companyFilters = MutableStateFlow(CompanyFilters())
    val companyFilters: StateFlow<CompanyFilters> = _companyFilters.asStateFlow()
    private val _listOfCompanies = MutableStateFlow<List<CompanyResponse>>(emptyList())
    val listOfCompanies: StateFlow<List<CompanyResponse>> = _listOfCompanies.asStateFlow()
    private val _listOfFilters = MutableStateFlow(CompanyFilters())
    val listOfFilters: StateFlow<CompanyFilters> = _listOfFilters.asStateFlow()


    init {
        loadCompanyFilters()
        getListOfEveryCompanies()
    }

    private fun loadCompanyFilters() {
        viewModelScope.launch {
            try {
                _companyFilters.value = companyRepository.getCompanyFilters()
                Log.i("HomeScreenViewModel", _companyFilters.value.toString())
            } catch (e: Exception) {
                Log.e("HomeScreenViewModel", "Error fetching company filters. $e")
            }
        }
    }

    private fun getListOfEveryCompanies() {
        viewModelScope.launch {
            try {
                _listOfCompanies.value = companyRepository.getAllCompanies()
            } catch (e: Exception) {
                Log.e("HomeScreenViewModel", "Error fetching list of all companies. $e")
            }
        }
    }

    fun getListOfFilteredCompanies(companyFilters: CompanyFilters) {
        viewModelScope.launch {
            try {
                Log.i("HomeScreenViewModel", companyFilters.toString())
                val response = companyRepository.getFilteredCompaniesList(companyFilters)
                if (response is CompanyApiResult.GetFilteredCompaniesSuccess) {
                    _listOfCompanies.value = response.filteredCompanies
                    Log.i("HomeScreenViewModel", "list of companies : ${listOfCompanies.value}")
                }
            } catch (e: Exception) {
                Log.e("HomeScreenViewModel", "Error fetching list of filtered companies. $e")
            }
        }
    }

    fun toggleFilter(item: String, category: FilterTabs) {
        val currentFilters = _listOfFilters.value

        val newFilters = when (category) {
            FilterTabs.LOCATIONS -> {
                val newList = currentFilters.locations.toMutableList().apply {
                    if (contains(item)) remove(item) else add(item)
                }
                currentFilters.copy(locations = newList)
            }
            FilterTabs.INDUSTRIES -> {
                val newList = currentFilters.industries.toMutableList().apply {
                    if (contains(item)) remove(item) else add(item)
                }
                currentFilters.copy(industries = newList)
            }

            FilterTabs.COUNTRIES -> {
                val newList = currentFilters.countries.toMutableList().apply {
                    if (contains(item)) remove(item) else add(item)
                }
                currentFilters.copy(countries = newList)
            }

            FilterTabs.TAGS -> {
                val newList = currentFilters.tags.toMutableList().apply {
                    if (contains(item)) remove(item) else add(item)
                }
                currentFilters.copy(tags = newList)
            }
        }
        _listOfFilters.value = newFilters
    }
}