package com.sample.scalabackend.module.user.dao

import com.sample.scalabackend.core.GeneratedId
import com.sample.scalabackend.module.user.domain.{UserCreateEntity, UserDetailsEntity}
import com.sample.scalabackend.core.GeneratedId
import com.sample.scalabackend.module.user.domain.{UserDetailsEntity, UserCreateEntity}

trait UserRepository
{
  def insert(item: UserCreateEntity): GeneratedId

  def findById(id: Long): Option[UserDetailsEntity]
  def findByUsername(username: String): Option[UserDetailsEntity]
  def findByEmail(email: String): Option[UserDetailsEntity]
  def findAll: List[UserDetailsEntity]
}
