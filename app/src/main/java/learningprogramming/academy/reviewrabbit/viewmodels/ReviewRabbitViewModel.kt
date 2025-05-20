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
import learningprogramming.academy.reviewrabbit.data.repository.ReviewRabbitRepository
import javax.inject.Inject

@HiltViewModel
class ReviewRabbitViewModel @Inject constructor(
    private val reviewRabbitRepository: ReviewRabbitRepository
): ViewModel() {
    private val _companyFilters = MutableStateFlow(CompanyFilters())
    val companyFilters: StateFlow<CompanyFilters> = _companyFilters.asStateFlow()
    private val _listOfCompanies = MutableStateFlow<List<CompanyApiResponse>>(emptyList())
    val listOfCompanies: StateFlow<List<CompanyApiResponse>> = _listOfCompanies.asStateFlow()

    init {
        loadCompanyFilters()
        getListOfCompanies()
    }

    fun loadCompanyFilters() {
        viewModelScope.launch {
            try {
                _companyFilters.value = reviewRabbitRepository.getCompanyFilters()
            } catch (e: Exception) {
                Log.e("ReviewRabbitViewModel", "Error fetching company filters. $e")
            }
        }
    }

    fun getListOfCompanies() {
        viewModelScope.launch {
            try {
                _listOfCompanies.value = reviewRabbitRepository.getAllCompanies()
            } catch (e: Exception) {
                Log.e("ReviewRabbitViewModel", "Error fetching list of all companies. $e")
            }
        }
    }
}