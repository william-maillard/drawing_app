# build the jar file with maven
mvn clean; mvn package;

# move it to the root dir to be indexing by git
mv ./target/Dessin-1.0.jar ./

# compile it into webassembly with cheerpJ