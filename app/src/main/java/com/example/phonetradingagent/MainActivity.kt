package com.example.phonetradingagent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import android.graphics.Typeface

class MainActivity : Activity() {
    private lateinit var status: TextView
    private lateinit var activityText: TextView
    private lateinit var prefs: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = getSharedPreferences("agent_settings", MODE_PRIVATE)
        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(28, 28, 28, 28)
            setBackgroundColor(0xFF101820.toInt())
        }
        fun label(value: String, size: Float = 16f): TextView = TextView(this).apply {
            text = value; textSize = size; setTextColor(0xFFF2F5F7.toInt())
            setPadding(0, 10, 0, 10)
        }
        fun field(title: String, key: String, default: String): EditText {
            page.addView(label(title))
            return EditText(this).apply {
                setSingleLine(true); setText(prefs.getString(key, default))
                setTextColor(0xFFFFFFFF.toInt()); setHintTextColor(0xFFB0BEC5.toInt())
                hint = title; setBackgroundColor(0xFF263746.toInt())
                setPadding(14, 10, 14, 10); page.addView(this)
            }
        }
        page.addView(label("PHONE TRADING AGENT", 23f).apply { setTypeface(null, Typeface.BOLD) })
        status = label("AGENT STATUS: INACTIVE", 18f); page.addView(status)
        activityText = label("Activity: Not running"); page.addView(activityText)
        page.addView(Button(this).apply {
            text = "ACTIVATE MONITORING"
            setOnClickListener {
                startForegroundService(Intent(this@MainActivity, AgentForegroundService::class.java))
                prefs.edit().putBoolean("active", true).apply(); refreshStatus()
            }
        })
        page.addView(Button(this).apply {
            text = "DEACTIVATE MONITORING"
            setOnClickListener {
                stopService(Intent(this@MainActivity, AgentForegroundService::class.java))
                prefs.edit().putBoolean("active", false).apply(); refreshStatus()
            }
        })
        val tp = field("Take Profit (points)", "tp", "300")
        val sl = field("Stop Loss (points)", "sl", "200")
        val trail = field("Trailing activation (points)", "trail", "450")
        val distance = field("Trailing distance (points)", "distance", "50")
        val risk = field("Maximum risk per trade (%)", "risk", "1.0")
        val pairs = field("Allowed symbols (comma separated)", "pairs", "EURUSD,GBPUSD,USDJPY")
        page.addView(Button(this).apply {
            text = "SAVE CONFIGURATION"
            setOnClickListener {
                prefs.edit().putString("tp", tp.text.toString()).putString("sl", sl.text.toString())
                    .putString("trail", trail.text.toString()).putString("distance", distance.text.toString())
                    .putString("risk", risk.text.toString()).putString("pairs", pairs.text.toString()).apply()
                Toast.makeText(this@MainActivity, "Configuration saved on this phone", Toast.LENGTH_SHORT).show()
            }
        })
        page.addView(Button(this).apply {
            text = "OPEN ACCESSIBILITY SETTINGS"
            setOnClickListener { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
        })
        page.addView(label("PERFORMANCE / INFORMATION", 18f).apply { setTypeface(null, Typeface.BOLD) })
        page.addView(label("Trade execution: DISABLED in this starter build\nOpen positions: Not connected\nAI/API: Not connected\nMT5 automation: Not enabled\n\nThis build is a UI and Android-service foundation only."))
        ScrollView(this).also { it.addView(page); setContentView(it) }
        refreshStatus()
    }

    private fun refreshStatus() {
        val active = getSharedPreferences("agent_settings", MODE_PRIVATE).getBoolean("active", false)
        status.text = if (active) "AGENT STATUS: MONITORING (STARTER)" else "AGENT STATUS: INACTIVE"
        activityText.text = if (active) "Activity: Local foreground service requested" else "Activity: Not running"
    }
}
