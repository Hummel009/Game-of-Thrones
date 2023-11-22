<div align = center>

<br>

[![Badge License]][License]
![Badge Contributions]

<br>

![Logo]

<br>

[![Button Curse]][Curse]   
[![Button Wiki]][Wiki]

<br>

</div>

Мод "Игра престолов" для Minecraft - глобальное дополнение, которое превращает Minecraft в RPG c открытым миром. Вас ждёт множество новых блоков, предметов, оружия, мобов и структур. Исследуйте регионы мира, посещая города и замки, добывая новые руды, встречаясь как с друзьями, так и с врагами. Торгуйте, зарабатывайте достижения, выполняйте задания, собирайте армию. Выберите, на чьей стороне вы хотите сражаться!

## Общая информация

Этот репозиторий - проект Gradle, который должен быть открыт через IntelliJ IDEA или импортирован в Eclipse IDE.

| Технология | Версия  | Пояснение                                     |
|------------|---------|-----------------------------------------------|
| Gradle     | 8.4-bin | Версия системы автоматической сборки          |
| Gradle JVM | 17.0.9  | Версия Java, используемая для запуска Gradle  |
| JDK        | 17.0.9  | Версия Java, используемая для запуска проекта |
| Java       | 8       | Синтаксис Java, используемый в проекте        |

## Установка

Первым делом нужно скачать репозиторий и разархивировать его в любое место на диске. Если всё сделано правильно, вы должны увидеть папку, в которой находятся файлы `build.gradle.kts`, `settings.gradle.kts` и другие. Полученную папку будем называть ***папкой проекта***.

### IntelliJ IDEA

Запустите IntelliJ IDEA и откройте папку проекта: `File -> Open -> [Выбираете папку] -> OK`. Сразу после открытия начнётся установка среды. Если от вас потребуется разрешение на скачивание файлов, дайте его. Спустя некоторое время все необходимые файлы скачаются, и среда будет готова к работе.

Если на этом моменте что-то пошло не так и среда не установилась, значит, самое время проверить значения, указанные в таблице из первого раздела. Где их настроить:
* Gradle JVM: `File -> Settings -> Build -> Build Tools -> Gradle -> Gradle JVM`;
* JDK: `File -> Project Structure -> Project -> SDK`;
* Java: `File -> Project Structure -> Project -> Language Level`.

После изменения этих значений необходимо перезагрузить проект Gradle. Это можно сделать в ***меню Gradle***: `View -> Tool Windows -> Gradle`, нажав на значок перезагрузки в появившемся справа меню.

### Eclipse IDE

Запустите Eclipse IDE и импортируйте папку проекта: `File -> Import -> Gradle -> Existing Gradle Project -> Next -> [Выбираете папку] -> Finish`. Сразу после импорта начнётся установка среды. Если от вас потребуется разрешение на скачивание файлов, дайте его. Спустя некоторое время все необходимые файлы скачаются, и среда будет готова к работе.

Если на этом моменте что-то пошло не так и среда не установилась, значит, самое время проверить значения, указанные в таблице из первого раздела. Где их настроить:
* Gradle JVM: *переменные среды ОС, а именно JAVA_HOME и Path*;
* JDK: `Project -> Properties -> Java Build Path -> Libraries -> [нажимаете на JRE System Library] -> Remove -> Add Library -> JRE System Library -> Next -> Alternate JRE -> Installed JREs -> Add -> Standard VM -> Next -> [Выбираете JRE home] -> Finish -> Apply and close -> [в выпадающем меню справа от Alternate JRE выбираете нужную JRE] -> Finish -> Apply and close`;
* Java: `Project -> Properties -> Java Compiler -> [Галочка на Enable project specific settings] -> [Выставляете Compiler compliance level] -> Apply and close`.

После изменения этих значений необходимо перезагрузить проект Gradle. Это можно сделать, нажав слева (под панелью Package Explorer) ПКМ по названию проекта и выбрав `Gradle -> Refresh Gradle Project`. После перезагрузки проекта в нижней части окна появится ***меню Gradle***. 

## Основы работы

После установки среды весь необходимый инструментарий готов к работе. Вот самые важные команды, необходимые каждому разработчику:

* Генерация исходного кода Minecraft: `Меню Gradle -> modded minecraft -> setupDecompWorkspace`.
* Запуск клиента из среды: `Меню Gradle -> modded minecraft -> runClient`.
* Запуск локального сервера из среды: `Меню Gradle -> modded minecraft -> runServer`. К нему можно подключиться из клиента, введя в качестве адреса `localhost`.
* Компиляция мода в файл с расширением .jar: `Меню Gradle -> build -> build`. После компиляции ваш мод появится в папке `папка_проекта/build/libs`. Вас интересует тот файл, который без приписки `-dev.jar`.

Все команды, независимо от того, Eclipse IDE вы используете или IntelliJ IDEA, запускаются двойным кликом мыши в меню Gradle.

<!----------------------------------------------------------------------------->

[License]: LICENSE
[Curse]: https://www.curseforge.com/minecraft/mc-mods/gotminecraftmod
[Logo]: src/main/resources/assets/got/logo.png
[Wiki]: https://gotminecraftmod.fandom.com/ru/wiki/%D0%9C%D0%BE%D0%B4_%22%D0%98%D0%B3%D1%80%D0%B0_%D0%BF%D1%80%D0%B5%D1%81%D1%82%D0%BE%D0%BB%D0%BE%D0%B2%22_%D0%B4%D0%BB%D1%8F_Minecraft_%D0%B2%D0%B8%D0%BA%D0%B8


<!----------------------------------[ Badges ]--------------------------------->

[Badge Contributions]: https://img.shields.io/badge/Contributions-Welcome-3d6c23.svg?style=for-the-badge&labelColor=569A31
[Badge License]: https://img.shields.io/badge/License-GPL_3-0167a0.svg?style=for-the-badge&labelColor=blue


<!---------------------------------[ Buttons ]--------------------------------->

[Button Curse]: https://img.shields.io/badge/Download-f16436.svg?style=for-the-badge&logoColor=white&logo=CurseForge
[Button Wiki]: https://img.shields.io/badge/Wiki-FA005A.svg?style=for-the-badge&logoColor=white&logo=Fandom
