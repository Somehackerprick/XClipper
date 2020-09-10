package com.kpstv.xclipper.extensions

class ConcatArrayList<T>(private val maxSize: Int): ArrayList<T>() {
    override fun add(element: T): Boolean {
        if (size == maxSize)
            removeAt(0)
        return super.add(element)
    }
}