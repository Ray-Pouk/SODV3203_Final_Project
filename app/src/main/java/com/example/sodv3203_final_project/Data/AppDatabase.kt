package com.example.sodv3203_final_project.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sodv3203_final_project.Data.Orders.Order
import com.example.sodv3203_final_project.Data.Orders.OrderCustomization
import com.example.sodv3203_final_project.Data.Orders.OrderCustomizationDao
import com.example.sodv3203_final_project.Data.Orders.OrderDao
import com.example.sodv3203_final_project.Data.Orders.OrderItem
import com.example.sodv3203_final_project.Data.Orders.OrderItemDao

@Database(
    entities = [
        User::class,
        MenuItem::class,
        Order::class,
        OrderItem::class,
        OrderCustomization::class,
        MenuCategory::class,
        Rating::class
    ],
    version = 4,

)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun menuItemDao(): MenuItemDao
    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao
    abstract fun orderCustomizationDao(): OrderCustomizationDao
    abstract fun menuCategoryDao(): MenuCategoryDao
    abstract fun ratingDao(): RatingDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration() // Optional: Use this for migrations (if you change your schema)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

}









