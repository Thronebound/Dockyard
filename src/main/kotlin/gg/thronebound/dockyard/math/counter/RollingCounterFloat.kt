package gg.thronebound.dockyard.math.counter

import cz.lukynka.bindables.Bindable
import cz.lukynka.bindables.BindableDispatcher
import gg.thronebound.dockyard.scheduler.Scheduler
import kotlin.math.abs
import kotlin.time.Duration

class RollingCounterFloat(scheduler: Scheduler) : RollingCounter<Float>(scheduler) {

    override val value: Bindable<Float> = Bindable<Float>(0f)

    override var animatedDisplayValue: Float = value.value

    override fun getProportionalRollDuration(current: Float, new: Float): Duration {
        if (!isRollingProportional) return rollingDuration

        val difference = abs(new - current)
        return rollingDuration * difference.toDouble()
    }

    val rollDispatcher: BindableDispatcher<Float> = BindableDispatcher()

    init {
        value.valueChanged { event ->
            if(event.newValue == event.oldValue) return@valueChanged
            animationProvider.stop()

            animationProvider.start(getProportionalRollDuration(event.oldValue, event.newValue), rollingEasing) { progress ->
                val new = interpolate(event.oldValue, event.newValue, progress)
                if (animatedDisplayValue != new) {
                    animatedDisplayValue = new
                    rollDispatcher.dispatch(animatedDisplayValue)
                }
            }
        }
    }

    override fun interpolate(start: Float, end: Float, progress: Float): Float {
        return start + (end - start) * progress
    }
}