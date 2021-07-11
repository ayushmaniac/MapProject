package com.ridecell.maps.ui.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ridecell.maps.data.local.repo.UserRepository
import com.ridecell.maps.data.remote.response.GeneralResponse
import com.ridecell.maps.data.remote.response.PasswordRequirementsResponse
import com.ridecell.maps.data.remote.response.UserResponse
import com.ridecell.maps.ui.base.BaseViewModel
import com.ridecell.maps.utils.common.*
import com.ridecell.maps.utils.network.NetworkError

class ForgotPasswordViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    val emailField: MutableLiveData<String> = MutableLiveData()
    private val validationList: MutableLiveData<List<Validation>> = MutableLiveData()
    val emailValidation: LiveData<ValidationResource<Int>> =
        filterValidation(Validation.Field.EMAIL)

    fun onEmailChanged(email: String) = emailField.postValue(email)


    private fun filterValidation(string: Validation.Field) = Transformations.map(validationList) {
        it.find { validation -> validation.field == string }
            ?.run { return@run this.resource }
            ?: ValidationResource.unknown()
    }

    fun resetPassword(): MutableLiveData<Resource<GeneralResponse>> {
        val email = emailField.value
        val validations = Validator.validateOnlyEmail(email)
        validationList.postValue(validations)
        var liveData = MutableLiveData<Resource<GeneralResponse>>()
        if (validations.isNotEmpty() && email != null) {
            val successfulValidation =
                validations.filter { it.resource.status == ValidationStatus.SUCCESS }
            if (successfulValidation.size == validations.size) {
                liveData = userRepository.resetUserPassword(email) as MutableLiveData<Resource<GeneralResponse>>
            }
            else
            {
                liveData.postValue(Resource.error(
                    "Something went wrong",
                    NetworkError(
                        false,
                        400,
                        "Something went wrong"
                    )
                ))
            }

        }
        return liveData
    }
}