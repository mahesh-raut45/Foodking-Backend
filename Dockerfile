# Use the Eclipse Temurin JDK 17 image
FROM eclipse-temurin:17-jdk

# 1. Set the working directory
WORKDIR /app

# 2. Copy only the Maven wrapper and config first (caching benefit)
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

# 3. Make mvnw executable
RUN chmod +x mvnw

# 4. Copy your source code into the image
COPY src ./src

# 5. Build the project (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# 6. Run the Spring Boot app
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
