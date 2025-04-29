FROM eclipse-temurin:17-jdk

# 1. Set working directory
WORKDIR /app

# 2. Copy Maven wrapper, POM, and wrapper config first (leverages Docker cache)
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

# 3. Make the mvnw script executable
RUN chmod +x mvnw

# 4. Run the build (skip tests to speed it up)
RUN ./mvnw clean package -DskipTests

# 5. Copy the rest of your source code
COPY src ./src

# 6. Run your Spring Boot app
CMD ["java", "-jar", "target/FoodkingBackend-0.0.1-SNAPSHOT.jar"]











# FROM eclipse-temurin:17-jdk
#
# WORKDIR /app
#
# COPY . .
#
# RUN ./mvnw clean package -DskipTests
#
# CMD ["java", "-jar", "target/FoodkingBackend-0.0.1-SNAPSHOT.jar"]
