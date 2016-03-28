package com.bbva.mike.tests.nacar

import java.util.Properties

import akka.actor.Actor
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.{ProducerRecord, KafkaProducer}

/**
 * Created by mikelsanvicente on 17/03/16.
 */
class KafkaSender(topic:String) extends Actor {
  val config = ConfigFactory.load()

  val producer: KafkaProducer[String, String] = new KafkaProducer(getConfiguration)
  def receive = {
    case SendMessage =>
      try {
        producer.send(new ProducerRecord[String, String](topic, MessageGenerator.generateRandomMessage()))
        sender ! SendSuccess
      } catch {
        case _ :Throwable => sender ! SendError
      }
  }

  private def getConfiguration: Properties = {
    val props: Properties = new Properties
    props.put("acks", config.getString("kafka.acks"))
    props.put("batch.size", config.getString("kafka.batch.size"))
    props.put("bootstrap.servers", config.getString("kafka.bootstrap.servers"))
    props.put("buffer.memory", config.getString("kafka.buffer.memory"))
    props.put("connections.max.idle.ms", config.getString("kafka.connections.max.idle.ms"))
    props.put("key.serializer", config.getString("kafka.key.serializer"))
    props.put("linger.ms", config.getString("kafka.linger.ms"))
    props.put("max.in.flight.requests.per.connection",
      config.getString("kafka.max.in.flight.requests.per.connection"))
    props.put("partitioner.class", config.getString("kafka.partitioner.class"))
    props.put("retries", config.getString("kafka.retries"))
    props.put("request.timeout.ms", config.getString("kafka.timeout.ms"))
    props.put("value.serializer", config.getString("kafka.value.serializer"))

    props
  }
}
