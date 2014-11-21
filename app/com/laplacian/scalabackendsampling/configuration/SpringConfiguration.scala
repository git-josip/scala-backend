package com.laplacian.scalabackendsampling.configuration

import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}

@Configuration
@ComponentScan(basePackages = Array("controllers", "com.laplacian.luxuryakka"))
class SpringConfiguration
