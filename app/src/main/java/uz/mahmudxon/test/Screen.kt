package uz.mahmudxon.test

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import kotlin.math.absoluteValue

object Screen : SensorEventListener {
    var currentOrientation = Orientation.PORTRAIT
    var move : ((Int) -> Unit)? = null
    var orientationListener : ((current : Int, next : Int) -> Unit)? = null

    object Orientation {
        const val PORTRAIT = 1
        const val LANDSCAPE = 2
        const val ON_THE_TABLE = 3
    }

    object Movement {
        const val NONE = 0
        const val LEFT = 1
        const val RIGHT = 2
        const val REVERSE = 3
    }

    private fun getOrientation(x: Float, y: Float): Int {
        if (x.absoluteValue + y.absoluteValue <= 8.0f) return Orientation.ON_THE_TABLE
        return if (x < -7 || x > 7) {
            if (x > 0) Orientation.LANDSCAPE else -Orientation.LANDSCAPE
        } else {
            if (y > 0) Orientation.PORTRAIT else -Orientation.PORTRAIT
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val x = event?.values?.get(0) ?: 0.0f
        val y = event?.values?.get(1) ?: 0.0f
        val orientation = getOrientation(x, y)
        onOrientationChanged(orientation)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    private fun onOrientationChanged(orientation: Int) {
        if (orientation != currentOrientation) {
            orientationListener?.invoke(currentOrientation, orientation)
            currentOrientation = orientation
        }
    }

    fun getDegrees(orientation: Int): Float{
        return when (orientation) {
            Orientation.PORTRAIT -> 0f
            Orientation.LANDSCAPE -> 90f
            -Orientation.LANDSCAPE -> -90f
            -Orientation.PORTRAIT -> 180f
            Orientation.ON_THE_TABLE -> -1f
            else -> 0f
        }
    }
}