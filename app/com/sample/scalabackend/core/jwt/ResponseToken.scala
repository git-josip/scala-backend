package com.sample.scalabackend.core.jwt

import com.sample.scalabackend.core.Asserts
import play.api.libs.json.{Format, Json}

case class ResponseToken(token: String)
{
  Asserts.argumentIsNotNullNorEmpty(token)
}

object ResponseToken
{
  implicit val jsonFormat : Format[ResponseToken] = Json.format[ResponseToken]
}
