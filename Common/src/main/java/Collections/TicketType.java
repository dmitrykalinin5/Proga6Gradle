package Collections;

/**
 * Перечисление типов билетов.
 * Содержит три типа: VIP, USUAL и CHEAP.
 */
public enum TicketType {
    VIP,       // Тип билета: VIP
    USUAL,     // Тип билета: обычный
    CHEAP;     // Тип билета: дешёвый

    /**
     * Возвращает строковое представление всех типов билетов, разделённых запятой.
     *
     * @return Строка с перечислением всех типов билетов
     */
    public static String types() {
        StringBuilder list = new StringBuilder();
        for (var TicketType : values()) {
            list.append(TicketType.name()).append(", ");
        }
        return list.substring(0, list.length() - 2);
    }
}
