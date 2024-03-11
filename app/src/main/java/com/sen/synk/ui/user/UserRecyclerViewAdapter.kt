package com.sen.synk.ui.user

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sen.synk.data.model.Account
import com.sen.synk.databinding.ItemUserBinding

/**
 * [RecyclerView.Adapter] that can display a [Account].
 */
class UserRecyclerViewAdapter(
    val listener: EditUserClickListener
) : RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {


    private var items: List<Account>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)

        holder.tvUser.text = "ID:${item?.id} - ${item?.role} - ${item?.username}"
        holder.tvEmail.text = item?.email

        holder.ivEdit.setOnClickListener {view ->
            item?.let {
                listener.onEditClick(
                    it
                )
            }
        }

        holder.ivDelete.setOnClickListener { view ->
            item?.let {
                listener.onDeleteClick(
                    it
                )
            }
        }
    }

    override fun getItemCount(): Int = items?.size ?: 0

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(items: List<Account>) {
        this.items = items
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivEdit: ImageView = binding.ivEdit
        val ivDelete: ImageView = binding.ivDelete
        val tvUser: TextView = binding.tvTitle
        val tvEmail: TextView = binding.tvEmail
    }
}

interface EditUserClickListener{
    fun onEditClick(account: Account)

    fun onDeleteClick(account: Account)
}