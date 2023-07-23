package com.dicoding.github

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _followerUser = MutableLiveData<List<UsersItem>>()
    val followerUser : LiveData<List<UsersItem>> = _followerUser
    private val _followingUser = MutableLiveData<List<UsersItem>>()
    val followingUser : LiveData<List<UsersItem>> = _followingUser
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findFollower(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object: Callback<List<UsersItem>> {
            override fun onResponse(
                call: Call<List<UsersItem>>,
                response: Response<List<UsersItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val items = response.body()
                    _followerUser.value = items
                } else {
                    Log.e("Followers :", "Data Not Found")
                }
            }
            override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("FOLLOWERS", "onFailure: ${t.message}")
            }
        })
    }

    fun findFollowing(username : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<UsersItem>>{
            override fun onResponse(
                call: Call<List<UsersItem>>,
                response: Response<List<UsersItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val items = response.body()
                    _followingUser.value = items
                }else{
                    Log.e("Following :", "Data not found")
                }
            }

            override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("Following", "onFailur : ${t.message}")
            }

        })

    }

}