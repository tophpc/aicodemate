package com.example.usermanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * 登录界面 - 适配 1280×800 平板
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var dbHelper: UserDatabaseHelper
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvHint: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = UserDatabaseHelper(this)

        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        tvHint = findViewById(R.id.tv_login_hint)

        btnLogin.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (username.isEmpty()) {
            etUsername.error = "请输入用户名"
            etUsername.requestFocus()
            return
        }

        if (password.isEmpty()) {
            etPassword.error = "请输入密码"
            etPassword.requestFocus()
            return
        }

        val user = dbHelper.validateLogin(username, password)
        if (user != null) {
            Toast.makeText(this, "欢迎回来，${user.displayName}！", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, UserManagementActivity::class.java).apply {
                putExtra("current_user_id", user.id)
                putExtra("current_user_role", user.role)
                putExtra("current_user_name", user.displayName)
            }
            startActivity(intent)
            finish()
        } else {
            tvHint.text = "用户名或密码错误，请重试"
            tvHint.setTextColor(getColor(R.color.error_red))
            Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show()
        }
    }
}
