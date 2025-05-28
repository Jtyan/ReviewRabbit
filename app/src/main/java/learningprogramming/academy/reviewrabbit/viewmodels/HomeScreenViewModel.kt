package learningprogramming.academy.reviewrabbit.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import learningprogramming.academy.reviewrabbit.data.model.CompanyApiResponse
import learningprogramming.academy.reviewrabbit.data.model.CompanyFilters
import learningprogramming.academy.reviewrabbit.data.repository.CompanyRepository
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val companyRepository: CompanyRepository
): ViewModel() {
    private val _companyFilters = MutableStateFlow(CompanyFilters())
    val companyFilters: StateFlow<CompanyFilters> = _companyFilters.asStateFlow()
    private val _listOfCompanies = MutableStateFlow<List<CompanyApiResponse>>(emptyList())
    val listOfCompanies: StateFlow<List<CompanyApiResponse>> = _listOfCompanies.asStateFlow()


    init {
        loadCompanyFilters()
        getListOfCompanies()
    }

    private fun loadCompanyFilters() {
        viewModelScope.launch {
            try {
                _companyFilters.value = companyRepository.getCompanyFilters()
            } catch (e: Exception) {
                Log.e("HomeScreenViewModel", "Error fetching company filters. $e")
            }
        }
    }

    private fun getListOfCompanies() {
        viewModelScope.launch {
            try {
                _listOfCompanies.value = companyRepository.getAllCompanies()
            } catch (e: Exception) {
                Log.e("HomeScreenViewModel", "Error fetching list of all companies. $e")
            }
        }
    }
}