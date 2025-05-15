# design-bitly
## Commands
```bash
gradle dependencies
gradle build
gradle bootRun

# watch
gradle wrapper

./gradlew bootRun --continuous
# in the other terminal
./gradlew build --continuous

# create a jar file
gradle bootJar
# list the contents of the jar file
jar tvf build/libs/bitly-0.0.1-SNAPSHOT.jar
# run the jar file
java -jar build/libs/bitly-0.0.1-SNAPSHOT.jar
```

# Deep Dives
## How can we ensure short urls are unique?
### Solution
- unique counter with Base62 encoding
- uses Redis to store the counter
    - if we used database INCREMENT, it would be 2 trips to db (1 to get counter, 1 to add row), rather than 1 redis trip, 1 db trip
    - if redis is down, we can failover to another redis instance (sentinel) or restore counter value from db