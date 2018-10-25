package org.minyo.services

import java.io._

import org.apache.log4j.Logger

class FooServiceImpl extends FooService.MethodPerEndpoint {

  private val LOG = Logger.getLogger(this.getClass.getName)

  override def deliver(deliveryInformation: DeliveryInformation): Boolean = {
    try {
      println("filePath: " + deliveryInformation.filePath)
      println("deliveryInformation: " + deserialise(deliveryInformation.data.array()))

      true
    } catch {
      case e: Exception => {
        LOG.error("Encountered error while delivering data.")
        e.printStackTrace()

        false
      }
    }
  }

  def deserialise[T](bytes: Array[Byte]): T = {
    val ois = new ObjectInputStream(new ByteArrayInputStream(bytes))
    val value = ois.readObject
    ois.close()
    value.asInstanceOf[T]
  }
}
