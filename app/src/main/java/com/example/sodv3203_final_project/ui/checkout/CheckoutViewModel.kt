import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203_final_project.Data.Orders.OrderItemDao
import com.example.sodv3203_final_project.Data.MenuItem
import com.example.sodv3203_final_project.Data.MenuItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckoutViewModel(
    private val orderItemDao: OrderItemDao,
    private val menuItemDao: MenuItemDao
) : ViewModel() {

    private val _cartItems = mutableListOf<MenuItem>()
    val cartItems: List<MenuItem> get() = _cartItems

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val orderItems = orderItemDao.getOrderItemsByOrderId(orderId = 1) // Replace with dynamic order ID later
            val tempCartItems = mutableListOf<MenuItem>()

            for (orderItem in orderItems) {
                val menuItem = menuItemDao.getMenuItemById(orderItem.itemId)
                if (menuItem != null) {
                    repeat(orderItem.quantity) {
                        tempCartItems.add(menuItem)
                    }
                }
            }

            withContext(Dispatchers.Main) {
                _cartItems.clear()
                _cartItems.addAll(tempCartItems)
            }
        }
    }

    fun getCartTotal(): Double {
        return _cartItems.sumOf { it.price }
    }
}
