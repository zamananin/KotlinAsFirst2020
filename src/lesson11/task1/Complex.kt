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
fun Complex(s: String): Complex {
    val sFormatted = Regex("""\s""").replace(s, "")
    val a = Regex("""(-?\d+(?:\.\d+)?)?(?:([-+]\d+(?:\.\d+)?)i)?""").matchEntire(sFormatted)?.groupValues
    if ((a == null)) throw Exception("Illegal argument $sFormatted")
    val re = if (a[1] == "") 0.0 else a[1].toDouble()
    val im = if (a[2] == "") 0.0 else a[2].toDouble()
    return Complex(re, im)
}

class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Конструктор из строки вида x+yi
     */

//    constructor(s: String) : this(stringToComplex(s).re, stringToComplex(s).im)

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
        require(other != Complex(0.0, 0.0)) { "/ by zero" }
        return Complex(
            (re * other.re + im * other.im) / (other.re * other.re + other.im * other.im),
            (other.re * im - re * other.im) / (other.re * other.re + other.im * other.im)
        )
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        this === other ||
                other is Complex && (re == other.re) && (im == other.im)

    override fun hashCode() = re.hashCode() * 31 + im.hashCode()

    /**
     * Преобразование в строку
     */
    override fun toString() = when {
        im < 0 -> "$re${im}i"
        im > 0 -> "$re+${im}i"
        else -> "$re"
    }
}