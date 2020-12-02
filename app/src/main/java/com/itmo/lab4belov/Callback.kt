package com.itmo.lab4belov

import android.content.Intent

interface Callback {
    fun startByCallback(callback: (Intent?, Int?) -> Unit)
}