﻿# Текст ДЗ

### Реляционные базы данных. Язык запросов SQL. Операторы SELECT, INSERT, UPDATE, DELETE. Подключение к базе через JDBC. Отправка запросов и обработка результатов.

1. Сформировать таблицу товаров (id, prodid, title, cost) запросом из Java-приложения:
   - id – порядковый номер записи, первичный ключ;
   - prodid – уникальный номер товара;
   - title – название товара;
   - cost – стоимость.
2. При запуске приложения очистить таблицу и заполнить 10 000 товаров вида:
   - id_товара 1 товар1 10
   - id_товара 2 товар2 20
   - id_товара 3 товар3 30
   - …
   - id_товара 10 000 товар10 000 100 000
3. Написать консольное приложение, которое позволяет узнать цену товара по его имени, либо вывести сообщение «Такого товара нет», если товар не обнаружен в базе. Консольная команда: «/цена товар545».
4. Добавить возможность изменения цены товара. Указываем имя товара и новую цену. Консольная команда: «/сменитьцену товар10 10 000».
5. Вывести товары в заданном ценовом диапазоне. Консольная команда: «/товарыпоцене 100 600».