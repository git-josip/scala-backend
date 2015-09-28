package com.sample.scalabackend.module.user.service.domain

import com.sample.scalabackend.core.Asserts
import com.sample.scalabackend.module.user.domain.UserCreateEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.beans.factory.annotation.Autowired
import com.sample.scalabackend.module.user.dao.UserRepository
import com.sample.scalabackend.core.{GeneratedId, Asserts}
import com.sample.scalabackend.module.user.domain.{UserDetailsEntity, UserCreateEntity}

@Service
class UserDomainServiceImpl @Autowired
(
  private val entityRepository: UserRepository
) extends UserDomainService
{
  Asserts.argumentIsNotNull(entityRepository)

  override def create(item: UserCreateEntity): GeneratedId =
  {
    Asserts.argumentIsNotNull(item)

    this.entityRepository.insert(item)
  }

  override def tryGetById(id: Long): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsTrue(id > 0)

    this.entityRepository.findById(id)
  }

  override def tryGetByUsername(username: String): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsNotNull(username)

    this.entityRepository.findByUsername(username)
  }

  override def tryGetByEmail(email: String): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsNotNull(email)

    this.entityRepository.findByEmail(email)
  }

  override def getAll: List[UserDetailsEntity] =
  {
    this.entityRepository.findAll
  }
}
