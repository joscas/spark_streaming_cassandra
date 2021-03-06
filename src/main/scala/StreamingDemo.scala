/**
 * Created by josep.casals on 04/09/2014.
 */

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkConf

object StreamingDemo{
  def main(args: Array[String]) {
    // Connect to the Cassandra Cluster
    val client: CassandraConnector = new CassandraConnector()
    val cassandraNode = if(args.length > 0) args(0) else "127.0.0.1"
    client.connect(cassandraNode)
    client.execute("CREATE KEYSPACE IF NOT EXISTS streaming_test WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1 } ;")
    client.execute("CREATE TABLE IF NOT EXISTS streaming_test.words (word text PRIMARY KEY, count int);")

    //  Create a StreamingContext with a SparkConf configuration
    val sparkConf = new SparkConf()
      .setAppName("StreamingDemo")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    // Create a DStream that will connect to serverIP:serverPort
    val lines = ssc.socketTextStream("localhost", 9999)

    client.execute("USE streaming_test;")
    lines.foreachRDD(rdd => {
      rdd.collect().foreach(line => {
        if (line.length > 1) {
          client.execute("INSERT INTO words (word, count)" +
            "VALUES ('" + line + "', 1);")
        }
        println("Line from RDD: " + line)
      })
    })

    ssc.start()
  }
}