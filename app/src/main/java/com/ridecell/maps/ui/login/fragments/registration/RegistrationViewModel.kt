package com.ridecell.maps.ui.login.fragments.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ridecell.maps.data.local.repo.UserRepository
import com.ridecell.maps.data.remote.request.SignupRequestBody
import com.ridecell.maps.data.remote.response.UserResponse
import com.ridecell.maps.ui.base.BaseViewModel
import com.ridecell.maps.utils.common.*
import com.ridecell.maps.utils.network.NetworkError

class RegistrationViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    private val validationList: MutableLiveData<List<Validation>> = MutableLiveData()
    val nameField : MutableLiveData<String> = MutableLiveData()
    val emailField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()
    val nameValidation : LiveData<ValidationResource<Int>> = filterValidation(Validation.Field.NAME)
    val emailValidation: LiveData<ValidationResource<Int>> =
        filterValidation(Validation.Field.EMAIL)
    val passwordValidation: LiveData<ValidationResource<Int>> =
        filterValidation(Validation.Field.PASSWORD)


    private fun filterValidation(string: Validation.Field) = Transformations.map(validationList) {
        it.find { validation -> validation.field == string }
            ?.run { return@run this.resource }
            ?: ValidationResource.unknown()
    }

    fun onNameChanged(name : String) = nameField.postValue(name)
    fun onEmailChanged(email: String) = emailField.postValue(email)
    fun onPasswordChanged(password: String) = passwordField.postValue(password)

    fun onSignup(): LiveData<Resource<UserResponse>> {
        var liveData = MutableLiveData<Resource<UserResponse>>()
        val name = nameField.value
        val email = emailField.value
        val password = passwordField.value

        val validations = Validator.validateSingupFields(email, password, name)
        validationList.postValue(validations)

         if (validations.isNotEmpty() && email != null && password != null) {
            val successfulValidation = validations.filter { it.resource.status == ValidationStatus.SUCCESS }
            if (successfulValidation.size == validations.size) {
               liveData = userRepository.doSignup(getRequestBody(email,password, name)) as MutableLiveData<Resource<UserResponse>>
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

    private fun getRequestBody(email: String, password: String, name: String?): SignupRequestBody {
        return SignupRequestBody(
            name, email, password
        )
    }

    fun saveUser(data: UserResponse?) {
        userRepository.saveUser(data)
    }
}