package com.itv.scalapact.http4s16a.impl

import com.itv.scalapact.shared.typeclasses.{IPactStubber, IResultPublisherBuilder, IScalaPactHttpClientBuilder}
import com.itv.scalapact.shared.SslContextMap
import scalaz.concurrent.Task

import scala.concurrent.duration.Duration

trait HttpInstances {

  // Note that we create a new stubber anytime this implicit is needed (i.e. this is a `def`).
  // We need this because implementations of `IPactStubber` might want to have their own state about the server running.
  implicit def serverInstance: IPactStubber =
    new PactServer

  implicit def httpClientBuilder(implicit sslContextMap: SslContextMap): IScalaPactHttpClientBuilder[Task] =
    (clientTimeout: Duration, sslContextName: Option[String]) => ScalaPactHttpClient(clientTimeout, sslContextName)

  implicit def resultPublisherBuilder(implicit sslContextMap: SslContextMap): IResultPublisherBuilder =
    (clientTimeout: Duration, sslContextName: Option[String]) => ResultPublisher(clientTimeout, sslContextName)
}
