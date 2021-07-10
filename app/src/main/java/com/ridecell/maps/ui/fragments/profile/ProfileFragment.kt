package com.ridecell.maps.ui.fragments.profile

import android.view.View
import com.github.sisyphsu.dateparser.DateParser
import com.ridecell.maps.R
import com.ridecell.maps.data.remote.response.ProfileResponse
import com.ridecell.maps.di.component.FragmentComponent
import com.ridecell.maps.ui.base.BaseFragment
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

class ProfileFragment : BaseFragment<ProfileViewModel>() {

    @Inject
    lateinit var dateParser : DateParser

    override fun provideLayoutId(): Int = R.layout.fragment_profile

    override fun injectDependencies(fragmentComponent: FragmentComponent) = fragmentComponent.inject(this)

    override fun setupView(view: View) {
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
        val format = SimpleDateFormat("yy/MM/dd HH:mm:ss")
        val dateStart = format.format(dateParser.parseDate(createdAt))
        val dateStop = format.format(Date())
        var d1: Date? = null
        var d2: Date? = null
        try {
            d1 = format.parse(dateStart)
            d2 = format.parse(dateStop)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val timeDifferenceMilliseconds = d1?.time?.minus(d2?.time!!)
        val diffDays: Long = timeDifferenceMilliseconds?.div((60 * 60 * 1000 * 24)) ?: 0
        return  diffDays.toString()
    }
}