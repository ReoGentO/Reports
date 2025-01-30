# REPORTS

### What is this?

- This is a reporting plugin for the Minecraft server.

### You'd think there's a lot of plugins like that!

- Yes, there are plenty of such plugins on the Internet, but mine is different in that it has a tracking system and good customization!

### Wow! I'll go download it!

- You can download it [here](https://rubukkit.org/threads/sec-reports-v0-5b-reporty-s-sistemoj-nabljudenija-1-16-1-19.194459/). The plugin is currently only on the Russian Bukkit website, and a transition to Spigot is planned soon.

## Default gui_settings.yml

```yaml
# 
# +----------------------------------------------------+ #
# <               Конфигурация GUI и др.               > #
# <       Telegram: https://t.me/uwuweweweonotwe       > #
# <             Discord: TheBlackInfinity              > #
# +----------------------------------------------------+ #


# Текст
gui:
  name: <gradient:#FF0000:#1800FF>Репорты</gradient>
  border_item:
    material: ORANGE_STAINED_GLASS_PANE
    name: ' '
  report_item:
    material: PAPER
    name: '<gray>Репорт ID: <aqua>{id}'
    lore:
    - ' <gray>От: <green>{reporter_player}'
    - ' <gray>Нарушитель: <red>{reported_player}'
    - ' <gray>Причина: <white>{reason}'
    - ' <gray>Дата: <yellow>{date}'
    - ' '
    - ' <gray><i>Нажмите <yellow>СКМ <gray>для удаления.'
    - ' <gray><i>Нажмите <yellow>ЛКМ <gray>для наблюдения.</i>'
    - ' <gray><i>Нажмите <yellow>ПКМ <gray>чтобы ответить.</i>'
  next_page_item:
    material: ARROW
    name: <gradient:#9F9F9F:#515151>Сл. страница</gradient>
    lore_has:
    - <gray>Перейти на страницу {next_page}/{all_pages}
    lore_none:
    - <gray>Больше страниц нету!
  prev_page_item:
    material: ARROW
    name: <gradient:#9F9F9F:#515151>Пред. страница</gradient>
    lore_has:
    - <gray>Перейти на страницу {prev_page}/{all_pages}
    lore_none:
    - <gray>Больше страниц нету!
```

## Default main.yml

```yaml
# 
# +----------------------------------------------------+ #
# <      Плагин на репорты от ReoGenT со слежкой!      > #
# <       Telegram: https://t.me/uwuweweweonotwe       > #
# <             Discord: TheBlackInfinity              > #
# +----------------------------------------------------+ #


# If you want to create your own language file,
# enter the code that you entered in the file name
# (lang-{Here will be your code}.yml)
lang: RU

# Кулдаун между репортами
# Введите -1 для отключения
cooldown: 20

# Если true, то игроки могут репортить сами себя
self_reports: false

# Если false, то при выходе из режима слежки
# игрок не будет телепортирован обратно
back_teleportation: true
```

## Default lang-RU.yml
```yaml
# 
# +----------------------------------------------------+ #
# <                   Языковой файл                    > #
# <       Telegram: https://t.me/uwuweweweonotwe       > #
# <             Discord: TheBlackInfinity              > #
# +----------------------------------------------------+ #

prefix: '<dark_aqua>[<aqua><b>Репорты</b><dark_aqua>]<reset> '
messages:
  enter_spy: <gradient:#0064FF:#00FFC1>Вы вошли в режим слежки за игроком!</gradient>
  delete_report: '<color:#4affcc>Репорт с ID: </color><color:#c8ffab>{id}</color>
    <color:#4affcc>был удален!</color>'
  report_announcement: <b><i><color:#ff8119>Пришел новый репорт. Быстро проверять!</color></i></b>
  report_message: |-
    <reset>
    <gray><-> <dark_aqua>< <st>                                            </st> ><reset>
    <gray><-> <aqua>Вы отправили репорт на игрока <dark_red>{player}<reset>
    <gray><-> <b><aqua>Причина:</aqua></b> <hover:show_text:'<red>{reason}</red>'><i><yellow>(наведите)</yellow></i></hover><reset>
    <gray><-> <dark_aqua>< <st>                                            </st> ><reset>
    <reset>
  cooldown_message: <gradient:#CF7A7A:#FF0000>Пожалуйста, потождите {time} секунд
    перед следующим отправлением команды!</gradient>
  find_error: <gradient:#5F0000:#FF0000>Игрока нету на сервере!</gradient>
  no_perm: <color:#ff9994>У Вас нет прав для просмотра репортов.</color>
  self_message: <color:#ff9994>Сам на себя наговариваешь?</color>
  usage: '<gradient:#7EC7F7:#1D8ADC>Используйте: /report <игрок> <причина></gradient>'
  exit_spy: <gradient:#7ACF7C:#00FF4F>Вы вышли из режима наблюдения.</gradient>
  spy_time: <gradient:#FFC2C2:#FF6464>{time}</gradient>
  reload_message: <gradient:#C5C2FF:#64FFBA>Конфигурация перезагружена!</gradient>
  answers:
    answering_format: <gradient:#FF6700:#DC721D>[Ответ от администратора] </gradient><color:#874b33><{admin_name}></color>
      <gradient:#943C00:#917259>{message}</gradient>
    answering_message: |
      <red><-> <st>                                         </st> <-></red><reset>

      <color:#c46205><></color> <gradient:#C46205:#FF5D00>Вы взялись за репорт игрока {reporter}</gradient><reset>
      <color:#c46205><></color> <gradient:#C46205:#FF5D00>Напишите ответ в чат или [Отмена], чтобы выйти:</gradient>
    answer_exit: <gradient:#C46205:#FF5D00><> Вы отказались от ответа!</gradient>
    answer_notify_exit: <gradient:#C46205:#FF5D00><> Администратор отказался от ответа!</gradient>
    answer_notify_player: '<gradient:#C46205:#FF5D00><> Администратор {player} взялся
      за ваш репорт! (ID: {id})</gradient>'
```
