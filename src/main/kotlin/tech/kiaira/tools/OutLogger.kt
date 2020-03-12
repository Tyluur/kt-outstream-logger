package tech.kiaira.tools

import java.io.OutputStream
import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * This class modifies previous [System.out] logging and prints them with information. We need to know which class
 * printed data and at what time at all times.
 *
 * @author Tyluur <contact@kiaira.tech>
 * @since April 9, 2015
 */
class OutLogger(
    /**
     * The pattern to use for date output
     */
    formatPattern: String = "MM.dd.yyyy hh:mm:ss.SSS",

    /**
     * The output stream that will be used
     */
    stream: OutputStream

) : PrintStream(stream) {

    /**
     * Gets the date in a formatted string.
     *
     * @return The date
     */
    private val dateFormat = SimpleDateFormat(formatPattern)

    override fun print(message: Boolean) {
        prettyLog(getStackTraceElement(), message.toString())
    }

    override fun print(message: Int) {
        prettyLog(getStackTraceElement(), message.toString())
    }

    override fun print(message: Long) {
        prettyLog(getStackTraceElement(), message.toString())
    }

    override fun print(message: Double) {
        prettyLog(getStackTraceElement(), message.toString())
    }

    override fun print(message: String) {
        prettyLog(getStackTraceElement(), message)
    }

    override fun print(message: Any) {
        prettyLog(getStackTraceElement(), message.toString())
    }

    /**
     * Converts the [StackTraceElement] instance to a detailed description of the source
     */
    private fun getStackTraceElement(): String {
        val element = getCallElement()
        val fileName = element.fileName ?: ""
        val endIndex = fileName.indexOf(".")
        return "${fileName.substring(
            0,
            (if (endIndex == -1) fileName.length else endIndex)
        )}:${element.lineNumber}#${element.methodName}"
    }

    /**
     * Outputs a pretty log
     *
     * @param description
     * The description of where its coming from
     * @param text
     * The text that is being outputted
     */
    private fun prettyLog(description: String, text: String) {
        super.print("[$description][${dateFormat.format(Date())}]  $text")
    }

    /**
     * Finds the source [StackTraceElement]
     */
    private fun getCallElement(): StackTraceElement {
        val elements = Thread.currentThread().stackTrace
        for (i in elements.indices) {
            val element = elements[i]
            if (element.toString().contains("java.io.PrintStream")) {
                val newIndex = i + 1
                if (newIndex >= elements.size) {
                    continue
                }
                return elements[newIndex]
            }
        }
        return elements[elements.size - 2]
    }

}