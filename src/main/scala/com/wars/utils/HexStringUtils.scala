package com.wars.utils

object HexStringUtils {

  // convert hex string to hex int value
  def string2hex(str: String): Int = {
    Integer.parseInt(str.toString, 16)
  }

}
