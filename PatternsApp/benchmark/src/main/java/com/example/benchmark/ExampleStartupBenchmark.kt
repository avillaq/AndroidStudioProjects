package com.example.benchmark

import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MemoryUsageMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ListPerformanceBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun listInteractionBenchmark() = benchmarkRule.measureRepeated(
        packageName = "com.example.patternsapp",
        metrics = listOf(
            FrameTimingMetric(),
            MemoryUsageMetric(MemoryUsageMetric.Mode.Last)),
        iterations = 5,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
        }
    ) {
        startActivityAndWait()

        // click en boton
        val addButton = device.findObject(UiSelector().text("Agregar usuario"))
        addButton.waitForExists(5000)
        repeat(3) {
            addButton.click()
            device.waitForIdle()
        }

        // click en un item
        val firstItem = device.findObject(UiSelector().textContains("User 0"))
        repeat(2) {
            firstItem.click() // se expande
            device.waitForIdle()
            Thread.sleep(500)
            firstItem.click() // se contrae
            device.waitForIdle()
        }

        // scroll de la lista
        val list = UiScrollable(UiSelector().scrollable(true))
        // hacia abajo
        repeat(3) {
            list.flingForward()
            device.waitForIdle()
        }
        // hacia arriba
        repeat(3) {
            list.flingBackward()
            device.waitForIdle()
        }
    }
}