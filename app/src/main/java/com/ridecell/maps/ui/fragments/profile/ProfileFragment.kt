package com.ridecell.maps.ui.fragments.profile

import android.content.Intent
import android.view.View
import com.github.sisyphsu.dateparser.DateParser
import com.ridecell.maps.R
import com.ridecell.maps.data.remote.response.ProfileResponse
import com.ridecell.maps.di.component.FragmentComponent
import com.ridecell.maps.ui.base.BaseFragment
import com.ridecell.maps.ui.login.ui.LoginRegistrationActivity
import com.ridecell.maps.utils.common.DateUtils
import com.ridecell.maps.utils.common.Status
import com.ridecell.maps.utils.loader.LoaderUtils
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ProfileFragment : BaseFragment<ProfileViewModel>(), View.OnClickListener {
    override fun provideLayoutId(): Int = R.layout.fragment_profile

    override fun injectDependencies(fragmentComponent: FragmentComponent) = fragmentComponent.inject(this)

    override fun setupView(view: View) {
        button.setOnClickListener(this)
        getUserProfile(view)
    }

    private fun getUserProfile(view: View) {
        viewModel.getUser().observe(this, {
            when(it.status){
                Status.LOADING ->  {
                    LoaderUtils.showLoader(requireContext(), false)
                }
                Status.SUCCESS -> {
                    renderData(it.data, view)
                    LoaderUtils.hideLoader()
                }
                Status.ERROR -> {
                    LoaderUtils.hideLoader()
                }
            }
        })
    }

    private fun renderData(data: ProfileResponse?, view: View) {
        data?.let { userDetails->
            view.tvName.text = userDetails.name
            view.tvEmail.text = userDetails.email
            view.tvCreatedAt.text = getCalculatedCreatedAtTime(userDetails.createdAt)
        }

    }

    private fun getCalculatedCreatedAtTime(createdAt: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dateStart = createdAt
        val dateStop = format.format(Date())
        var d1: Date? = null
        var d2: Date? = null
        try {
            d1 = format.parse(dateStart)
            d2 = format.parse(dateStop)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val timeDifferenceMilliseconds = d2?.time?.minus(d1?.time!!)
        val diffDays: Long = timeDifferenceMilliseconds?.div((60 * 60 * 1000 * 24)) ?: 0
        return "$diffDays Day Old"
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button -> {
                viewModel.logout()
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
        val intent = Intent(requireContext(), LoginRegistrationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}