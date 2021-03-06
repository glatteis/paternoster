package me.glatteis.paternoster

import me.glatteis.paternoster.operations.*
import me.glatteis.paternoster.operations.comparison.*
import me.glatteis.paternoster.operations.math.*
import me.glatteis.paternoster.operations.selfmodification.GetOperation
import me.glatteis.paternoster.operations.selfmodification.ModifyOperation
import java.util.*

object RAM {
    val variables = HashMap<String, Any>()                  //The variables currently stored
    var operation: Operation = PlaceholderOperation         //The ongoing operation
    var operationOnHold: Operation = PlaceholderOperation   //Operation on hold. If | is called, the current operation will be stored here
}

class Direction(var x: Int, var y: Int) : Cloneable {
    fun setDirection(arrow: Char) {
        when (arrow) {
            '←', '↤' -> set(0, -1)
            '↑', '↥' -> set(-1, 0)
            '→', '↦' -> set(0, 1)
            '↓', '↧' -> set(1, 0)
            '↖' -> set(-1, -1)
            '↗' -> set(-1, 1)
            '↘' -> set(1, 1)
            '↙' -> set(1, -1)
        }
    }

    fun set(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    public override fun clone(): Direction {
        return super.clone() as Direction
    }
}

class Location(var x: Int, var y: Int) : Cloneable {
    fun add(direction: Direction) {
        x += direction.x
        y += direction.y
    }

    public override fun clone(): Location {
        return super.clone() as Location
    }
}

fun findOperation(initChar: Char): Operation? {
    when (initChar) {
        ' ' -> return null //Skip whitespace
        'A' -> return AssignOperation()
        '"' -> return StringOperation()
        'P' -> return PrintOperation()
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.' -> return NumberOperation()
        '+' -> return AddOperation()
        '-' -> return SubOperation()
        '*' -> return MulOperation()
        '/' -> return DivOperation()
        '%' -> return ModuloOperation()
        '?' -> return ConditionOperation()
        '=' -> return EqualsOperation()
        '>' -> return BiggerThanOperation()
        '<' -> return SmallerThanOperation()
        '!' -> return NotOperation()
        'M' -> return ModifyOperation()
        'G' -> return GetOperation()
        'Y' -> return InputOperation()
        'X' -> System.exit(0)
    }
    return NamespaceOperation()
}