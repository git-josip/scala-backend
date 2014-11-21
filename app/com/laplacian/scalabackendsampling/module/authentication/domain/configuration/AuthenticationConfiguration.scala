package com.laplacian.scalabackendsampling.module.authentication.domain.configuration

import com.laplacian.scalabackendsampling.core.Asserts

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
