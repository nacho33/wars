package com.wars

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, Materializer}
import com.typesafe.config.{Config, ConfigFactory}
import com.wars.controllers.DecryptController

import scala.concurrent.{ExecutionContextExecutor, Future}

case class EncryptedInfo(query: String)

case class ParsedEncriptedInfo(
  galaxy: String,
  quadrant: String,
  starsystem1: String,
  starsystem2: String,
  planet: String)

case class DecryptedInfo(
  galaxy: String,
  quadrant: String,
  starsystem: String,
  planet: String)

trait Service extends Protocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def config: Config
  val logger: LoggingAdapter
  val decryptController: DecryptController

  val routes = {
    logRequestResult("decrypt-coordinates") {
      pathPrefix("decrypt") {
        (get & path(Segment)) { encrypted =>
          complete {
            decryptController.decryptInfo(
              EncryptedInfo(encrypted)).map[ToResponseMarshallable] {
                case Right(decoded) => decoded
                case Left(errorMessage) => BadRequest -> errorMessage
            }
          }
        }
      }
    }
  }
}

object DecryptCoordinates extends App with Service {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)
  override val decryptController = DecryptController()

  Http().bindAndHandle(
    routes, config.getString("http.interface"), config.getInt("http.port"))
}
