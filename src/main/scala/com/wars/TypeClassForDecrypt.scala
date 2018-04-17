package com.wars

import com.wars.utils.HexStringUtils

trait CodeDecrypt[T] {
  def getGalaxy(t: T): String
  def getQuadrant(t1: T): String
  def getStarSystem(t1: T): String
  def getPlanet(t1: T): String
}

object CodeDecrypt {

  implicit object WarsDecrypt extends CodeDecrypt[ParsedEncriptedInfo] {

    /*
     * Sum the HEX value of each char, output in decimal
     */
    def getGalaxy(parsedEncryptedInfo: ParsedEncriptedInfo) = {
      parsedEncryptedInfo
        .galaxy
        .toList
        .foldLeft(0){ (acc, e) =>
          acc + HexStringUtils.string2hex(e.toString)
        }
        .toString
    }

    /*
     * Max HEX value of the stream, output in decimal
     */
    def getQuadrant(parsedEncryptedInfo: ParsedEncriptedInfo) = {
      parsedEncryptedInfo
        .quadrant
        .toList
        .map(c => HexStringUtils.string2hex(c.toString))
        .max
        .toString
    }

    /*
     * Comparation of the third and fourth group of digits, get the MAX value
     * for each position. Sum the HEX value of each char of the previous
     * comparation, output in decimal
     */
    def getStarSystem(parsedEncryptedInfo: ParsedEncriptedInfo) = {

      val list1: Seq[Int] = parsedEncryptedInfo
        .starsystem1
        .toList
        .map(c => HexStringUtils.string2hex(c.toString))

      val list2: Seq[Int] = parsedEncryptedInfo
        .starsystem2
        .toList
        .map(c => HexStringUtils.string2hex(c.toString))

      list1.zip(list2).map{tuple =>
        Math.max(tuple._1, tuple._2)
      }.sum.toString
    }

    /*
     * Order descending and if exists repeated chars, only get 1 of the
     * repeated char
     */
    def getPlanet(parsedEncryptedInfo: ParsedEncriptedInfo) = {
      parsedEncryptedInfo
        .planet
        .toList
        .sorted(Ordering[Char].reverse)
        .distinct
        .mkString
    }
  }
}


