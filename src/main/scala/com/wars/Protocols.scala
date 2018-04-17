package com.wars

import spray.json.DefaultJsonProtocol

trait Protocols extends DefaultJsonProtocol {
  implicit val decryptedInfoFormat = jsonFormat4(DecryptedInfo.apply)
  implicit val encryptedInfoFormat = jsonFormat1(EncryptedInfo.apply)
  implicit val parsedEncriptedInfoFormat = jsonFormat5(ParsedEncriptedInfo.apply)
}
