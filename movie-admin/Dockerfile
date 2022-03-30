FROM openjdk:11

ARG MOVIE_ADMIN=target/movie-admin-0.0.1-SNAPSHOT.jar

COPY ${MOVIE_ADMIN} movie-admin.jar

ENTRYPOINT ["java","-jar","movie-admin.jar"]
