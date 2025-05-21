# Используем официальный образ OpenJDK 17
FROM eclipse-temurin:17-jdk-jammy

# Рабочая директория внутри контейнера
WORKDIR /app

# Копируем Maven-файлы для эффективного кэширования зависимостей
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Копируем исходный код
COPY src src

# Собираем приложение
RUN ./mvnw clean package -DskipTests

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "target/hotel-booking-0.0.1-SNAPSHOT.jar"]