<div align="center">

# 🏦 EconomySystem
### *A Modern Minecraft Economy Plugin*

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java&logoColor=white)
![Minecraft](https://img.shields.io/badge/Minecraft-1.21.4-green?style=for-the-badge&logo=minecraft&logoColor=white)
![Spigot](https://img.shields.io/badge/Spigot-Paper-yellow?style=for-the-badge)
![License](https://img.shields.io/badge/License-Educational-blue?style=for-the-badge)

*Built with modern architecture patterns and cutting-edge frameworks*

---

</div>

## 🌟 **Project Overview**

A comprehensive economy system that demonstrates **advanced Java development practices** and **modern plugin architecture**. This project showcases professional-grade code organization, robust error handling, and seamless integration with industry-standard frameworks.

<table>
<tr>
<td width="50%">

### 🎯 **Core Features**
- 💰 **Advanced Balance System**
- 🔄 **Secure Transactions**
- 📊 **Dynamic Leaderboards**
- 🛡️ **Permission-Based Security**
- 🗄️ **SQLite Integration**
- ⚡ **Vault Compatibility**

</td>
<td width="50%">

### 🏗️ **Architecture Highlights**
- 🎭 **Dependency Injection**
- 📝 **Annotation-Driven Commands**
- 🎨 **Rich Text Messaging**
- 🔧 **Configuration Management**
- 🧩 **Modular Design**
- 📱 **Event-Driven Architecture**

</td>
</tr>
</table>

---

## 🚀 **Command Arsenal**

<div align="center">

| Command | Description | Permissions |
|---------|-------------|-------------|
| 💰 `/balance` | Check player balances | `eco.bal` |
| 💸 `/pay <player> <amount>` | Transfer money securely | `eco.pay` |
| 🏆 `/baltop [amount]` | View wealth leaderboard | `eco.baltop` |
| ⚙️ `/aeco give <player> <amount>` | Administrative money management | `eco.admincommands` |
| ⚙️ `/aeco take <player> <amount>` | Remove player funds | `eco.admincommands` |
| ⚙️ `/aeco set <player> <amount>` | Set exact balance | `eco.admincommands` |
| ⚙️ `/aeco reset <player>` | Reset player balance | `eco.admincommands` |

</div>

---

## 🎨 **Visual Experience**

<div align="center">

### **Rich Text & Color Coding**
*Powered by MiniMessage & Adventure API*

</div>

```yaml
# Beautiful, customizable messages with rich formatting
messages:
  Balance-onself: "<green>💰 Your balance: <yellow>%amount%"
  Pay-Success-Sender: "<green>✅ You sent <yellow>%amount% %currency% <green>to <aqua>%player%"
  Top-Format: "<gold>🏆 #<rank> %player% has %amount%</gold>"
```

---

## 🏛️ **Architecture Deep Dive**

<div align="center">

### **Modern Design Patterns Implementation**

</div>

<table>
<tr>
<td width="33%">

#### 🎯 **Command Pattern**
```java
@Command("balance")
@Permission("eco.bal")
@Description("Check balances")
public class BalanceCommands {
    @Dependency
    private EconomyManager economyManager;
}
```

</td>
<td width="33%">

#### 🔄 **Repository Pattern**
```java
public class EconomyManager {
    private final Connection connection;
    
    public double getBalance(UUID uuid) {
        // Secure database operations
    }
}
```

</td>
<td width="33%">

#### 🎭 **Dependency Injection**
```java
imperat = BukkitImperat.builder(this)
    .dependencyResolver(
        EconomyManager.class,
        () -> economyManager
    ).build();
```

</td>
</tr>
</table>

---

## 🗄️ **Database Architecture**

<div align="center">

### **Optimized SQLite Schema**

</div>

```sql
CREATE TABLE players (
    uuid TEXT PRIMARY KEY,      -- Future-proof UUID identification
    balance REAL DEFAULT 0,     -- Precise monetary values
    username TEXT NOT NULL      -- Human-readable identification
);
```

**Key Features:**
- 🔑 **UUID-Based Primary Keys** - Handles name changes gracefully
- 💾 **Lightweight SQLite** - No external database dependencies
- 🔒 **ACID Compliance** - Guaranteed transaction integrity
- ⚡ **Connection Pooling** - Optimized database performance

---

## 🎨 **User Experience Design**

<div align="center">

### **Intuitive Feedback System**

</div>

<table>
<tr>
<td align="center" width="25%">

### 🟢 **Success States**
Rich green formatting<br/>
Clear action confirmation<br/>
Positive visual feedback

</td>
<td align="center" width="25%">

### 🔴 **Error Handling**
Descriptive error messages<br/>
Input validation feedback<br/>
Helpful suggestions

</td>
<td align="center" width="25%">

### 🟡 **Information**
Formatted data display<br/>
Currency visualization<br/>
Professional styling

</td>
<td align="center" width="25%">

### 🔵 **Administrative**
Admin-specific styling<br/>
Action confirmations<br/>
Security indicators

</td>
</tr>
</table>

---

## ⚙️ **Configuration Excellence**

<div align="center">

### **Flexible & Powerful Configuration System**

</div>

```yaml
# 🎯 System Configuration
SystemName: "EconomySystem"
CurrencyName: "Dollar"
CurrencyNamePlural: "Dollars"

# 🎨 Message Customization (MiniMessage Format)
messages:
  # 💰 Economy Messages
  Balance-onself: "<green>💰 Your balance: <yellow>%amount%"
  Pay-Success-Sender: "<green>✅ Sent <yellow>%amount% %currency% <green>to <aqua>%player%"
  
  # 🏆 Leaderboard
  Top-Format: "<gold>🏆 #<rank> %player% has %amount%</gold>"
  
  # 🛡️ Security & Validation
  Error-Pay-Insufficient-Funds: "<red>❌ Insufficient funds!"
  Error-Pay-Invalid-Amount: "<red>⚠️ Please enter a valid amount!"
```

**Configuration Highlights:**
- 🎯 **Zero-Downtime Updates** - Hot-reload configuration changes
- 🌍 **Internationalization Ready** - Easy localization support
- 🎨 **Rich Text Support** - Full MiniMessage formatting
- 🔧 **Placeholder System** - Dynamic content injection

---

## 🙏 **Framework Acknowledgments**

<div align="center">

### **Powered by Industry-Leading Frameworks**

</div>

<table>
<tr>
<td align="center" width="50%">

### 🎯 **[Imperat Command Framework](https://github.com/VelixDevelopment/Imperat)**

<img src="https://img.shields.io/badge/Imperat-Revolutionary-purple?style=for-the-badge" alt="Imperat">

**The Future of Minecraft Commands**

✨ **Annotation-Driven Development**<br/>
🔧 **Advanced Dependency Injection**<br/>
🛡️ **Type-Safe Command Handling**<br/>
⚡ **Auto-Completion Support**<br/>
🎭 **Flexible Architecture**<br/>
🔐 **Seamless Permission Integration**

*Imperat transforms command development from tedious boilerplate into elegant, declarative code. This framework represents the cutting edge of Minecraft plugin development.*

</td>
<td align="center" width="50%">

### 🌟 **Supporting Technologies**

<br/>

#### 🎨 **Adventure API**
*Modern text component system*<br/>
Rich formatting & cross-platform compatibility

#### 🏦 **Vault API**
*Economy integration standard*<br/>
Universal plugin compatibility

#### ✨ **MiniMessage**
*Intuitive text formatting*<br/>
Powerful yet simple syntax

#### ☕ **Modern Java**
*Latest language features*<br/>
Clean, maintainable code

</td>
</tr>
</table>

---

## 🎓 **Educational Excellence**

<div align="center">

### **A Showcase of Professional Development Practices**

</div>

<table>
<tr>
<td width="50%">

#### 🏗️ **Architecture Mastery**
- **Clean Architecture Principles**
- **SOLID Design Patterns**
- **Dependency Inversion**
- **Separation of Concerns**
- **Modular Component Design**

#### 🔒 **Security & Validation**
- **Input Sanitization**
- **Permission-Based Access Control**
- **Transaction Integrity**
- **SQL Injection Prevention**
- **Comprehensive Error Handling**

</td>
<td width="50%">

#### 💾 **Data Management**
- **Database Design Principles**
- **Connection Management**
- **Transaction Safety**
- **Query Optimization**
- **Data Integrity Constraints**

#### 🎨 **User Experience Design**
- **Intuitive Command Structure**
- **Rich Visual Feedback**
- **Comprehensive Help System**
- **Error Recovery Guidance**
- **Accessibility Considerations**

</td>
</tr>
</table>

---

<div align="center">

## 🚀 **Technical Stack**

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)
![Minecraft](https://img.shields.io/badge/Minecraft-62B47A?style=for-the-badge&logo=minecraft&logoColor=white)

### **Dependencies & Frameworks**
`Spigot API` • `Paper API` • `Vault API` • `Adventure API` • `Imperat Framework` • `MiniMessage`

---

## 📚 **Project Purpose**

*This project serves as an **educational demonstration** and **portfolio showcase**, highlighting modern Java development practices, clean architecture principles, and professional-grade plugin development for the Minecraft ecosystem.*

---

**✨ Crafted with passion for the Minecraft development community ✨**

![Made with Love](https://img.shields.io/badge/Made%20with-❤️-red?style=for-the-badge)
![For Education](https://img.shields.io/badge/Purpose-Education-blue?style=for-the-badge)
![Portfolio](https://img.shields.io/badge/Type-Portfolio-green?style=for-the-badge)

</div>