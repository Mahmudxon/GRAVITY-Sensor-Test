package uz.mahmudxon.test

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.image)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        sensorManager.registerListener(Screen, sensor, SensorManager.SENSOR_DELAY_UI)
        Screen.orientationListener = { current, next ->
            var c = Screen.getDegrees(current)
            var n = Screen.getDegrees(next)

            if(n == 180f)
            {
                n = if(c > 0f) 180f else -180f
            }
            if(c == 180f)
            {
                c = if(n > 0f) 180f else -180f
            }

            val anim = RotateAnimation(c, n, image.width / 2f, image.height / 2f)
            anim.duration = 500
            anim.fillAfter = true
            image.startAnimation(anim)
        }
    }
}