package uz.mahmudxon.test

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity(), SensorEventListener {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        Log.d(TAG, "onSensorChanged: event: ${event?.values?.asList()}")
        val x = event?.values?.get(0) ?: 0.0f
        val y = event?.values?.get(1) ?: 0.0f
        val z = event?.values?.get(2) ?: 0.0f
        val needChange = x.absoluteValue + y.absoluteValue > 8.0f

        if(needChange) {
            if (x < -7 || x > 7) {
                findViewById<TextView?>(R.id.textView)?.text = "Landscape"
            } else {
                findViewById<TextView?>(R.id.textView)?.text = "Portrait"
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d(TAG, "onAccuracyChanged: sensor: $sensor accuracy: $accuracy")
    }
}