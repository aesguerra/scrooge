package org.minyo

import com.twitter.finagle.Thrift
import com.twitter.server.TwitterServer
import com.twitter.util.Await
import org.minyo.services.FooServiceImpl
import org.slf4j.LoggerFactory


object HelloScrooge extends TwitterServer {
  private val LOG = LoggerFactory.getLogger(this.getClass)

  override val failfastOnFlagsNotParsed = true

  val hostAddress = flag("host", "127.0.0.1:63999", "Host address to be used.")
  val maxConcurrentRequests = flag("max-requests", 32, "Max concurrent requests")
  val maxWaiters = flag("max-waiters", 128, "Max waiter when max-requests is full.")

  def main() {

    LOG.info("Creating the thrift server")
    val service = new FooServiceImpl()

    val server = Thrift.server
      .withLabel("HelloScrooge")
      .withAdmissionControl.concurrencyLimit(
      maxConcurrentRequests = maxConcurrentRequests.getWithDefault.get,
      maxWaiters            = maxWaiters.getWithDefault.get
    )
      .withBufferedTransport()
      .withMaxReusableBufferSize(536870912)
      .serveIface(hostAddress.getWithDefault.get, service)

    LOG.info("Thrift server now running...")
    onExit {
      LOG.info("Closing server.")
      server.close()
      LOG.info("server closed.")
    }

    LOG.info("Waiting for events...")
    Await.ready(server)
  }
}
