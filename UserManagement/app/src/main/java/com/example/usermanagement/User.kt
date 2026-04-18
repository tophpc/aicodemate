package com.example.usermanagement

/**
 * 用户数据模型
 */
data class User(
    val id: Long = 0,
    val username: String,
    val password: String,
    val displayName: String,
    val email: String = "",
    val phone: String = "",
    val role: String = "普通用户", // 普通用户 / 管理员
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
