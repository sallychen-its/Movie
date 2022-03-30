cd movie-admin
mvn clean && mvn install -DskipTests && mvn package -DskipTests
cd target
cp movie-admin-0.0.1-SNAPSHOT.jar ../../public
cd ../../movie-be
mvn install -DskipTests && mvn package -DskipTests
cd target
cp movie-be-0.0.1-SNAPSHOT.jar ../../public
cd ../../