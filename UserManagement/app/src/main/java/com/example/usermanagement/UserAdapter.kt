package com.example.usermanagement

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

/**
 * 用户列表适配器
 */
class UserAdapter(
    private val onEditClick: (User) -> Unit,
    private val onDeleteClick: (User) -> Unit,
    private val onToggleActiveClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var users: List<User> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDisplayName: TextView = itemView.findViewById(R.id.tv_item_display_name)
        private val tvUsername: TextView = itemView.findViewById(R.id.tv_item_username)
        private val tvRole: TextView = itemView.findViewById(R.id.tv_item_role)
        private val tvEmail: TextView = itemView.findViewById(R.id.tv_item_email)
        private val tvPhone: TextView = itemView.findViewById(R.id.tv_item_phone)
        private val tvStatus: TextView = itemView.findViewById(R.id.tv_item_status)
        private val tvCreatedAt: TextView = itemView.findViewById(R.id.tv_item_created_at)
        private val btnEdit: ImageButton = itemView.findViewById(R.id.btn_item_edit)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btn_item_delete)
        private val btnToggleActive: ImageButton = itemView.findViewById(R.id.btn_item_toggle_active)

        @SuppressLint("SimpleDateFormat")
        fun bind(user: User) {
            tvDisplayName.text = user.displayName
            tvUsername.text = "@${user.username}"
            tvRole.text = user.role
            tvEmail.text = user.email.ifEmpty { "未设置" }
            tvPhone.text = user.phone.ifEmpty { "未设置" }

            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            tvCreatedAt.text = "创建于 ${dateFormat.format(Date(user.createdAt))}"

            if (user.isActive) {
                tvStatus.text = "已启用"
                tvStatus.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.status_active)
                )
            } else {
                tvStatus.text = "已禁用"
                tvStatus.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.status_inactive)
                )
            }

            if (user.role == "管理员") {
                tvRole.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.role_admin)
                )
            } else {
                tvRole.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.role_user)
                )
            }

            btnEdit.setOnClickListener { onEditClick(user) }
            btnDelete.setOnClickListener { onDeleteClick(user) }
            btnToggleActive.setOnClickListener { onToggleActiveClick(user) }
        }
    }
}
