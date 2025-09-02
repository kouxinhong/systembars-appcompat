# 贡献指南

欢迎对 SystemBarsCompat 项目进行贡献！以下是一些指导原则，帮助你更好地参与项目开发。

## 开发环境设置

1. 克隆项目仓库：
   ```bash
   git clone https://github.com/kouzi/systembars-appcompat.git
   cd systembars-appcompat
   ```

2. 使用 Android Studio 打开项目

3. 确保项目能够正常编译和运行

## 代码风格

- 对于 Kotlin 代码，请遵循 [Kotlin 官方编码规范](https://kotlinlang.org/docs/coding-conventions.html)
- 对于 Java 代码，请遵循 [Android 官方 Java 编码规范](https://source.android.com/setup/contribute/code-style)
- 确保代码格式一致，使用适当的缩进和空格
- 添加有意义的注释，特别是对于复杂的逻辑

## 提交代码

1. 在提交代码前，确保所有测试都能通过

2. 提交消息应清晰明了，遵循以下格式：
   ```
   [类别] 简短描述
   
   详细描述（如有需要）
   ```

   常见类别包括：
   - `feat`：新功能
   - `fix`：修复bug
   - `docs`：文档更新
   - `style`：代码风格调整（不影响功能）
   - `refactor`：代码重构
   - `test`：添加或修改测试
   - `chore`：构建过程或辅助工具变动

3. 尽量保持每个提交的改动范围较小，专注于解决特定问题

## 提交流程

1. Fork 项目仓库

2. 创建一个新的分支：
   ```bash
   git checkout -b feature/your-feature-name
   ```
   或
   ```bash
   git checkout -b fix/your-bugfix-name
   ```

3. 进行代码修改

4. 提交你的更改：
   ```bash
   git commit -m "[类别] 简短描述"
   ```

5. 推送到你的 Fork：
   ```bash
   git push origin feature/your-feature-name
   ```

6. 创建 Pull Request 到主仓库

## 报告问题

如果你发现了bug或有新功能建议，请在GitHub上创建一个Issue。在创建Issue时，请提供以下信息：

1. 清晰的标题
2. 详细的描述
3. 复现步骤（对于bug）
4. 预期行为
5. 实际行为
6. 环境信息（Android版本、设备型号等）
7. 相关的错误日志或截图

## 代码审查

所有的Pull Request都将经过代码审查。请耐心等待项目维护者的反馈，并根据反馈进行必要的修改。

## 许可证

通过向此项目贡献代码，你同意你的贡献将在 Apache License 2.0 下发布。