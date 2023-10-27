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

Приведённая ниже документация будет полезна как разработчикам, так и мне самому из будущего. Сведения, гайды, подводные камни, советы и всё такое. 

<h2> Общая информация </h2>

* Этот репозиторий - проект Gradle, который должен быть открыт через IntelliJ IDEA или импортирован в Eclipse IDE.
* Используемый синтаксис Java - 8.
* Используемая версия Gradle - 8.4.
* Используемая версия ForgeGradle – Anatawa12's 1.2-1.1.+.
* Используемая версия JDK - Eclipse Temurin 1.8.0_392.

Обязательно проконтролируйте, чтобы ваш синтаксис Java и ваша JDK были такими, как сказано выше. Ваша IDE и переменные среды вашей ОС должны быть правильно сконфигурированы, иначе ничего не будет работать.

* JDK, сконфигурированный в IDE, отвечает за то, какие конструкции языка вы можете использовать в коде мода и какой на выходе получится байткод (который должен быть совместим с ожиданиями Forge).
* Синтаксис, сконфигурированный в IDE, отвечает за то, какие конструкции языка вы можете использовать (чтобы случайно не получить на выходе байткод, который будет нечитаемым и крашнет Forge). В некоторых случаях вы можете выбрать более новую JDK и более старый синтаксис, чтобы ограничить себя.
* JDK, сконфигурированный в переменных средах ОС, отвечает за то, какой джавой будет запущена сборка мода и загрузка среды.
* JDK в Eclipse называется JRE/JDK, в IntelliJ IDEA - SDK. Допуск синтаксиса в Eclipse называется `Compiler Compliance Level`, в IntelliJ IDEA - `Language Level`.

Краткий ответ на вопрос "как и где настраивать эти значения?":

* Переменные среды Windows (у меня Windows 11, так что у вас может что-то отличаться):
  * Первый способ: `ЛКМ по "Мой компьютер" -> Свойства -> Дополнительные параметры системы -> Переменные среды`.
  * Второй способ: `Параметры -> Система -> О системе -> Дополнительные параметры системы -> Переменные среды`.
  * Когда вы всё-таки добрались до переменных сред, создайте там две переменные: `JAVA_HOME` и `Path`. Возможно, они уже существуют, тогда дважды нажмите на них и смотрите, что там написано.
    * В JAVA_HOME должна быть только одна строка, содержащая путь к вашей JDK. У меня это `C:\Users\Hummel009\.jdks\temurin-1.8.0_392`
	* В Path, помимо других строк, должна быть как минимум одна строка, содержащая путь вашей JDK\bin. У меня это `C:\Users\Hummel009\.jdks\temurin-1.8.0_392\bin`
* Eclipse IDE:
  * Сверху справа находится лупа, через которую можно попасть куда угодно, введя `Compliance` или `Installed JREs`.
    * В первом случае вас интересует выдача `Compiler`, где вы сверху выставляете число.
	* Во втором случае вас интересует `Installed JREs | Open the preferences dialog`. Там вы можете выбрать и удалить текущую JRE, чтобы потом добавить новую `Standard VM` и указать место на диске, где лежит ваша Java, после чего подтвердить и выбрать её.
  * Если без лупы, то сверху выбираете `Window -> Preferences -> Java` и там выбираете либо `Compiler`, либо `Installed JREs`.
  * Если у вас в среде много проектов, вы можете настроить каждый отдельно. Для этого нажмите слева на проект и сверху пройдите по пути `Project -> Properties` (а не `Window -> Preferences`). Там есть `Java Build Path` и `Java Compiler`.
    * `Java Build Path` настроить сложнее, там необходимо открыть вкладку `Libraries`, нажать на `Module path` и нажать справа `Add Library -> JRE System Library`. Там вы можете указать одну из добавленных ранее в `Installed JREs`.
* IntelliJ IDEA:
  * `Ctrl + Alt + Shift + S` приведёт вас к структуре проекта, где вы настраиваете SDK и Language Level.
  * `Ctrl + Alt + S`, а затем `Build... -> Compiler -> Java Compiler` приведёт вас к настройкам Language Level в среде разработки.
  * `Ctrl + Alt + S`, а затем `Build... -> Build Tools -> Gradle -> Gradle JVM` приведёт вас к настройкам переменных сред в среде разработки. Это на случай, если лень каждый раз править переменные среды ОС.
  * Альтернативные способы добраться до структуры проекта или настроек среды - это нажать на шестерёнку сверху справа и выбрать там `Project Structure` или `Settings`.
  * Ещё один альтернативный способ найти настройки среды - нажать `File -> Settings`.

