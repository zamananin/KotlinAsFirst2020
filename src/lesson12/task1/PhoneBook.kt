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
    private val people = mutableSetOf<Person>()

    class Person(val name: String) {
        val phones = mutableSetOf<Phone>()

        fun addPhone(phone: String): Boolean {
            val newPhone = Phone(phone)
            return if (newPhone in this.phones) false
            else {
                this.phones.add(newPhone)
                true
            }

        }

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

    private fun getPerson(name: String): Person? {
        for (person in people)
            if (name == person.name)
                return person
        return null
    }

    /**
     * Добавить человека.
     * Возвращает true, если человек был успешно добавлен,
     * и false, если человек с таким именем уже был в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun addHuman(name: String): Boolean {
        val newPerson = Person(name)

        return if (newPerson in this.people) false
        else {
            this.people.add(newPerson)
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
        return this.people.remove(newPerson)

    }

    /**
     * Добавить номер телефона.
     * Возвращает true, если номер был успешно добавлен,
     * и false, если человек с таким именем отсутствовал в телефонной книге,
     * либо у него уже был такой номер телефона,
     * либо такой номер телефона зарегистрирован за другим человеком.
     */
    fun addPhone(name: String, phone: String): Boolean =
        (humanByPhone(phone) == null) && (getPerson(name)?.addPhone(phone) ?: false)

    /**
     * Убрать номер телефона.
     * Возвращает true, если номер был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * либо у него не было такого номера телефона.
     */
    fun removePhone(name: String, phone: String): Boolean {
        val person = getPerson(name) ?: return false
        return person.phones.remove(Phone(phone))
    }

    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> =
        getPerson(name)?.phones?.map { it.toString() }?.toSet() ?: emptySet()

    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? {
        for (person in people) {
            if (Phone(phone) in person.phones) return person.name
        }
        return null
    }

    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneBook) return false
        if (people != other.people) return false
        for (person in people) {
            if (person.phones != other.getPerson(person.name)!!.phones) return false
        }
        return true
    }

    override fun hashCode(): Int = people.hashCode()
}