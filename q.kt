import java.math.BigDecimal
import java.math.RoundingMode

enum class CoffeeType(val displayName: String, val price: BigDecimal) {
    ESPRESSO("Espresso", BigDecimal("8.00")),
    LATTE("Latte", BigDecimal("12.00")),
    CAPPUCCINO("Cappuccino", BigDecimal("10.50"))
}

data class OrderItem(
    val type: CoffeeType,
    var isReady: Boolean = false
) {
    val name: String get() = type.displayName
    val price: BigDecimal get() = type.price
}

class CoffeeShop {
    private val orders = mutableListOf<OrderItem>()
    
    fun addOrder(item: OrderItem) {
        orders.add(item)
    }

    fun calculateTotal(): BigDecimal {
        return orders.fold(BigDecimal.ZERO) { acc, item -> acc.add(item.price) }
            .setScale(2, RoundingMode.HALF_UP)
    }
    
    fun completeOrder(typeName: String) {
        val order = orders.find { it.name.equals(typeName, ignoreCase = true) && !it.isReady }
        
        if (order != null) {
            order.isReady = true
            println("Order for $typeName is ready")
        } else {
            println("Order not found: $typeName")
        }
    }
    
    fun showPendingOrders() {
        println("--- Pending Orders ---")

        orders.filter { !it.isReady }.forEach {
            println("${it.name} - $${it.price}")
        }
    }
}

fun main() {

    val myShop = CoffeeShop()
    myShop.addOrder(OrderItem(CoffeeType.ESPRESSO))
    myShop.addOrder(OrderItem(CoffeeType.LATTE))
    
    println("Total to pay: $${myShop.calculateTotal()}")
    myShop.completeOrder("Latte")
    myShop.showPendingOrders()
    println("Coffee Shop System is running!")

}