<h1>Hibernate-JPA-JavaSE</h1>
<p>Использования Hibernate с JPA в Java SE.</p>
<h2>Что делает данная программа?</h2>
<p>Данная программа умеет сохранять/обновлять/удалять пользователя из базы данных (Postgres)</p>
<h2>Как настроить проект?</h2>
<ul>
  <li>1. Меняем <a href="https://github.com/ByteC0d3/Hibernate-JPA-JavaSE/blob/main/src/main/resources/META-INF/persistence.xml">этот</a> файл (настраиваем имя/пароль/ссылку для подключения к БД)</li>
  <li>2. Если мы работаем с локальной базой данных - то можно также настроить <a href="https://github.com/ByteC0d3/Hibernate-JPA-JavaSE/blob/main/src/main/java/ru/kazbo/ormexamples/PostgresDB.java">этот класс</a> для быстрого старта базы данных (однако, это также можно делать вручную)</li>
</ul>
