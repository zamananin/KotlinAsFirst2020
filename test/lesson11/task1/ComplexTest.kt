package lesson11.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

internal class ComplexTest {

    private fun assertApproxEquals(expected: Complex, actual: Complex, eps: Double) {
        assertEquals(expected.re, actual.re, eps)
        assertEquals(expected.im, actual.im, eps)
    }

    @Test
    fun stringToComplex() {
        assertEquals(Complex(12.0, 14.0), Complex("12 + 14i"))
        assertEquals(Complex(12.0, 14.5), Complex("12.0 + 14.5i"))
        assertEquals(Complex(-12.0, -14.0), Complex("-      12 - 1 4  .00 i"))
        assertEquals(Complex(0.0, 0.0), Complex("0"))
        assertEquals(Complex(0.0, 0.0), Complex("-0"))
        assertEquals(Complex(0.0, -2.0), Complex("-2i"))
        assertEquals(Complex(0.4, 0.0), Complex("0.4"))
        assertEquals(Complex(0.0, 0.0), Complex("-0i"))
    }

    @Test
    @Tag("2")
    fun plus() {
        assertApproxEquals(Complex("4-2i"), Complex("1+2i") + Complex("3-4i"), 1e-10)
    }

    @Test
    @Tag("2")
    operator fun unaryMinus() {
        assertApproxEquals(Complex(1.0, -2.0), -Complex(-1.0, 2.0), 1e-10)
    }

    @Test
    @Tag("2")
    fun minus() {
        assertApproxEquals(Complex("4-2i"), Complex("1+2i") + Complex("3-4i"), 1e-10)
    }

    @Test
    @Tag("4")
    fun times() {
        assertApproxEquals(Complex("11+2i"), Complex("1+2i") * Complex("3-4i"), 1e-10)
    }

    @Test
    @Tag("4")
    fun div() {
        assertApproxEquals(Complex("2.6 + 0.8i"), Complex("11-8i") / Complex("3-4i"), 1e-10)
    }

    @Test
    @Tag("2")
    fun equals() {
        assertApproxEquals(Complex(1.0, 2.0), Complex("1+2i"), 1e-12)
        assertApproxEquals(Complex(1.0, 0.0), Complex(1.0), 1e-12)
    }
}