package com.reogent.reports.Config;

public enum Messages {
    PREFIX("prefix", "<dark_aqua>[<aqua><b>Репорты</b><dark_aqua>]<reset> "),
    ENTER_SPY("messages.enter_spy", "<gradient:#0064FF:#00FFC1>Вы вошли в режим слежки за игроком!</gradient>"),
    DELETE_REPORT("messages.delete_report", "<color:#4affcc>Репорт с ID: </color><color:#c8ffab>{id}</color> <color:#4affcc>был удален!</color>"),
    REPORT_ANNOUNCEMENT("messages.report_announcement", "<b><i><color:#ff8119>Пришел новый репорт. Быстро проверять!</color></i></b>"),
    REPORT_MESSAGE("messages.report_message", "<reset>\n<gray><-> <dark_aqua>< <st>                                            </st> ><reset>\n<gray><-> <aqua>Вы отправили репорт на игрока <dark_red>{player}<reset>\n<gray><-> <b><aqua>Причина:</aqua></b> <hover:show_text:'<red>{reason}</red>'><i><yellow>(наведите)</yellow></i></hover><reset>\n<gray><-> <dark_aqua>< <st>                                            </st> ><reset>\n<reset>"),
    COOLDOWN_MESSAGE("messages.cooldown_message", "<gradient:#CF7A7A:#FF0000>Пожалуйста, потождите {time} секунд перед следующим отправлением команды!</gradient>"),
    FIND_ERROR("messages.find_error", "<gradient:#5F0000:#FF0000>Игрока нету на сервере!</gradient>"),
    NO_PERM("messages.no_perm", "<color:#ff9994>У Вас нет прав для просмотра репортов.</color>"),
    SELF_REPORT_M("messages.self_message", "<color:#ff9994>Сам на себя наговариваешь?</color>"),
    USAGE("messages.usage", "<gradient:#7EC7F7:#1D8ADC>Используйте: /report <игрок> <причина></gradient>"),
    EXIT_SPY("messages.exit_spy", "<gradient:#7ACF7C:#00FF4F>Вы вышли из режима наблюдения.</gradient>"),
    SPY_TIME("messages.spy_time", "<gradient:#FFC2C2:#FF6464>{time}</gradient>"),
    RELOAD_MESSAGE("messages.reload_message", "<gradient:#C5C2FF:#64FFBA>Конфигурация перезагружена!</gradient>"),

    ANSWERING_FORMAT("messages.answers.answering_format", "<gradient:#FF6700:#DC721D>[Ответ от администратора] </gradient><color:#874b33><{admin_name}></color> <gradient:#943C00:#917259>{message}</gradient>"),
    ANSWERING_MESSAGE("messages.answers.answering_message", "<red><-> <st>                                         </st> <-></red><reset>\n\n<color:#c46205><></color> <gradient:#C46205:#FF5D00>Вы взялись за репорт игрока {reporter}</gradient><reset>\n<color:#c46205><></color> <gradient:#C46205:#FF5D00>Напишите ответ в чат или [Отмена], чтобы выйти:</gradient>\n"),
    ANSWER_EXIT("messages.answers.answer_exit", "<gradient:#C46205:#FF5D00><> Вы отказались от ответа!</gradient>"),
    ANSWER_NOTIFY_EXIT("messages.answers.answer_notify_exit", "<gradient:#C46205:#FF5D00><> Администратор отказался от ответа!</gradient>"),
    ANSWER_NOTIFY_PLAYER("messages.answers.answer_notify_player", "<gradient:#C46205:#FF5D00><> Администратор {player} взялся за ваш репорт! (ID: {id})</gradient>")
    ;

    private String path;
    private Object def;

    Messages(String path, Object value) {
        this.path = path;
        this.def = value;
    }

    public Object getDefault()
    {
        return this.def;
    }

    public String getPath()
    {
        return this.path;
    }
}
