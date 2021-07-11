package com.ridecell.maps.ui.forgotpassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.ridecell.maps.R
import com.ridecell.maps.di.component.ActivityComponent
import com.ridecell.maps.ui.base.BaseActivity
import com.ridecell.maps.utils.common.Status
import com.ridecell.maps.utils.common.ValidationStatus
import com.ridecell.maps.utils.loader.LoaderUtils
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_forgot_password.edEmail

class ForgotPasswordActivity : BaseActivity<ForgotPasswordViewModel>(), View.OnClickListener {

    override fun provideLayoutId(): Int = R.layout.activity_forgot_password

    override fun injectDependencies(activityComponent: ActivityComponent) = activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        setOnClickListener()
        setTextWatcher()
        observeEmail()
    }

    private fun setTextWatcher() {
        edEmail.addTextChangedListener(object  : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEmailChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun observeEmail() {
            viewModel.emailValidation.observe(this, {
                when (it.status) {
                    ValidationStatus.ERROR -> tlEnterEmail.error = it.data?.run { getString(this) }
                    else -> tlEnterEmail.isErrorEnabled = false
                }
            })
    }

    private fun setOnClickListener() {
        btnReset.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnReset -> {
                observeResetPassword()
            }
        }
    }

    private fun observeResetPassword() {
        viewModel.resetPassword().observe(this, {
            when(it.status){
                Status.LOADING -> {
                    LoaderUtils.showLoader(this, false)
                }
                Status.SUCCESS -> {
                    Toast.makeText(this, it.data?.message, Toast.LENGTH_LONG).show()
                    LoaderUtils.hideLoader()
                }
                Status.ERROR -> {
                    LoaderUtils.hideLoader()
                    Toast.makeText(this, "Something went wrong, Please try again later", Toast.LENGTH_LONG).show()
                }
            }
        })

    }
}