# Social Network HW
Проект домашка по курсу Highload Architect

# Запуск
1. Выкачать и собрать проект:
    ```bash
    gradlew build
    ```
2. Запуск в докере:
    ```bash
    docker compose up
    ```
   
   **Настройки подключения к бд:**

   url: jdbc:postgresql://localhost:5432/sn

   user: postgres

   pass: postgres


3. В корне проекта лежит файл с тестовыми запросами: [файл](requests.http).
   Коллекция для постмана [тык](HW.postman_collection.json)
   (при запросе пользовательской информации следует обновить токен и айди в запросе)




./jmeter -n -t templates/simple-http-request-test-plan.jmx -l results_index_1000.jtl

