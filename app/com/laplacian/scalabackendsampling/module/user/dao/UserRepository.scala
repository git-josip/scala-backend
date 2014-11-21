package com.laplacian.scalabackendsampling.module.user.dao

import com.laplacian.scalabackendsampling.core.GeneratedId
import com.laplacian.scalabackendsampling.module.user.domain.{UserCreateEntity, UserDetailsEntity}

trait UserRepository
{
  def insert(item: UserCreateEntity): GeneratedId

  def findById(id: Long): Option[UserDetailsEntity]
  def findByUsername(username: String): Option[UserDetailsEntity]
  def findByEmail(email: String): Option[UserDetailsEntity]
  def findAll: List[UserDetailsEntity]
}
