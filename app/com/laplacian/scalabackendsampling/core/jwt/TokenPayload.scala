package com.laplacian.scalabackendsampling.core.jwt

import com.laplacian.scalabackendsampling.core.Asserts
import org.joda.time.DateTime
import play.api.libs.json.{Json, Format}

case class TokenPayload
(
  userId      : Long,
  username    : String,
  expiration  : DateTime
)
{
  Asserts.argumentIsNotNull(userId)
  Asserts.argumentIsNotNull(username)
  Asserts.argumentIsNotNull(expiration)
}

object TokenPayload
{
  implicit val jsonFormat : Format[TokenPayload] = Json.format[TokenPayload]
}