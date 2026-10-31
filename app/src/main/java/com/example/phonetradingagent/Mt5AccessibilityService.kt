package com.example.phonetradingagent

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class Mt5AccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Foundation only: observe package/window events. No taps, order entry,
        // order modification, or order closing are implemented in this version.
        val packageName = event?.packageName?.toString() ?: return
        if (packageName.contains("metatrader", ignoreCase = true) ||
            packageName.contains("metaquotes", ignoreCase = true)) {
            getSharedPreferences("agent_settings", MODE_PRIVATE).edit()
                .putString("last_mt5_event", "${event.eventType} @ ${System.currentTimeMillis()}").apply()
        }
    }
    override fun onInterrupt() {}
}
