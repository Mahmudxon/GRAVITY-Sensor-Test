package uz.mahmudxon.test

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    lateinit var constraintLayout: ConstraintLayout
    var sensorManager: SensorManager? = null
    var accelerometer: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        constraintLayout = findViewById(R.id.constraintLayout)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_GRAVITY)
        Screen.orientationListener = { current, next ->
            var c = Screen.getDegrees(current)
            var n = Screen.getDegrees(next)

            if (n == 180f) {
                n = if (c > 0f) 180f else -180f
            }
            if (c == 180f) {
                c = if (n > 0f) 180f else -180f
            }

            val anim =
                RotateAnimation(c, n, constraintLayout.width / 2f, constraintLayout.height / 2f)
            anim.duration = 500
            anim.fillAfter = true
            constraintLayout.startAnimation(anim)
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(Screen, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        sensorManager?.unregisterListener(Screen)
        super.onPause()
    }
}