package com.ridecell.maps.ui.login.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ridecell.maps.data.local.repo.UserRepository
import com.ridecell.maps.data.remote.request.LoginRequestBody
import com.ridecell.maps.data.remote.response.PasswordRequirementsResponse
import com.ridecell.maps.data.remote.response.UserResponse
import com.ridecell.maps.ui.base.BaseViewModel
import com.ridecell.maps.utils.common.*
import com.ridecell.maps.utils.network.NetworkError

class LoginViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    private val validationList: MutableLiveData<List<Validation>> = MutableLiveData()
    val emailField: MutableLiveData<String> = MutableLiveData()
    val passwordField: MutableLiveData<String> = MutableLiveData()
    val emailValidation: LiveData<ValidationResource<Int>> =
        filterValidation(Validation.Field.EMAIL)
    val passwordValidation: LiveData<ValidationResource<Int>> =
        filterValidation(Validation.Field.PASSWORD)

     var passwordRequirements : PasswordRequirementsResponse? = null

    fun getPasswordValidations() = Transformations.map(userRepository.getPasswordValidation()){
        it.data
    }

    private fun filterValidation(string: Validation.Field) = Transformations.map(validationList) {
        it.find { validation -> validation.field == string }
            ?.run { return@run this.resource }
            ?: ValidationResource.unknown()
    }

    fun setRequirements(requirements: PasswordRequirementsResponse){
        passwordRequirements = requirements
    }

    fun onEmailChanged(email: String) = emailField.postValue(email)
    fun onPasswordChanged(password: String) = passwordField.postValue(password)


    fun onLogin(): LiveData<Resource<UserResponse>> {
        val email = emailField.value
        val password = passwordField.value
        var liveData = MutableLiveData<Resource<UserResponse>>()
        var validations : List<Validation>? = null
        validations = if(passwordRequirements!= null){
            Validator.validateFullLoginFields(email, password)

        } else {
            Validator.validateLoginFields(email, password)

        }
        validationList.postValue(validations)
        if (validations?.isNotEmpty()!! && email != null && password != null) {
            val successfulValidation =
                validations.filter { it.resource.status == ValidationStatus.SUCCESS }
            if (successfulValidation.size == validations.size) {
                liveData = userRepository.doLogin(getLoginRequestBody(email, password)) as MutableLiveData<Resource<UserResponse>>
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

    private fun getLoginRequestBody(email: String, password: String): LoginRequestBody {
        return LoginRequestBody(
            email,
            password
        )
    }

    fun saveUser(data: UserResponse?) {
        userRepository.saveUser(data)
    }



}