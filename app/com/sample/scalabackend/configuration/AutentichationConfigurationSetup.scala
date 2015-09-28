package com.sample.scalabackend.configuration

import com.sample.scalabackend.core.utils.ConfigurationUtils
import com.sample.scalabackend.module.authentication.domain.configuration.AuthenticationConfiguration
import org.springframework.stereotype.Component

@Component
class AutentichationConfigurationSetup extends AuthenticationConfiguration(
  secret            = ConfigurationUtils.getConfigurationByKey[String]("jwt.token.secret"),
  tokenHoursToLive  = ConfigurationUtils.getConfigurationByKey[Int]("jwt.token.hoursToLive")
)
