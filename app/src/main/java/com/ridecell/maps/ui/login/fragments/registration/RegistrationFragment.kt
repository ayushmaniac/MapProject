package com.ridecell.maps.ui.login.fragments.registration

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ridecell.maps.R
import com.ridecell.maps.data.remote.response.UserResponse
import com.ridecell.maps.di.component.FragmentComponent
import com.ridecell.maps.ui.base.BaseFragment
import com.ridecell.maps.ui.main.MainActivity
import com.ridecell.maps.utils.common.Status
import com.ridecell.maps.utils.common.ValidationStatus
import com.ridecell.maps.utils.loader.LoaderUtils
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.edEmail
import kotlinx.android.synthetic.main.fragment_registration.edPassword
import kotlinx.android.synthetic.main.fragment_registration.tlEmail
import kotlinx.android.synthetic.main.fragment_registration.tlPassword
import kotlinx.android.synthetic.main.fragment_registration.view.*
import kotlinx.android.synthetic.main.fragment_registration.view.edEmail
import kotlinx.android.synthetic.main.fragment_registration.view.edPassword

class RegistrationFragment : BaseFragment<RegistrationViewModel>(), View.OnClickListener {

    private var navController: NavController? = null

    override fun provideLayoutId(): Int = R.layout.fragment_registration

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    override fun setupView(view: View) {
        navController = Navigation.findNavController(view)
        getPasswordRequirements()
        setOnClickListener(view)
        setTextWatchers(view)
        setObservers()
    }

    private fun getPasswordRequirements() {
        viewModel.getPasswordValidations().observe(this, {
            if(it!=null){
                viewModel.setRequirements(it)
            }
        })
    }

    private fun setObservers() {
        viewModel.emailValidation.observe(this, {
            when (it.status) {
                ValidationStatus.ERROR -> tlEmail.error = it.data?.run { getString(this) }
                else -> tlEmail.isErrorEnabled = false
            }
        })

        viewModel.emailField.observe(this, {
            if (edEmail.text.toString() != it) edEmail.setText(it)
        })

        viewModel.passwordValidation.observe(this, {
            when (it.status) {
                ValidationStatus.ERROR ->{
                    tlPassword.error = it.data?.run { getString(this) }
                    tlConfirmPassword.error = it.data?.run { getString(this) }
                }
                else -> {
                    tlPassword.isErrorEnabled = false
                    tlConfirmPassword.isErrorEnabled = false
                }
            }
        })

        viewModel.passwordField.observe(this, {
            if (edPassword.text.toString() != it) edPassword.setText(it)
            if (edConfirmPassword.text.toString() != it) edConfirmPassword.setText(it)

        })

        viewModel.nameValidation.observe(this, {
            when(it.status){
                ValidationStatus.ERROR -> {
                    tlName.error = it.data?.run { getString(this) }
                }
                else -> tlName.isErrorEnabled = false
            }
        })

        viewModel.nameField.observe(this, {
            if(edName.text.toString() != it) edName.setText(it)
        })
    }

    private fun setTextWatchers(view: View) {
        textWatcherImpl(view.edName)
        textWatcherImpl(view.edEmail)
        textWatcherImpl(view.edPassword)
        textWatcherImpl(view.edConfirmPassword)
    }


    private fun textWatcherImpl(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when (editText.id) {
                    rootView?.edName?.id -> {
                        viewModel.onNameChanged(s.toString())
                    }
                    rootView?.edEmail?.id -> {
                        viewModel.onEmailChanged(s.toString())
                    }
                    rootView?.edPassword?.id -> {
                        viewModel.onPasswordChanged(s.toString())
                    }
                    rootView?.edConfirmPassword?.id -> {
                        viewModel.onPasswordChanged(s.toString())
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    private fun setOnClickListener(view: View) {
        view.ivBack.setOnClickListener(this)
        view.btnSignup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBack -> {
                navController?.popBackStack()
            }
            R.id.btnSignup -> {
                observeSignup()
            }
        }
    }

    private fun observeSignup() {
        viewModel.onSignup().observe(this, {
            when(it.status){
                Status.LOADING -> {
                    LoaderUtils.showLoader(requireContext(), false)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.networkError?.message, Toast.LENGTH_LONG).show()
                    LoaderUtils.hideLoader()
                }
                Status.SUCCESS -> {
                    saveUser(it.data)
                    LoaderUtils.hideLoader()
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                }
            }
        })
    }

    fun saveUser(data: UserResponse?) {
        viewModel.saveUser(data)
    }
}