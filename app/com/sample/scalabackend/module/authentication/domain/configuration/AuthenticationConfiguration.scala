package com.sample.scalabackend.module.authentication.domain.configuration

import com.sample.scalabackend.core.Asserts

class AuthenticationConfiguration
(
  final val secret            : String,
  final val tokenHoursToLive  : Int
)
{
  Asserts.argumentIsNotNullNorEmpty(secret)
  Asserts.argumentIsNotNull(tokenHoursToLive)
  Asserts.argumentIsTrue(tokenHoursToLive > 0)
}
