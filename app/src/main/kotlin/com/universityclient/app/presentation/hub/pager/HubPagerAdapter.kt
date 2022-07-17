package com.universityclient.app.presentation.hub.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.universityclient.app.presentation.chat.ChatFragment
import com.universityclient.app.presentation.home.HomeFragment
import com.universityclient.app.presentation.profile.ProfileFragment

class HubPagerAdapter(
    parentFragment: Fragment
) : FragmentStateAdapter(parentFragment) {

    private val homeFragment: HomeFragment
        get() = HomeFragment()
    private val chatFragment: ChatFragment
        get() = ChatFragment()
    private val profileFragment: ProfileFragment
        get() = ProfileFragment()

    private val fragmentList = listOf(
        homeFragment,
        chatFragment,
        profileFragment
    )

    override fun createFragment(position: Int) = fragmentList[position]

    override fun getItemCount() = fragmentList.size
}
