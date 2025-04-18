import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203_final_project.Data.Orders.OrderItem
import com.example.sodv3203_final_project.Data.Orders.OrderItemDao
import com.example.sodv3203_final_project.Data.MenuItem
import com.example.sodv3203_final_project.Data.MenuItemDao
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val orderItemDao: OrderItemDao,
    private val menuItemDao: MenuItemDao
) : ViewModel() {

    private val _cartItems = mutableListOf<MenuItem>()
    val cartItems: List<MenuItem> get() = _cartItems

    init {
        loadCartItems()
    }

    // Load cart items based on the current user's order
    private fun loadCartItems() {
        viewModelScope.launch {
            // Replace with actual logic to get order items for the current user
            val orderItems = orderItemDao.getOrderItemsByOrderId(orderId = 1) // Replace `1` with the actual order ID

            _cartItems.clear()
            for (orderItem in orderItems) {
                // Get the MenuItem corresponding to the current OrderItem
                val menuItem = menuItemDao.getMenuItemById(orderItem.itemId)
                if (menuItem != null) {
                    // Add the MenuItem multiple times depending on its quantity
                    repeat(orderItem.quantity) {
                        _cartItems.add(menuItem)
                    }
                }
            }
        }
    }

    // Example method to get the total price of the cart
    fun getCartTotal(): Double {
        return _cartItems.sumOf { it.price }
    }
}
