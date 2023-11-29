package com.dev.frequenc.ui_codes.connect.chat

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.currentRecomposeScope
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.dev.frequenc.R
import com.dev.frequenc.databinding.FragmentAllChatUserBinding
import com.dev.frequenc.ui_codes.connect.Profile.ProfileFragment
import com.dev.frequenc.ui_codes.connect.VibesProfileList.ConnectionAdapter
import com.dev.frequenc.ui_codes.data.ConnectionResponse

class AllChatUserFragment : Fragment(), ChatListAdapter.ItemListListener,
    ConnectionAdapter.ListAdapterListener {

    private var currentActivity: FragmentActivity? = null
    lateinit var binding: FragmentAllChatUserBinding
    lateinit var allChatListViewModel: AllChatListViewModel

    companion object {
        private const val ItemUserListLay = 1
        private const val ItemUserChatPendingListLay = 2
        private const val ItemUserChatRequestsLay: Int = 3

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllChatUserBinding.inflate(inflater, container, false)
        try {
            currentActivity = activity
        } catch (ex: Exception) {
            try {
                currentActivity = requireActivity()
            } catch (exss: Exception) {
                exss.printStackTrace()
            }
            ex.printStackTrace()
        }

        allChatListViewModel =
            ViewModelProvider(requireActivity())[AllChatListViewModel::class.java]
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val connectionAdapter = ConnectionAdapter(ArrayList(3), this@AllChatUserFragment)
        binding.rvConnection.apply {
            adapter = connectionAdapter
        }
        val chatListAdapter =
            ChatListAdapter(ArrayList(3), 1, this@AllChatUserFragment)
        binding.rvChatUser.apply {
            adapter = chatListAdapter
        }

        activity?.runOnUiThread {
            allChatListViewModel.isConnectionTabSelected.observe(viewLifecycleOwner) {
                showConnectionTab(it, chatListAdapter)
            }
        }

        activity?.runOnUiThread {
            allChatListViewModel.isPendingSubTabSelected.observe(viewLifecycleOwner) {
                showPendingRequestsSubTab(it, chatListAdapter)
            }
        }
        allChatListViewModel.isApiCalled.observe(viewLifecycleOwner) {
            if (it == true) {

            } else {

            }
        }

        activity?.runOnUiThread {
            allChatListViewModel.userListsData.observe(viewLifecycleOwner) {
                if (!it.isNullOrEmpty() && it.size > 0) {

                } else {

                }
            }
        }

        binding.tvRequestsTag.setOnClickListener {
            allChatListViewModel.setConnectionTab(false)
        }
        binding.tvConnectionTag.setOnClickListener {
            allChatListViewModel.setConnectionTab(true)
        }

        binding.pendingTab.setOnClickListener {
            allChatListViewModel.setPendingTab(true)
        }
        binding.myRequestsTab.setOnClickListener {
            allChatListViewModel.setPendingTab(false)
        }

        allChatListViewModel.setConnectionTab(true)
    }

    private fun showPendingRequestsSubTab(
        toShowPendingRequestTab: Boolean,
        chatListAdapter: ChatListAdapter
    ) {
        if (allChatListViewModel.isConnectionTabSelected.value == false) {
            if (toShowPendingRequestTab) {
                    allChatListViewModel.userListsData.value?.let {
                        chatListAdapter.refreshData(
                            it,
                            ItemUserChatPendingListLay
                        )
                    }
                binding.headPending.setTextColor(Color.parseColor("#8023EB"))
                binding.selectedHeadPending.visibility = View.VISIBLE

                binding.headMyrequests.setTextColor(Color.parseColor("#171A1F"))
                binding.selectedHeadMyrequests.visibility = View.INVISIBLE
            } else {
//                activity?.runOnUiThread {
                    allChatListViewModel.userListsData.value?.let {
                        chatListAdapter.refreshData(
                            it,
                            ItemUserChatRequestsLay
                        )
                    }
//                }
                binding.headMyrequests.setTextColor(Color.parseColor("#8023EB"))
                binding.selectedHeadMyrequests.visibility = View.VISIBLE


                binding.headPending.setTextColor(Color.parseColor("#171A1F"))
                binding.selectedHeadPending.visibility = View.INVISIBLE
            }
        }
    }

    private fun showConnectionTab(toShowConnectionTab: Boolean, chatListAdapter: ChatListAdapter) {
        if (toShowConnectionTab) {
                allChatListViewModel.userListsData.value?.let {
                    chatListAdapter.refreshData(it, ItemUserListLay)
                }
            binding.tvConnectionTag.setTextColor(Color.parseColor("#8023EB"))
            binding.tvChatTag.visibility = View.VISIBLE

            binding.tvRequestsTag.setTextColor(Color.parseColor("#171A1F"))
            binding.requestLay.visibility = View.INVISIBLE
        } else {
            allChatListViewModel.setPendingTab(true)
            binding.tvRequestsTag.setTextColor(Color.parseColor("#8023EB"))
            binding.requestLay.visibility = View.VISIBLE

            binding.tvConnectionTag.setTextColor(Color.parseColor("#171A1F"))
            binding.tvChatTag.visibility = View.INVISIBLE
        }
    }

    override fun onClickAtConnection(item: ConnectionResponse) {
        Toast.makeText(context, "Chat Screen is under construction. ", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(itemPosition: Int, useType: Int, action: String) {

        when (useType) {
            ItemUserListLay -> {
                performClickAction(action, 0)
            }

            ItemUserChatPendingListLay -> {
//                if (allChatListViewModel.isConnectionTabSelected.value == false && allChatListViewModel.isPendingSubTabSelected.value == true  ) {
                performClickAction(action, 0)
//                }
            }

            ItemUserChatRequestsLay -> {
                performClickAction(action, 0)
            }
        }
    }

    private fun performClickAction(action: String, item_id: Int) {
        when (action) {
            "goChat" -> {
                Toast.makeText(context, "Chat Screen is under construction. ", Toast.LENGTH_SHORT)
                    .show()
            }

            "goProfile" -> {
                try {
                    currentActivity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.flFragment, ProfileFragment())
                        ?.commit()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }

            "showMenu" -> {
                Toast.makeText(context, "Menu is not designed. ", Toast.LENGTH_SHORT).show()
            }

            "accept" -> {
                Toast.makeText(context, "Accepted", Toast.LENGTH_SHORT).show()
            }

            "decline" -> {
                Toast.makeText(context, "Rejected. ", Toast.LENGTH_SHORT).show()
            }

        }
    }
}