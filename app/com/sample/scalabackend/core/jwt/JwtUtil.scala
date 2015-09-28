package com.sample.scalabackend.core.jwt

import com.sample.scalabackend.core.Asserts
import play.api.libs.json.{Reads, Writes, JsValue, Json}
import com.nimbusds.jose.{Payload, JWSAlgorithm, JWSHeader, JWSObject}



object JwtUtil
{
  def signJwtPayload(payload : String)(implicit secret : JwtSecret) : String =
  {
    Asserts.argumentIsNotNullNorEmpty(payload)
    Asserts.argumentIsNotNull(secret)

    val jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(payload))
    jwsObject.sign(secret.signer)
    jwsObject.serialize
  }

  def signJwtPayload(payload : JsValue)(implicit secret : JwtSecret) : String =
  {
    Asserts.argumentIsNotNull(payload)
    Asserts.argumentIsNotNull(secret)

    this.signJwtPayload(payload.toString())
  }

  def signJwtPayload[T](payload : T)(implicit secret : JwtSecret, jsonWrites : Writes[T]) : String =
  {
    Asserts.argumentIsNotNull(payload)
    Asserts.argumentIsNotNull(secret)
    Asserts.argumentIsNotNull(jsonWrites)

    this.signJwtPayload(Json.toJson(payload))
  }

  def tryGetPayloadStringIfValidToken(token : String)(implicit secret : JwtSecret) : Option[String] =
  {
    Asserts.argumentIsNotNull(token)
    Asserts.argumentIsNotNull(secret)

    try
    {
      val jwsObject = JWSObject.parse(token)

      jwsObject.verify(secret.verifier) match
      {
        case true  => Some(jwsObject.getPayload.toString)
        case false => None
      }
    }
    catch
    {
      case  _ : Throwable => None
    }
  }

  def getPayloadIfValidToken[T](token : String)(implicit secret : JwtSecret, jsonWrites : Reads[T]) : Option[T] =
  {
    Asserts.argumentIsNotNull(token)
    Asserts.argumentIsNotNull(secret)
    Asserts.argumentIsNotNull(jsonWrites)

    this.tryGetPayloadStringIfValidToken(token).map(Json.parse(_).as[T])
  }
}
