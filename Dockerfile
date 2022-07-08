FROM amazoncorretto:11-alpine-jdk
MAINTAINER oguzhanturan
COPY target/favorite*.jar favorite-recipe.jar
ENTRYPOINT ["java","-jar","/favorite-recipe.jar"]
