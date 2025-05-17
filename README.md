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

# testing
./gradlew test
./gradlew test --info
./gradlew test --debug
```

# Deep Dives
## How can we ensure short urls are unique?
### Solution
- unique counter with Base62 encoding
- uses Redis to store the counter
    - if we used database INCREMENT, it would be 2 trips to db (1 to get counter, 1 to add row), rather than 1 redis trip, 1 db trip
    - if redis is down, we can failover to another [redis instance (sentinel)](https://github.com/bookpanda/deepdive-redis) or restore counter value from db

## How can we ensure that redirects are fast?
### Solution
- use a Redis cache to store the mapping of short urls to long urls
- use CDNs, but might be a bit overkill for this use case

## How can we scale to support 1B shortened urls and 100M DAU?
### Back-of-the-envelope calculations
a row in urls table:
- short_code VARCHAR(50) = 50 bytes
- original_url TEXT = ~100 bytes
- created_at TIMESTAMP = 8 bytes
- expires_at TIMESTAMP = 8 bytes

we can round this to 500 bytes/row (for additional metadata, row overhead, etc)
- 1B rows = 500B * 1B = 500GB = 1 DB can easily handle this
- if hardware limit is hit, can always shard the database later
- assume 100k new urls per day (~1 write/s) = manageable
- read:write ratio is 100:1 (100 reads/s), a Redis cache is enough (100k operations/s)
- for HA, can do DB replication or backup

### Solution
- we can split into read and write services to scale independently
- 1 database, 1 Redis is enough
- DB replication/backup for HA