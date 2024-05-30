# Start with a base image containing Java runtime
FROM amazoncorretto:21

# Add Maintainer Info
LABEL maintainer="michalpobuta@student.agh.edu.pl"

# Ustawiamy zmienną środowiskową dla katalogu aplikacji
ENV APP_HOME /app

# Tworzymy katalog aplikacji
RUN mkdir -p $APP_HOME

# Ustawiamy katalog roboczy
WORKDIR $APP_HOME

# Kopiujemy plik JAR do katalogu aplikacji
COPY target/*.jar app.jar

# Ustawiamy port, na którym aplikacja będzie działać
EXPOSE 8080

# Uruchamiamy aplikację
ENTRYPOINT ["java", "-jar", "app.jar"]
