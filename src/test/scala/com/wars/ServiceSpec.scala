package com.wars

import akka.event.NoLogging
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest._
import spray.json.JsObject
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.wars.controllers.DecryptController

class ServiceSpec extends FlatSpec with Matchers with ScalatestRouteTest with Service {
  override def testConfigSource = "akka.loglevel = WARNING"
  override def config = testConfig
  override val logger = NoLogging
  override val decryptController = DecryptController()

  val code1 = "2952410b-0a94-446b-8bcb-448dc6e30b08"
  val code2 = "6f9c15fa-ef51-4415-afab-36218d76c2d9"
  val code3 = "2ab81c9b-1719-400c-a676-bdba976150eb"
  val code4 = "2ab81c9b-1719-400c-a67"
  val code5 = "2ab81c9b-1719-400c-a676-bdba976150eb-123"

  it should "respond ok" in {
    Get(s"/decrypt/$code1") ~> routes ~> check {
      status shouldBe OK
      responseAs[String].length should be > 0
    }
  }

  it should "parse the galaxy 1" in {
    Get(s"/decrypt/$code1") ~> routes ~> check {
      status shouldBe OK
      //responseAs[JsObject].fields("galaxy").convertTo[String] shouldBe "22"
      responseAs[JsObject].fields("galaxy").convertTo[String] shouldBe "34"
    }
  }

  it should "parse the quadrant 1" in {
    Get(s"/decrypt/$code1") ~> routes ~> check {
      status shouldBe OK
      responseAs[JsObject].fields("quadrant").convertTo[String] shouldBe "10"
    }
  }

  it should "parse the starsystem 1" in {
    Get(s"/decrypt/$code1") ~> routes ~> check {
      status shouldBe OK
      responseAs[JsObject].fields("starsystem").convertTo[String] shouldBe "42"
    }
  }

  it should "parse the planet 1" in {
    Get(s"/decrypt/$code1") ~> routes ~> check {
      status shouldBe OK
      responseAs[JsObject].fields("planet").convertTo[String] shouldBe "edcb86430"
    }
  }

  it should "respond ok for code2" in {
    Get(s"/decrypt/$code2") ~> routes ~> check {
      status shouldBe OK
      responseAs[String].length should be > 0
    }
  }

  it should "parse the galaxy 2" in {
    Get(s"/decrypt/$code2") ~> routes ~> check {
      status shouldBe OK
      responseAs[JsObject].fields("galaxy").convertTo[String] shouldBe "73"
    }
  }


  it should "parse the quadrant 2" in {
    Get(s"/decrypt/$code2") ~> routes ~> check {
      status shouldBe OK
      responseAs[JsObject].fields("quadrant").convertTo[String] shouldBe "15"
    }
  }

  it should "parse the starsystem 2" in {
    Get(s"/decrypt/$code2") ~> routes ~> check {
      status shouldBe OK
      responseAs[JsObject].fields("starsystem").convertTo[String] shouldBe "46"
    }
  }

  it should "parse the planet 2" in {
    Get(s"/decrypt/$code2") ~> routes ~> check {
      status shouldBe OK
      responseAs[JsObject].fields("planet").convertTo[String] shouldBe "dc9876321"
    }
  }

  it should "respond ok for code3" in {
    Get(s"/decrypt/$code3") ~> routes ~> check {
      status shouldBe OK
      responseAs[String].length should be > 0
    }
  }

  it should "parse the galaxy 3" in {
    Get(s"/decrypt/$code3") ~> routes ~> check {
      status shouldBe OK
      responseAs[JsObject].fields("galaxy").convertTo[String] shouldBe "64"
    }
  }


  it should "parse the quadrant 3" in {
    Get(s"/decrypt/$code3") ~> routes ~> check {
      status shouldBe OK
      responseAs[JsObject].fields("quadrant").convertTo[String] shouldBe "9"
    }
  }

  it should "parse the starsystem 3" in {
    Get(s"/decrypt/$code3") ~> routes ~> check {
      status shouldBe OK
      responseAs[JsObject].fields("starsystem").convertTo[String] shouldBe "35"
    }
  }

  it should "parse the planet 3" in {
    Get(s"/decrypt/$code3") ~> routes ~> check {
      status shouldBe OK
      responseAs[JsObject].fields("planet").convertTo[String] shouldBe "edba976510"
    }
  }

  it should "respond bad request for code4" in {
    Get(s"/decrypt/$code4") ~> routes ~> check {
      status shouldBe BadRequest
      responseAs[String] shouldBe "Encrypted info not valid"
    }
  }

  it should "respond bad request for code5" in {
    Get(s"/decrypt/$code5") ~> routes ~> check {
      status shouldBe BadRequest
      responseAs[String] shouldBe "Encrypted info not valid"
    }
  }
}
