## Проект TODO LIST

***
#### Работа приложения заключается в следующем:

1. Возможность создать задачу.
1. Возможность редактировать/удалить задачу.
1. По умолчанию выводится список всех задач, которые необходимо выполнить, если установить галочку в checkbox "Показать все задания",
то список обновится и будут видны задания, которые помечены как выполненные в том числе.

Ниже приведены скриншоты работы приложения

Список заданий которые не выполнены(по умолчанию):

---
![alt text](https://raw.githubusercontent.com/Web-Programmer-B-P/todo-list/master/src/main/webapp/img_readme/first.png)

Список всех заданий с включеной галочкой:

---
![alt text](https://raw.githubusercontent.com/Web-Programmer-B-P/todo-list/master/src/main/webapp/img_readme/second.png)

Модальное окно обновления задания:

---
![alt text](https://raw.githubusercontent.com/Web-Programmer-B-P/todo-list/master/src/main/webapp/img_readme/third.png)

Модальное окно создания нового задания:

---
![alt text](https://raw.githubusercontent.com/Web-Programmer-B-P/todo-list/master/src/main/webapp/img_readme/fourth.png)

***

#### Техногии использованные при разработке

**фронтенд:**

    1. jquery-min 3.4.1 (обработка dom)
    2. bootstrap3 (модальные окна)
    3. ajax (подгрузка данных и обновление) 
    
**бэкенд:**

    1. java 12
    2. servlet 4.0.1.
    3. База данных: postgres 9.6
    4. Hibernate ORM 5.4.2

**Сервер/контейнер**

    1. Apache Tomcat 9.0.30
    
**Логирование**

    1.log4j 2.12.1