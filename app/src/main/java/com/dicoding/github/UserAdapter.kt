package com.dicoding.github

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val listUser: List<UsersItem>) : RecyclerView.Adapter<UserAdapter.ViewHolderUser>() {
    class ViewHolderUser(view: View) : RecyclerView.ViewHolder(view){
        val imgPhoto: CircleImageView = view.findViewById(R.id.img_item_photo)
        val tvUsername: TextView  = view.findViewById(R.id.tv_item_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  ViewHolderUser (
        LayoutInflater.from(parent.context).inflate(R.layout.item_rv_user,parent,false)
    )

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ViewHolderUser, position: Int) {
        val user = listUser[position]
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.imgPhoto)
        holder.tvUsername.text = user.login
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailActivity::class.java)
            intent.putExtra(DetailActivity.LOGIN,user.login)
            holder.itemView.context.startActivity(intent)
        }
    }


}