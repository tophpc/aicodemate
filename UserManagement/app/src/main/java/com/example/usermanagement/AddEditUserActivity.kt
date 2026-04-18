package com.example.usermanagement

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/**
 * 添加/编辑用户界面
 */
class AddEditUserActivity : AppCompatActivity() {

    private lateinit var dbHelper: UserDatabaseHelper
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etDisplayName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var spinnerRole: Spinner
    private lateinit var switchActive: Switch
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var tvTitle: TextView

    private var editUserId: Long = -1
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_user)

        dbHelper = UserDatabaseHelper(this)
        editUserId = intent.getLongExtra("user_id", -1)
        isEditMode = editUserId > 0

        initViews()

        if (isEditMode) {
            loadUserData()
        }
    }

    private fun initViews() {
        tvTitle = findViewById(R.id.tv_form_title)
        etUsername = findViewById(R.id.et_form_username)
        etPassword = findViewById(R.id.et_form_password)
        etDisplayName = findViewById(R.id.et_form_display_name)
        etEmail = findViewById(R.id.et_form_email)
        etPhone = findViewById(R.id.et_form_phone)
        spinnerRole = findViewById(R.id.spinner_role)
        switchActive = findViewById(R.id.switch_active)
        btnSave = findViewById(R.id.btn_save)
        btnCancel = findViewById(R.id.btn_cancel)

        tvTitle.text = if (isEditMode) "编辑用户" else "添加用户"

        // 设置角色下拉选项
        val roles = arrayOf("普通用户", "管理员")
        val roleAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRole.adapter = roleAdapter

        btnSave.setOnClickListener { saveUser() }
        btnCancel.setOnClickListener { finish() }
    }

    private fun loadUserData() {
        val user = dbHelper.getUserById(editUserId) ?: return

        etUsername.setText(user.username)
        etPassword.setText(user.password)
        etDisplayName.setText(user.displayName)
        etEmail.setText(user.email)
        etPhone.setText(user.phone)
        switchActive.isChecked = user.isActive

        // 设置角色选中状态
        val roleIndex = if (user.role == "管理员") 1 else 0
        spinnerRole.setSelection(roleIndex)
    }

    private fun saveUser() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val displayName = etDisplayName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val role = spinnerRole.selectedItem.toString()
        val isActive = switchActive.isChecked

        // 验证必填字段
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
        if (password.length < 6) {
            etPassword.error = "密码至少6位"
            etPassword.requestFocus()
            return
        }
        if (displayName.isEmpty()) {
            etDisplayName.error = "请输入显示名称"
            etDisplayName.requestFocus()
            return
        }

        // 检查用户名是否重复
        if (dbHelper.isUsernameExists(username, if (isEditMode) editUserId else -1)) {
            etUsername.error = "该用户名已被占用"
            etUsername.requestFocus()
            return
        }

        val user = User(
            id = if (isEditMode) editUserId else 0,
            username = username,
            password = password,
            displayName = displayName,
            email = email,
            phone = phone,
            role = role,
            isActive = isActive,
            createdAt = if (isEditMode) {
                dbHelper.getUserById(editUserId)?.createdAt ?: System.currentTimeMillis()
            } else {
                System.currentTimeMillis()
            }
        )

        if (isEditMode) {
            dbHelper.updateUser(user)
            Toast.makeText(this, "用户信息已更新", Toast.LENGTH_SHORT).show()
        } else {
            val newId = dbHelper.addUser(user)
            if (newId > 0) {
                Toast.makeText(this, "用户添加成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "添加失败，请重试", Toast.LENGTH_SHORT).show()
                return
            }
        }

        setResult(RESULT_OK)
        finish()
    }
}
