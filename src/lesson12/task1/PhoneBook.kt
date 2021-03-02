@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "Телефонная книга".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 14.
 * Объект класса хранит список людей и номеров их телефонов,
 * при чём у каждого человека может быть более одного номера телефона.
 * Человек задаётся строкой вида "Фамилия Имя".
 * Телефон задаётся строкой из цифр, +, *, #, -.
 * Поддерживаемые методы: добавление / удаление человека,
 * добавление / удаление телефона для заданного человека,
 * поиск номера(ов) телефона по заданному имени человека,
 * поиск человека по заданному номеру телефона.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class PhoneBook {
    private val people = mutableMapOf<String, Person>()
    private val phones = mutableMapOf<Phone, Person>()

    class Person(val name: String) {
        val phones = mutableSetOf<Phone>()

        fun addPhone(phone: String): Boolean = phones.add(Phone(phone))

        fun removePhone(phone: String): Boolean = phones.remove(Phone(phone))

        override fun toString(): String = name

        override fun equals(other: Any?): Boolean =
            this === other ||
                    (other is Person) && (name == other.name)

        override fun hashCode(): Int = name.hashCode()
    }

    class Phone(p: String) {
        private val phone: String

        init {
            require(Regex("""[\d+\-*#]+""").matches(p)) { "Illegal argument $p" }
            phone = p
        }

        override fun toString(): String = phone

        override fun equals(other: Any?): Boolean =
            this === other ||
                    (other is Phone) && (phone == other.phone)

        override fun hashCode(): Int = phone.hashCode()
    }

//    private fun getPerson(name: String): Person? {
//        for (person in people)
//            if (name == person.name)
//                return person
//        return null
//    }

    /**
     * Добавить человека.
     * Возвращает true, если человек был успешно добавлен,
     * и false, если человек с таким именем уже был в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun addHuman(name: String): Boolean {
        val newPerson = Person(name)

        return if (newPerson in people.values) false
        else {
            this.people[name] = newPerson
            true
        }
    }

    /**
     * Убрать человека.
     * Возвращает true, если человек был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun removeHuman(name: String): Boolean {
        return if (name !in people.keys) false
        else {
            people[name]!!.phones.forEach {
                phones.remove(it)
            }
            people.remove(name)
            true
        }
    }

    /**
     * Добавить номер телефона.
     * Возвращает true, если номер был успешно добавлен,
     * и false, если человек с таким именем отсутствовал в телефонной книге,
     * либо у него уже был такой номер телефона,
     * либо такой номер телефона зарегистрирован за другим человеком.
     */
    fun addPhone(name: String, phone: String): Boolean {
        val newPhone = Phone(phone)
        return if (newPhone in phones.keys || name !in people.keys) false
        else {
            phones[newPhone] = Person(name)
            people[name]!!.addPhone(phone)
            true
        }
    }

    /**
     * Убрать номер телефона.
     * Возвращает true, если номер был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * либо у него не было такого номера телефона.
     */
    fun removePhone(name: String, phone: String): Boolean {
        val thisPhone = Phone(phone)
        return if (people[name]?.removePhone(phone) == true) {
            phones.remove(thisPhone)
            true
        } else false
    }

    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> =
        people[name]?.phones?.map { it.toString() }?.toSet() ?: emptySet()

    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? = phones[Phone(phone)]?.toString()

    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */
    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is PhoneBook -> false
        phones == other.phones -> true
        else -> false
    }

    override fun hashCode(): Int = phones.hashCode()
}