<h2> Гайд по установке и работе </h2>

Собственно, для начала нужно скачать и разархивировать куда-то папку с исходниками. Будем называть это папкой проекта. В ней лежат папки (gradle, src) и различные файлы.

Версия 1.7.10 вышла в 2014 году, и с тех пор все инструменты для разработки уже устарели. Более того, даже на момент создания они были неидеальны и требовали костыли. Костыли реализуются созданием файлов IDE.

* В папке есть два bat-файла, `setupEclipse` и `setupIdea`. Выбираем нужный, запускаем двойным нажатием ЛКМ по нему, ждём окончания.
  * Под капотом bat-файлов лежат команды консоли Windows, `gradlew setupDecompWorkspace eclipse` и `gradlew setupDecompWorkspace idea`, соответственно. Если желаете взять процесс в свои руки и не полагаться на bat-файлы, можете вместо их запуска открыть в папке проекта консоль Windows и ввести там эти команды руками. Смысл bat-файла, скорее, не в экономии времени, а в напоминании о том, что эта версия требует костыль.
* После окончания генерации среды мы импортируем сгенерированный проект IDE. ***Именно проект IDE!*** Это не проект Gradle.
  * В случае Eclipse IDE мы импортируем всю папку как "существующий Eclipse-проект": `File -> Import -> General -> Existing Projects into Workspace -> Next -> [Выбираете папку] -> Finish`.
  * В случае IntelliJ IDEA мы двойным нажатием ЛКМ открываем файл `.ipr`. Загрузку скриптов Gradle нужно пропустить (`skip`). Когда инициализация закончится, крайне рекомендуется в меню `File -> Manage IDE Settings` конвертировать проект `.ipr` в `Directory-based format`, иначе багов и зависаний не миновать.
* Чтобы запустить мод из среды, используйте следующий подход.
  * В случае IntelliJ IDEA у нас уже сгенерированы сверху два варианта запуска, клиент и сервер. Сервер не работает, это норма. Клиент зачастую запускается с недостаточным количеством памяти. Это можно исправить, нажав на название запуска, а затем на `Edit Configurations`. Меняем `1024` на `2048` (как минимум).
  * В случае Eclipse IDE мы должны самостоятельно создать новую конфигурацию запуска. Это делается возле зелёного кружочка запуска на верхней панели - там есть чёрный треугольник, при нажатии на который можно увидеть `Run Configurations`. Нажимаем, затем выбираем слева `Java Application` и жмём сверху слева на белый документ. Появляется ненастроенный запуск. Настраиваем его.
    * В первой вкладке (сверху) указываем проект - это папка, для которой мы создаём запуски.
    * В первой вкладке (ниже) указываем файл входа - это `GradleStart`.
    * Во вкладке `Arguments` (сверху) выделяем память: `-Xincgc -Xmx2G -Xms2G`.
    * Во вкладке `Arguments` (снизу) указываем рабочую папку (где будут миры майнкрафта и прочие файлы игры). Например, создайте в скачанном репозитории папку `run` и укажите её, как рабочую.
    * Сохраняем.
* Чтобы скомпилировать мод в jar-файл, используйте следующий подход.
  * В случае Eclipse IDE откройте консоль Windows в папке среды и введите `gradlew build`. Альтернатива - создайте файл `build.bat`, откройте его блокнотом, впишите туда команду `gradlew build`, сохраните, запустите двойным нажатием ЛКМ.
  * В случае IntelliJ IDEA можно применить способ Eclipse, но есть и более красивый способ. Создайте собственный Gradle Task с командой `build` на вашем проекте и выполните его. Как это сделать:
    * Нажмите на любой из уже сгенерированных запусков.
	* Затем нажмите на `Edit Configurations`.
	* Слева сверху жмёте плюсик и затем дважды нажимаете ЛКМ по `Gradle` в появившемся меню.
	* Под словом `Run` пишете `build`.
	* Сохраняете снизу, нажав на "ОК".
	* Для компиляции выбираете тот запуск, который вы только что создали, и запускаете его зелёной кнопкой запуска (треугольник).
* После того, как вы запустите сборку одним из способов выше, произойдёт компиляция, а ваш мод появится в папке `среда_разработки/build/libs`.

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
