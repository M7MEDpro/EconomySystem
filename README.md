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

### **Player Commands**

| Command | Aliases | Description | Permissions |
|---------|---------|-------------|-------------|
| 💰 `/balance` | `/bal` | Check your own balance or another player's balance | `eco.bal` |
| 💸 `/pay <player> <amount>` | - | Transfer money securely to another player | `eco.pay` |
| 🏆 `/baltop [amount]` | - | View wealth leaderboard (default: top 5, customizable) | `eco.baltop` |

### **Administrative Commands**

| Command | Description | Permissions |
|---------|-------------|-------------|
| ⚙️ `/aeco give <player> <amount>` | Add money to a player's balance | `eco.admincommands` |
| ⚙️ `/aeco take <player> <amount>` | Remove money from a player's balance | `eco.admincommands` |
| ⚙️ `/aeco set <player> <amount>` | Set a player's balance to a specific amount | `eco.admincommands` |
| ⚙️ `/aeco reset <player>` | Reset a player's balance to zero | `eco.admincommands` |

### **Command Usage Examples**

</div>

```bash
# Player Commands
/balance                    # Check your own balance
/bal                       # Alternative command for balance
/balance PlayerName        # Check another player's balance
/pay Steve 100            # Send 100 currency to Steve
/baltop                   # Show top 5 richest players
/baltop 10               # Show top 10 richest players

# Administrative Commands
/aeco give Steve 1000     # Give Steve 1000 currency
/aeco take Steve 500      # Remove 500 currency from Steve
/aeco set Steve 2000      # Set Steve's balance to exactly 2000
/aeco reset Steve         # Reset Steve's balance to 0
```

### **Advanced Features**

<table>
<tr>
<td width="50%">

#### 🔒 **Security Features**
- **Console Protection**: Pay command blocks console usage
- **Self-Payment Prevention**: Players cannot pay themselves
- **Insufficient Funds Checking**: Validates balance before transactions
- **Input Validation**: Prevents negative amounts and invalid inputs
- **Permission-Based Access**: Granular permission control

</td>
<td width="50%">

#### 💬 **Rich Feedback System**
- **Dual Notifications**: Both sender and receiver get notifications
- **Contextual Messages**: Different messages for different scenarios
- **Error Handling**: Descriptive error messages with suggestions
- **Success Confirmations**: Clear confirmation of completed actions
- **Administrative Feedback**: Special styling for admin operations

</td>
</tr>
</table>

### **Smart Command Behavior**

- **Balance Command**:
    - Works on self when no player specified
    - Supports checking other players' balances
    - Console-safe with appropriate error handling

- **Pay Command**:
    - Automatic transaction processing
    - Real-time balance updates
    - Comprehensive validation checks

- **Baltop Command**:
    - Flexible display count (default: 5 players)
    - Customizable ranking display
    - Efficient database querying

- **Admin Commands**:
    - Instant balance modifications
    - Notification system for affected players
    - Comprehensive placeholder support

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
CREATE TABLE IF NOT EXISTS players (
    uuid TEXT PRIMARY KEY,      -- Future-proof UUID identification
    balance REAL DEFAULT 0.0,   -- Precise monetary values
    username TEXT NOT NULL      -- Human-readable identification
);

-- Performance indexes
CREATE INDEX IF NOT EXISTS idx_balance ON players(balance DESC);
CREATE INDEX IF NOT EXISTS idx_username ON players(username);
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
DefaultBalance: 0.0

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
  
  # Additional error messages
  Error-Player-Not-Found: "<red>❌ Player not found or never joined!"
  Error-Pay-Self: "<red>❌ You cannot pay yourself!"
  Error-Console-Pay: "<red>❌ Console cannot use the pay command!"
  
  # Admin messages
  Admin-Give-Success: "<green>✅ Gave <yellow>%amount% %currency% <green>to <aqua>%player%"
  Admin-Take-Success: "<green>✅ Took <yellow>%amount% %currency% <green>from <aqua>%player%"
  Admin-Set-Success: "<green>✅ Set <aqua>%player%'s <green>balance to <yellow>%amount% %currency%"
  Admin-Reset-Success: "<green>✅ Reset <aqua>%player%'s <green>balance to <yellow>0 %currency%"
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