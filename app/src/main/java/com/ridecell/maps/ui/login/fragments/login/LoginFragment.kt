package com.ridecell.maps.ui.login.fragments.login

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ridecell.maps.R
import com.ridecell.maps.data.remote.response.UserResponse
import com.ridecell.maps.di.component.FragmentComponent
import com.ridecell.maps.ui.base.BaseFragment
import com.ridecell.maps.ui.forgotpassword.ForgotPasswordActivity
import com.ridecell.maps.ui.main.MainActivity
import com.ridecell.maps.utils.common.Status
import com.ridecell.maps.utils.common.ValidationStatus
import com.ridecell.maps.utils.loader.LoaderUtils
import com.ridecell.maps.utils.navigation.safeNavigate
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.w3c.dom.Text

class LoginFragment : BaseFragment<LoginViewModel>(), View.OnClickListener {

    private var navController : NavController? = null

    override fun provideLayoutId(): Int = R.layout.fragment_login

    override fun injectDependencies(fragmentComponent: FragmentComponent) = fragmentComponent.inject(this)

    override fun setupView(view: View) {
        navController = Navigation.findNavController(view)
        getPasswordRequirements()
        setOnClickListeners(view)
        setTextWatchers(view)
        observeValidation()
    }

    private fun getPasswordRequirements() {
        viewModel.getPasswordValidations().observe(this, {
            if(it!=null){
                viewModel.setRequirements(it)
            }
        })
    }

    private fun observeValidation() {
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
                ValidationStatus.ERROR -> tlPassword.error = it.data?.run { getString(this) }
                else -> tlPassword.isErrorEnabled = false
            }
        })

        viewModel.passwordField.observe(this, {
            if (edPassword.text.toString() != it) edPassword.setText(it)
        })
    }

    private fun setOnClickListeners(view: View) {
        view.button.setOnClickListener(this)
        view.textView.setOnClickListener(this)
        view.buttonRegistration.setOnClickListener(this)
    }

    private fun setTextWatchers(view: View) {
        view.edEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        view.edPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onPasswordChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button -> {
                observeLogin()
            }
            R.id.buttonRegistration -> {
                navController?.safeNavigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
            }
            R.id.textView -> {
                //Forgot Password
                val intent = Intent(requireActivity(), ForgotPasswordActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun observeLogin() {
        viewModel.onLogin().observe(this, {
            when(it.status){
                Status.LOADING -> {
                    LoaderUtils.showLoader(requireContext(), false)
                }
                Status.SUCCESS -> {
                    saveUserData(it.data)
                    LoaderUtils.hideLoader()
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.networkError?.message, Toast.LENGTH_LONG).show()
                    LoaderUtils.hideLoader()
                }
            }
        })
    }

    private fun saveUserData(data: UserResponse?) {
        viewModel.saveUser(data)
    }
}