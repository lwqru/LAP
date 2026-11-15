package com.example.studyplanner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

// 1. The receiver: called when airplane mode changes
class AirplaneModeReceiver(
    private val onChange: (Boolean) -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val isOn = intent.getBooleanExtra("state", false)
        onChange(isOn)
    }
}

// Reads the setting once, for the first value
fun isAirplaneModeOn(context: Context): Boolean =
    Settings.Global.getInt(
        context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0
    ) != 0

// 2. Register while on screen, unregister when it leaves
@Composable
fun rememberAirplaneModeOn(): Boolean {
    val context = LocalContext.current
    var isOn by remember {
        mutableStateOf(isAirplaneModeOn(context))
    }
    DisposableEffect(context) {
        val receiver = AirplaneModeReceiver { newValue ->
            isOn = newValue
        }
        ContextCompat.registerReceiver(
            context,
            receiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED),
            ContextCompat.RECEIVER_EXPORTED   // sent by the system
        )
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }
    return isOn
}
