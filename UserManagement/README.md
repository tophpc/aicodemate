# 用户管理系统 (User Management)

基于 **Android 15 (API 35)** 的用户管理应用，专为 **1280×800** 平板设备优化。

## 功能特性

### 🔐 登录界面
- 用户名 + 密码登录验证
- 输入校验与错误提示
- Material Design 卡片式登录表单
- 默认管理员账号：`admin` / `admin123`

### 👥 用户管理
- **查看** 所有用户列表（带搜索功能）
- **添加** 新用户
- **编辑** 已有用户信息
- **删除** 用户（带确认弹窗）
- **启用/禁用** 用户账号
- 用户角色管理（管理员 / 普通用户）

### 📱 适配平板
- 针对 1280×800 分辨率优化布局
- 使用 `layout-sw600dp` 提供平板专属布局
- 响应式设计，支持横竖屏切换

## 技术架构

| 组件 | 技术 |
|------|------|
| 语言 | Kotlin |
| UI | Material Design 3 + ConstraintLayout |
| 数据存储 | SQLite (本地数据库) |
| 最低 SDK | API 26 (Android 8.0) |
| 目标 SDK | API 35 (Android 15) |
| 构建工具 | Gradle 8.7 + AGP 8.5.0 |

## 项目结构

```
UserManagement/
├── app/
│   ├── build.gradle.kts          # 应用构建配置
│   └── src/main/
│       ├── AndroidManifest.xml    # 应用清单
│       ├── java/.../
│       │   ├── User.kt               # 用户数据模型
│       │   ├── UserDatabaseHelper.kt  # SQLite 数据库帮助类
│       │   ├── UserAdapter.kt         # RecyclerView 适配器
│       │   ├── LoginActivity.kt       # 登录界面
│       │   ├── UserManagementActivity.kt  # 用户管理主界面
│       │   └── AddEditUserActivity.kt     # 添加/编辑用户界面
│       └── res/
│           ├── layout/             # 默认布局
│           ├── layout-sw600dp/     # 平板优化布局
│           ├── values/             # 颜色、字符串、主题
│           └── drawable/           # 自定义背景
├── build.gradle.kts               # 项目级构建文件
├── settings.gradle.kts            # 项目设置
└── gradle/                        # Gradle 配置
```

## 快速开始

1. 使用 Android Studio 打开 `UserManagement` 文件夹
2. 等待 Gradle 同步完成
3. 连接 1280×800 平板或创建对应尺寸的模拟器
4. 点击 Run 运行应用
5. 使用默认账号 `admin` / `admin123` 登录

## 用户数据字段

| 字段 | 说明 | 是否必填 |
|------|------|----------|
| 用户名 | 唯一登录标识 | ✅ |
| 密码 | 至少6位 | ✅ |
| 显示名称 | 在界面显示的名称 | ✅ |
| 邮箱 | 电子邮件地址 | ❌ |
| 手机号 | 联系电话 | ❌ |
| 角色 | 管理员 / 普通用户 | ✅ |
| 状态 | 启用 / 禁用 | ✅ |
