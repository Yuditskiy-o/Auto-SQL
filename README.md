# Тестирование формы входа в интернет-банк 
Тестирование формы входа в интернет-банк через веб-интерфейс с использованием готовой схемы БД MySQL.

## **Перед началом работы**
1. Загрузите на свой ПК данный репозиторий. Для этого в IntelliJ IDEA нажмите "Get from Version Control" и вставьте ссылку https://github.com/Yuditskiy-o/Auto-SQL.git в поле URL, нажмите "Clone".
![image](https://i.gyazo.com/2cd6d21272a1cfa1966e64c2a09732d9.png)
![image](https://i.gyazo.com/fe653f3b399505389c2a093542fa08ff.png)
2. Установите Docker Desktop или Docker Toolbox (в зависимости от вашей ОС). Ознакомьте с [этой](https://github.com/netology-code/aqa-homeworks/blob/aqa4/docker/installation.md) инструкцией.
3. Если установили Docker Toolbox, то запустите ярлык "Docker Quickstart Terminal" на рабочем столе, дождитесь появления вот такого окна:
![image](https://i.gyazo.com/c9c95ee6362f841dd2f22d63844404e8.png)

## **Запуск**
1. **Запускаем docker-контейнер с базой данных MySQL.**

Все параметры для запуска уже есть в соответствующем файле — docker-compose.yml. Вам необходимо лишь ввести в терминале команду:
```
docker-compose up
```
Дождитесь появления сообщения о готовности базы к подключению:
```
[System] [MY-010931] [Server] /usr/sbin/mysqld: ready for connections. Version: '8.0.19'  socket: '/var/run/mysqld
/mysqld.sock'  port: 3306  MySQL Community Server - GPL.
```
2. **Запускаем SUT**

Для этого открываем новую вкладку в Терминале IDEA и вводим следующую команду:
```
java -jar artifacts/app-deadline.jar -P:jdbc.url=jdbc:mysql://192.168.99.100:3306/app -P:jdbc.user=app -P:jdbc.password=pass
```
Дожидаемся сообщения, которое будет означать готовность SUT к работе:
```
2020-10-08 21:30:53.830 [DefaultDispatcher-worker-1] INFO  Application - No ktor.deployment.watch patterns specified, automatic reload is not active
2020-10-08 21:30:54.998 [DefaultDispatcher-worker-1] INFO  Application - Responding at http://0.0.0.0:9999
```
3. **Запускаем авто-тесты**

Для этого открываем еще одну вкладку в Терминале и вводим следующую команду:
```
gradlew clean test
```
Авто-тесты должны проходить корректно и выводиться вот такое сообщение:

![image](https://i.gyazo.com/0858d4c379c00e25a1e7e970a6e856ea.png)

## **Если возникла необходимость перезапустить тесты**
Если нужно перезапустить тесты, либо переподключить контейнер и SUT не выходя при этом из проекта, то во вкладках терминала с запущенным контейнером 
и SUT нажмите **CTRL + C**, соответственно и контейнер, и SUT прекратят свою работу. Затем опять включаем по очереди, вводя команды из п.1 и п.2.
