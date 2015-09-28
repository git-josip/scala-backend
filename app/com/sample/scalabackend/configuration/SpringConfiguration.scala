package com.sample.scalabackend.configuration

import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}

@Configuration
@ComponentScan(basePackages = Array("controllers", "com.sample.luxuryakka"))
class SpringConfiguration
