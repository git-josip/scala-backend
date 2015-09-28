package com.sample.scalabackend.core.jwt

import com.nimbusds.jose.crypto.{MACVerifier, MACSigner}
import com.sample.scalabackend.core.Asserts

case class JwtSecret(secret : String)
{
  Asserts.argumentIsNotNullNorEmpty(secret)

  val signer   = new MACSigner(secret)
  val verifier = new MACVerifier(secret)
}
