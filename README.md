# BeanStack — Modern POS Engine

> A lightweight, in-memory point-of-sale engine for coffee shops, built in Kotlin.

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9%2B-7F52FF?logo=kotlin)](https://kotlinlang.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## Description

BeanStack is a minimal, dependency-free point-of-sale (POS) engine for coffee shops, written in Kotlin. It models a fixed coffee menu, tracks individual orders and their preparation status, and computes order totals using precise decimal arithmetic. The project serves as a clear reference implementation of core POS business logic — useful for learning Kotlin, prototyping a larger shop-management system, or as a teaching example for handling currency calculations correctly.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features

- **Fixed, type-safe menu** — coffee types and prices are defined once in the `CoffeeType` enum (`Espresso`, `Latte`, `Cappuccino`), eliminating magic strings and pricing typos.
- **Accurate currency arithmetic** — all totals use `java.math.BigDecimal` with `HALF_UP` rounding to two decimal places, avoiding the floating-point rounding errors common with `Double`.
- **Order lifecycle tracking** — each `OrderItem` carries its own `isReady` flag, so pending and completed orders can be distinguished at any time.
- **Case-insensitive order completion** — `completeOrder` matches orders by display name regardless of case, and only affects orders that are not already marked ready.
- **Pending order overview** — `showPendingOrders` prints a console summary of every unprepared order with its price.

## Tech Stack

| Component | Technology |
|---|---|
| Language | Kotlin |
| Runtime | JVM (Java 17+) |
| Currency arithmetic | `java.math.BigDecimal` / `java.math.RoundingMode` |
| Dependencies | None — Kotlin and Java standard libraries only |

## Requirements

- JDK 17 or newer
- Kotlin compiler (`kotlinc`) 1.9 or newer — installable via [SDKMAN!](https://sdkman.io/) or the [official Kotlin releases](https://kotlinlang.org/docs/command-line.html)
- No external libraries are required; the project uses only the Kotlin and Java standard libraries.

## Installation

Clone the repository:

```bash
git clone https://github.com/eryks23/BeanStack-Modern-POS-Engine.git
cd BeanStack-Modern-POS-Engine
```

Compile the source file into a runnable JAR:

```bash
kotlinc src/main/kotlin/CoffeeShop.kt -include-runtime -d beanstack.jar
```

Run the application:

```bash
java -jar beanstack.jar
```

## Usage

`CoffeeShop.kt` includes a `main` function that demonstrates the full order flow: adding orders, calculating the total, completing an order, and listing the remaining pending orders.

```kotlin
fun main() {
    val myShop = CoffeeShop()
    myShop.addOrder(OrderItem(CoffeeType.ESPRESSO))
    myShop.addOrder(OrderItem(CoffeeType.LATTE))

    println("Total to pay: $${myShop.calculateTotal()}")
    myShop.completeOrder("Latte")
    myShop.showPendingOrders()
    println("Coffee Shop System is running!")
}
```

Expected output:

```text
Total to pay: $20.00
Order for Latte is ready
--- Pending Orders ---
Espresso - $8.00
Coffee Shop System is running!
```

## API Documentation

### `CoffeeType` (enum)

Defines the available beverages and their fixed prices.

| Constant | Display Name | Price |
|---|---|---|
| `ESPRESSO` | Espresso | 8.00 |
| `LATTE` | Latte | 12.00 |
| `CAPPUCCINO` | Cappuccino | 10.50 |

### `OrderItem` (data class)

```kotlin
data class OrderItem(
    val type: CoffeeType,
    var isReady: Boolean = false
)
```

| Member | Type | Description |
|---|---|---|
| `type` | `CoffeeType` | The beverage ordered. |
| `isReady` | `Boolean` | Whether the order has been prepared. Defaults to `false`. |
| `name` | `String` (computed) | Display name, derived from `type.displayName`. |
| `price` | `BigDecimal` (computed) | Price, derived from `type.price`. |

### `CoffeeShop`

Holds and manages the in-memory list of orders.

**`addOrder(item: OrderItem): Unit`**
Adds a new order to the shop's order list.
- `item` — the `OrderItem` to register.

**`calculateTotal(): BigDecimal`**
Sums the price of every order currently registered (ready or pending) and returns the result rounded to two decimal places using `RoundingMode.HALF_UP`.

**`completeOrder(typeName: String): Unit`**
Finds the first pending order whose display name matches `typeName` (case-insensitive) and marks it as ready. Prints a confirmation message, or `Order not found: {typeName}` if no matching pending order exists.
- `typeName` — display name of the coffee type to mark ready (e.g. `"Latte"`).

**`showPendingOrders(): Unit`**
Prints every order with `isReady == false`, formatted as `{name} - ${price}`.

## Project Structure

```text
BeanStack-Modern-POS-Engine/
├── src/
│   └── main/
│       └── kotlin/
│           └── CoffeeShop.kt   # Domain model, business logic, and entry point
├── LICENSE
└── README.md
```

## Testing

No automated test suite is included at this time. To verify behavior manually, run the application as shown in [Usage](#usage) and compare the console output against the [API Documentation](#api-documentation). Contributions that add unit tests (e.g., with `kotlin.test` or JUnit 5) covering `CoffeeShop`, `OrderItem`, and `CoffeeType` are welcome.

## Contributing

1. Fork the repository and create a feature branch.
2. Keep changes focused and follow idiomatic Kotlin style (prefer `val` over `var`, use data classes for simple models).
3. Add or update tests for any behavioral change.
4. Open a pull request with a clear description of the change and its motivation.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

## Contact

Maintained by [@eryks23](https://github.com/eryks23). For bug reports or feature requests, please open an issue on the [repository](https://github.com/eryks23/BeanStack-Modern-POS-Engine).
