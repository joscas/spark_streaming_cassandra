spark_streaming_cassandra
=========================

Example of Spark Streaming saving to Cassandra

To use with local Cassandra create keyspace and table first:

```
CREATE KEYSPACE streaming_test WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1 } ;
CREATE TABLE streaming_test.words (word text PRIMARY KEY, count int);"
```

Then start netcat on another shell tab and see the words you type appear in the ```words``` C* table:

```
$ nc -lk 9999
```
