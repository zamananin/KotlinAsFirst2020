@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
private fun stringToComplex(s: String): Complex {
    val sFormatted = Regex("""\s""").replace(s, "")
    require(Regex("""-?\d+(\.\d+)?([-+]\d+(\.\d+)?i)?""").matches(sFormatted)) { "Incorrect format: $s" }
    return if (Regex("""-?\d+(\.\d+)?""").matches(sFormatted))
        Complex(sFormatted.toDouble())
    else {
        val re = Regex("""-?\d+(\.\d+)?""").find(sFormatted)
        val lastIndexOfRe = (Regex("""-?\d+(\.\d+)?""").find(sFormatted))!!.range.last
        val im = Regex("""-?\d+(\.\d+)?""").find(sFormatted, lastIndexOfRe + 1)
        val reStr = re!!.value
        val imStr = im!!.value
        Complex(reStr.toDouble(), imStr.toDouble())
    }

}

class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Конструктор из строки вида x+yi
     */

    constructor(s: String) : this(stringToComplex(s).re, stringToComplex(s).im)

    /**
     * Сложение.
     */
    operator fun plus(other: Complex) = Complex(
        re + other.re,
        im + other.im
    )

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus() = Complex(-re, -im)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex) = Complex(
        re - other.re,
        im - other.im
    )

    /**
     * Умножение
     */
    operator fun times(other: Complex) = Complex(
        re * other.re - im * other.im,
        re * other.im + other.re * im
    )

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex {
        require(other != Complex("0")) { "/ by zero" }
        return Complex(
            (re * other.re + im * other.im) / (other.re * other.re + other.im * other.im),
            (other.re * im - re * other.im) / (other.re * other.re + other.im * other.im)
        )
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        other is Complex && (re == other.re) && (im == other.im)

    override fun hashCode() = re.hashCode() + im.hashCode()

    /**
     * Преобразование в строку
     */
    override fun toString() = when {
        im < 0 -> "$re${im}i"
        im > 0 -> "$re+${im}i"
        else -> "$re"
    }
}