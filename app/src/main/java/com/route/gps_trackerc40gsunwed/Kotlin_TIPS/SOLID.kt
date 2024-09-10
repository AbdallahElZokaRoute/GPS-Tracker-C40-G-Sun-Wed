package com.route.gps_trackerc40gsunwed.Kotlin_TIPS

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView.Adapter

// SOLID Design Principles

// Creational Design Patters (Builder Pattern & Factory Pattern)


/***
 *      SOLID
 *      'S'ingle Responsibility Principle
 *      'O'pen For Extension / Closed For Modification
 *            // Inheritance
 *      'L'iskov Substitution Principle
 *      'I'nterface Segregation
 *      'D'ependency Inversion Principle
 *
 */
// Bad Example

// 1- Maintain
// 2- Scalable

// 1-
data class Book(
    val id: String? = null,
    val title: String? = null,
    val publishedDate: String? = null
)

// 3-
class BookPersistentManager {
    // 3- Book Persistent Database
    fun saveBookIntoDatabase(book: Book) {
        // Todo
    }

    // 3- Book Persistent Database
    fun getBooksFromDatabase() {

    }
}

// 2-
class BookLogger {
    // 2- Book Printer - Logger
    fun printBookDetails(book: Book) {
        // prints id , title , published At

    }
}
///////////////////////////////////


fun printShape(shapeType: String) {
    when (shapeType) {
        "rectangle" -> {
            Log.e("TAG", "main: Rectangle Draw!")
        }

        "square" -> {
            Log.e("TAG", "main: Square Draw!")
        }

        "triangle" -> {
            Log.e("TAG", "main: Triangle Draw!")
        }

        "circle" -> {
            Log.e("TAG", "printShape: Circle Draw!")
        }

        else -> {
            Log.e("TAG", "main: Shape Unknown !")
        }
    }
}

fun printShapeV2() {
    val shape: Shape = Rectangle()
    shape.draw()

}

interface Shape {
    fun draw()
}

class Rectangle : Shape {
    override fun draw() {
        Log.e("TAG", "main: Rectangle Draw!")
    }
}

class Square : Shape {
    override fun draw() {
        Log.e("TAG", "main: Square Draw!")
    }
}

class Triangle : Shape {
    override fun draw() {
        Log.e("TAG", "main: Triangle Draw!")
    }
}

class Circle : Shape {
    override fun draw() {
        Log.e("TAG", "main: Circle Draw!")
    }
}
/////////////////////////////////////////////////////////

interface Bird {
    fun walk()
}

interface FlyableBird : Bird {
    fun fly()
}


class Penguin : Bird {
    override fun walk() {

    }
}

class Duck : FlyableBird {
    override fun fly() {

    }

    override fun walk() {

    }
}
//////////////////////////////////////////////////////////


interface OnClickListener {
    fun onClick()
}

interface OnDoubleClickListener {
    fun onDoubleClick()
}

interface OnPinchZoomListener {
    fun onPinchZoom()
}

interface OnSwipeListener {
    fun onSwipe()
}
/////////////////////////////////

class Calculator {
    val operation: CalculationOperation = Division()
}

interface CalculationOperation
class Add : CalculationOperation
class Sub : CalculationOperation
class Multiplication : CalculationOperation
class Division : CalculationOperation


//////////////////////////////////////
// Creational  Design Patterns   {Singleton  , Builder Pattern , Factory Pattern}

data class PersonalComputer private constructor(
    val cpu: String? = null,
    val motherBoard: String? = null,
    val ram: String? = null,
    val vga: String? = null,
    val screen: String? = null,
) {
    class Builder() {
        private var cpu: String? = null
        private var motherBoard: String? = null
        private var ram: String? = null
        private var vga: String? = null
        private var screen: String? = null
        fun cpu(name: String): Builder {
            cpu = name
            return this
        }

        fun motherBoard(name: String): Builder {
            motherBoard = name
            return this
        }

        fun ram(name: String): Builder {
            ram = name
            return this
        }

        fun vga(name: String): Builder {
            vga = name
            return this
        }

        fun screen(name: String): Builder {
            screen = name
            return this
        }

        fun build(): PersonalComputer {
            return PersonalComputer(cpu, motherBoard, ram, vga, screen)
        }
    }
}

fun main() {
    val personalComputer = PersonalComputer.Builder()
        .cpu("Intel Core I7-13700H")
        .vga("Nvidia RTX 2060")
        .screen("Samsung")
        .ram("ADATA 1x16GB 3200MHz")
        .motherBoard("Lenovo Motherboard")
        .build()
    // Cart -> E-Commerce Application
    val vehicle: Transport = SeaLogistics().createTransport() // Ship
    RoadLogistics().createTransport() // Truck
}


interface Transport
class Ship : Transport
class Truck : Transport
interface Logistics {
    fun createTransport(): Transport
}

class SeaLogistics : Logistics {
    override fun createTransport(): Transport {
        return Ship()
    }
}

class RoadLogistics : Logistics {
    override fun createTransport(): Transport {
        return Truck()
    }
}


// News App => APIs & Networking


