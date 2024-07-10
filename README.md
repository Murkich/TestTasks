# Тестовое задание 2
## Описание проекта
Данное приложение формирует чек и выводит его на экран консоли, а также записывает результат чека в файл.
## Инструкция по запуску
Приложение запускается с помощью консольной команды:
```
java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity discountCard=xxxx balanceDebitCard=xxxx pathToFile=xxxx saveToFile=xxxx
```
где
* id - идентификатор товара
* quantity - кол-во товара
* discountCard=xxxx - название и номер дисконтной карты
* balanceDebitCard=xxxx - баланс на дебетовой карте
* pathToFile - относительный (от корневой директории проекта) путь + название файла с расширением для чтения продуктов (всегда присутствует в заданном формате)
* saveToFile - относительный (от корневой директории проекта) путь + название файла с расширением для записи результатов
### Примечание:
- всё указывается через пробел
- id-quantity - добавляем префикс id-(количество товара). В наборе параметров должна быть минимум одна такая связка "id-quantity"
- discountCard=xxxx - добавляем префикс discountCard=(любые четыре цифры)
- balanceDebitCard=xxxx - указываем префикс balanceDebitCard=(любая сумма на счете). Обязательно должна присутствовать. Баланс может быть как и с двумя знаками после запятой, так и отрицательный
- id могут повторяться: 1-3 2-5 1-1 то же, что и 1-4 2-5
- xxxx - для числа с плавающей точкой разделитель точка
- pathToFile=xxxx и saveToFile=xxxx должны иметь формат .csv
#### Пример команды:
```
java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java 13-5 9-1 6-30 13-4 discountCard=1111 discountCard=1111 balanceDebitCard=100 saveToFile=./new_result.csv pathToFile=./src/main/resources/products.csv
```
