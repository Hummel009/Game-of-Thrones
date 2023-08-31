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

> [!NOTE]
> У меня на компьютере в переменных средах указана JDK Eclipse Temurin 1.8.0_382. Если у вас в ходе установки что-то пошло не так, первым делом проверьте версию Java.

Этот репозиторий - проект Gradle, который должен быть импортирован в Eclipse IDE или открыт через IntelliJ IDEA. Однако, не всё так просто - версия 1.7.10 вышла в 2014 году, и с тех пор все инструменты для разработки уже устарели. Более того, даже на момент создания они были неидеальны и требовали костыли. Костыли реализуются созданием файлов IDE.

* В папке есть два батника, `setupEclipse` и `setupIdea`. Выбираем нужный, запускаем, ждём окончания.
  * Под капотом батников лежат команды консоли Windows `gradlew setupDecompWorkspace eclipse` и `gradlew setupDecompWorkspace idea` соответственно. Если желаете взять процесс в свои руки и не полагаться на батники, можете вместо их запуска прописывать эти команды в консоли Windows и эффект будет тот же. Смысл батника, скорее, не в экономии времени, а в напоминании о том, что эта версия требует костыль.
* После окончания генерации среды мы импортируем сгенерированный проект IDE. ***Именно проект IDE!*** Это не проект Gradle.
  * В случае Eclipse IDE мы импортируем всю папку как "существующий Eclipse-проект".
  * В случае IntelliJ IDEA мы открываем файл `.ipr`. Загрузку скриптов Gradle нужно пропустить. Когда инициализация закончится, крайне рекомендуется в меню `File -> Manage IDE Settings` конвертировать проект `.ipr` в Directory-based format, иначе багов и зависаний не миновать.
    * Существуют фокусы с открытием файла проекта Eclipse (`.project`) через IntelliJ IDEA, тогда ещё нужно добавить модуль видимости. Работать будет, но зачем всё это?
* Чтобы запустить мод, используйте следующий подход.
  * В случае IntelliJ IDEA у нас уже сгенерированы сверху два варианта запуска, клиент и сервер. Сервер не работает, это норма.
  * В случае Eclipse IDE мы должны создать новую конфигурацию запуска. Файл входа - это `GradleStart`. Не забудьте об аргументах выделения памяти, вроде `-Xmx2G -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M`.
* Чтобы скомпилировать мод, используйте следующий подход.
  * В случае Eclipse IDE откройте консоль Windows в папке среды и введите `gradlew build`.
  * В случае IntelliJ IDEA создайте собственный Gradle Task с командой `build` на вашем проекте и выполните его, либо откройте консоль Windows в папке среды и введите `gradlew build`.
* Готовый мод лежит в папке `среда_разработки/build/libs`.

<h2> Сведения об используемых JDK/SDK и Compiler Compliance Level/Language Level </h2>

У Minecraft, Forge, ForgeGradle и Gradle есть свои требования к установленной версии Java и допуску её синтаксиса (с годами в язык добавляют новые конструкции, для которых ещё нет поддержки у вышеуказанных технологий).

> [!IMPORTANT]
> Версия 1.7.10 использует Java 8 + допуск синтаксиса 8.

Обязательно проконтролируйте, чтобы в вашей IDE после установки были именно такие значения, а если они другие - измените их.

Java в Eclipse называется JRE/JDK, в IntelliJ IDEA - SDK. Допуск синтаксиса в Eclipse называется Compiler Compliance Level, в IntelliJ IDEA - Language Level.


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
