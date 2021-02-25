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
    private val people = mutableMapOf<Person, MutableSet<Phone>>()
    private val phones = mutableMapOf<Phone, Person>()

    class Person(val name: String) {
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

        return if (newPerson in this.people.keys) false
        else {
            this.people[newPerson] = mutableSetOf()
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
        val newPerson = Person(name)
        return if (newPerson !in people.keys) false
        else {
            people[newPerson]!!.forEach {
                phones.remove(it)
            }
            people.remove(newPerson)
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
        return if (Phone(phone) in phones.keys || Person(name) !in people.keys) false
        else {
            phones[Phone(phone)] = Person(name)
            people[Person(name)]!!.add(Phone(phone))
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
        return if (humanByPhone(phone) != name) false
        else {
            people[Person(name)]!!.remove(Phone(phone))
            phones.remove(Phone(phone), Person(name))
            true
        }
    }

    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> =
        people[Person(name)]?.map { it.toString() }?.toSet() ?: emptySet()

    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? = if (phones[Phone(phone)] == null) null
    else phones[Phone(phone)].toString()

    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */
    override fun equals(other: Any?): Boolean = when{
        this === other -> true
        other !is PhoneBook -> false
        phones == other.phones -> true
        else -> false
    }

    override fun hashCode(): Int = people.hashCode()
}