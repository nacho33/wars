package com.wars.controllers

import com.wars._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait DecryptController {

  // Polymorphic functions
  def getPlanet[A](encryptedInfo: A)( decipherer: CodeDecrypt[A]): String =
    decipherer.getPlanet(encryptedInfo)

  def getQuadrant[A](encryptedInfo: A)( decipherer: CodeDecrypt[A]): String =
    decipherer.getQuadrant(encryptedInfo)

  def getStarSystem[A](encryptedInfo: A)( decipherer: CodeDecrypt[A]): String =
    decipherer.getStarSystem(encryptedInfo)

  def getGalaxy[A](encryptedInfo: A)( decipherer: CodeDecrypt[A]): String =
    decipherer.getGalaxy(encryptedInfo)

  // Parse the string with the encrypted info
  def parseEncryptedInfo(
    info: EncryptedInfo): Either[String, ParsedEncriptedInfo] = {

    val splitted: Array[String] = info.query.split("-")
    if(splitted.length == 5)
      Right(
        ParsedEncriptedInfo(
          splitted(0),
          splitted(1),
          splitted(2),
          splitted(3),
          splitted(4)))
    else Left("Encrypted info not valid")
  }

  // Function to decrypt the info
  def decryptInfo(
    info: EncryptedInfo): Future[Either[String, DecryptedInfo]] = Future {

    parseEncryptedInfo(info).map{ parsedInfo =>
      DecryptedInfo(
        getGalaxy(parsedInfo)(CodeDecrypt.WarsDecrypt),
        getQuadrant(parsedInfo)(CodeDecrypt.WarsDecrypt),
        getStarSystem(parsedInfo)(CodeDecrypt.WarsDecrypt),
        getPlanet(parsedInfo)(CodeDecrypt.WarsDecrypt))
    }
  }
}
object DecryptController {
  def apply(): DecryptController = new DecryptController{}
}
