package com.example.groceryapp.database

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.groceryapp.models.OrderSummary
import com.example.groceryapp.models.Product
import com.example.groceryapp.models.Totals

class DBHelper(var context:Context):SQLiteOpenHelper(context, DATA_NAME, null, DATABASE_VERSION){

    companion object{
        const val DATA_NAME = "prodDB"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Cart"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "Product_Name"
        const val COLUMN_PRICE = "Price"
        const val COLUMN_MRP = "mrp"
        const val COLUMN_IMAGE = "Image"
        const val COLUMN_QUANTITY = "Quantity"


        const val createTable = "create table $TABLE_NAME ($COLUMN_ID CHAR(250) , $COLUMN_NAME CHAR(50),$COLUMN_PRICE INTEGER, $COLUMN_MRP INTEGER, $COLUMN_IMAGE CHAR(250) , $COLUMN_QUANTITY INTEGER )"

        const val dropTable = "drop table $TABLE_NAME"

    }
    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(dropTable)
        onCreate(db)
    }

    fun addProduct(product: Product) {
        if (isItemInCart(product)) {
            add1(product)
        } else {
            var database = writableDatabase
            var contentValues = ContentValues()
            contentValues.put(COLUMN_ID, product._id)
            contentValues.put(COLUMN_NAME, product.productName)
            contentValues.put(COLUMN_PRICE, product.price)
            contentValues.put(COLUMN_MRP, product.mrp)
            contentValues.put(COLUMN_IMAGE, product.image)
            contentValues.put(COLUMN_QUANTITY, product.quantity)
            database.insert(TABLE_NAME, null, contentValues)

        }
    }

    fun isItemInCart(product:Product):Boolean{
        val database = writableDatabase
        val query = "Select * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = database.rawQuery(query, arrayOf(product._id))
        var count = cursor.count
        return count != 0
    }

    fun getProduct():ArrayList<Product>{
        var productList:ArrayList<Product> = ArrayList()
        var database = writableDatabase
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_PRICE,
            COLUMN_MRP,
            COLUMN_IMAGE,
            COLUMN_QUANTITY
        )

        var cursor = database.query(TABLE_NAME, columns, null, null, null, null, null)
        if(cursor != null && cursor.moveToFirst()){
            do{
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                var price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE))
                var mrp = cursor.getInt(cursor.getColumnIndex(COLUMN_MRP))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))

                var product = Product(_id = id, productName = name, price = price.toDouble(),  mrp = mrp.toDouble(),  image = image,  quantity = quantity)
                productList.add(product)

            }while(cursor.moveToNext())
        }
        cursor.close()
        return productList
    }

    fun deleteProduct(id:String){
        var database = writableDatabase
        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(id)
        database.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun getTotal(mList:ArrayList<Product>):Totals{

        var total:Double
        var subtotal = 0.0
        var discount = 0.0
        for(i in 0 until mList.size){
            subtotal += mList[i].price * mList[i].quantity
            discount += (mList[i].mrp - mList[i].price) * mList[i].quantity
        }
        total = subtotal - discount
        return Totals(subtotal, discount, total)

    }

    fun add1(product: Product){
        var database = writableDatabase

        val columns = arrayOf(COLUMN_QUANTITY)
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(product._id)
        val cursor = database.query(TABLE_NAME, columns, whereClause, whereArgs, null, null, null)

        if(cursor != null && cursor.moveToFirst()){
            do{
                val count = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
                val contentValues = ContentValues()
                contentValues.put(COLUMN_QUANTITY, count+1)
                database.update(TABLE_NAME, contentValues, whereClause, whereArgs)
            }while(cursor.moveToNext())
        }
        cursor.close()
    }

    fun sub1(product: Product){
        var database = writableDatabase
        val columns = arrayOf(COLUMN_QUANTITY)
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(product._id)
        val cursor = database.query(TABLE_NAME, columns, whereClause, whereArgs, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val count = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
                val contentValues = ContentValues()
                contentValues.put(COLUMN_QUANTITY, count - 1)
                if(count-1 == 0){
                    deleteProduct(product._id!!)
                }
                database.update(TABLE_NAME, contentValues, whereClause, whereArgs)

            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    fun getCartTotalQuantity():Int{
        var database = writableDatabase
        var count = 0
        val columns = arrayOf(COLUMN_QUANTITY)
        var cursor = database.query(TABLE_NAME, columns, null, null, null, null, null)
        if(cursor != null && cursor.moveToFirst()){
            do{
                var qty = cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)).toInt()
                count = count + qty
            }while(cursor.moveToNext())
        }
        cursor.close()
        return count
    }

    fun contains(product: Product):Boolean{
        var database = writableDatabase

        val exist = DatabaseUtils.longForQuery(database,
        "Select count(*) from $TABLE_NAME where $COLUMN_ID = '${product._id}'",
        null)
        return exist >= 1
    }

    fun getOrderSummary(): OrderSummary {
        var database = writableDatabase
        var total = 0
        var price = 0
        var quantity = 0
        //var discount = 0
        var deliveryCharges = 0
        var columns = arrayOf(
            COLUMN_MRP,
            COLUMN_PRICE,
            COLUMN_QUANTITY
        )
        var cursor = database.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                total += cursor.getDouble(cursor.getColumnIndex(COLUMN_MRP)).toInt()
                price += cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)).toInt()
                quantity += cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
            } while (cursor.moveToNext())
        }
        cursor.close()

        total *= quantity
        price *= quantity
        var discount = total - price
        //discount=0

        if (price < 300)
            deliveryCharges = 30

        return OrderSummary(
            totalAmount = total,
            ourPrice = price,
            discount = discount,
            deliveryCharges = deliveryCharges
        )
    }

    fun plus1(product:Product){
        var database = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(product._id)
        var contentValues = ContentValues()
        contentValues.put(COLUMN_QUANTITY, (product.quantity))
        database.update(TABLE_NAME, contentValues,whereClause,whereArgs)
    }

    fun emptyCart() {
        var database = writableDatabase
        database.execSQL("delete from $TABLE_NAME")
    }


}