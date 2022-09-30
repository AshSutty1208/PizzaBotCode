package com.ash.pizzabot.data.utils

class PepperoniLogger {
    companion object {
        fun debug(tag: String, message: String) {
            println("DEBUG: $tag : $message")
        }
    }
}