package com.laplacian.scalabackendsampling.configuration

import com.laplacian.scalabackendsampling.core.utils.ConfigurationUtils
import com.laplacian.scalabackendsampling.module.authentication.domain.configuration.AuthenticationConfiguration
import org.springframework.stereotype.Component

@Component
class AutentichationConfigurationSetup extends AuthenticationConfiguration(
  secret            = ConfigurationUtils.getConfigurationByKey[String]("jwt.token.secret"),
  tokenHoursToLive  = ConfigurationUtils.getConfigurationByKey[Int]("jwt.token.hoursToLive")
)
