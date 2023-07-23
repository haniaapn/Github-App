package com.dicoding.github

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.github.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {
    private lateinit var binding : FragmentFollowBinding
    private var position :  Int = 0
    var username : String? = null

    companion object{
        const val ARG_POSITION= "position"
        const val ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUserFollow.layoutManager = layoutManager

        val followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowViewModel::class.java]

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1){
            username?.let {
                followViewModel.findFollower(it)
            }
            followViewModel.followerUser.observe(viewLifecycleOwner){item ->
                setData(item)
            }
        }else{
           username?.let {
               followViewModel.findFollowing(it)
           }
            followViewModel.followingUser.observe(viewLifecycleOwner){ item ->
                setData(item)
            }
        }
        followViewModel.isLoading.observe(requireActivity()){
            showLoading(it)
        }
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    private fun setData(item: List<UsersItem>) {
        val adapter = UserAdapter(item)
        binding.rvUserFollow.adapter = adapter
    }

}

