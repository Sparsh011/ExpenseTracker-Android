package com.sparshchadha.expensetracker.core.domain

sealed class Resource<T>(val data: T? = null, val error: Throwable? = null){
    class Success<T>(data: T? = null): Resource<T>(data)
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Error<T>(data: T? = null, error: Throwable? = null): Resource<T>(data, error)
}

//
//// Generic update method in repository
//class UserRepository @Inject constructor(
//    private val api: ApiService,
//    private val dataStorePreference: DataStorePreference
//) {
//    suspend fun <T> updateUserField(
//        field: UserField,
//        newValue: T,
//        accessToken: String
//    ): Flow<Resource<FieldUpdateResult<T>>> = flow {
//        emit(Resource.Loading())
//
//        try {
//            val response = api.updateUserField(
//                authorization = "Bearer $accessToken",
//                field = field.apiPath,
//                value = newValue
//            )
//
//            if (response.isSuccessful) {
//                when (field) {
//                    UserField.NAME -> dataStorePreference.saveUserName(newValue as String)
//                    UserField.EMAIL -> dataStorePreference.saveUserEmail(newValue as String)
//                    UserField.EXPENSE_BUDGET -> dataStorePreference.saveExpenseBudget(newValue as Double)
//                    // Handle other fields as needed
//                }
//                emit(Resource.Success(FieldUpdateResult(
//                    isSuccess = true,
//                    field = field.name,
//                    newValue = newValue,
//                    errorMessage = null
//                )))
//            } else {
//                emit(Resource.Error(FieldUpdateResult(
//                    isSuccess = false,
//                    field = field.name,
//                    newValue = null,
//                    errorMessage = handleErrorResponse(response)
//                )))
//            }
//        } catch (e: Exception) {
//            emit(Resource.Error(FieldUpdateResult(
//                isSuccess = false,
//                field = field.name,
//                newValue = null,
//                errorMessage = e.localizedMessage ?: "An unexpected error occurred"
//            )))
//        }
//    }
//
//    private fun handleErrorResponse(response: Response<*>): String {
//        return when (response.code()) {
//            400 -> "Invalid format for ${field.name}"
//            401 -> "Unauthorized. Please log in again."
//            403 -> "You don't have permission to change this field"
//            else -> response.errorBody()?.string() ?: "Unable to update. Please try again."
//        }
//    }
//}
//
//// Usage in ViewModel
//class ProfileViewModel @Inject constructor(
//    private val userRepository: UserRepository
//) : ViewModel() {
//
//    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
//    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
//
//    fun updateUserField<T>(field: UserField, newValue: T, accessToken: String) {
//        viewModelScope.launch {
//            userRepository.updateUserField(field, newValue, accessToken).collect { result ->
//                _uiState.value = when (result) {
//                    is Resource.Loading -> ProfileUiState.Loading
//                    is Resource.Success -> {
//                        val data = result.data
//                        if (data.isSuccess) {
//                            ProfileUiState.FieldUpdateSuccess(data.field, data.newValue)
//                        } else {
//                            ProfileUiState.Error(data.errorMessage ?: "Update failed")
//                        }
//                    }
//                    is Resource.Error -> {
//                        ProfileUiState.Error(result.data?.errorMessage ?: "Update failed")
//                    }
//                }
//            }
//        }
//    }
//}
//
//// Usage in UI
//viewModel.updateUserField(UserField.NAME, "New Name", accessToken)
//viewModel.updateUserField(UserField.EXPENSE_BUDGET, 1000.0, accessToken)