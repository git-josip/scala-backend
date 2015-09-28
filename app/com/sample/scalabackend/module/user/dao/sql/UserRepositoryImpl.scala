package com.sample.scalabackend.module.user.dao.sql

import com.sample.scalabackend.module.user.dao.UserRepository
import com.sample.scalabackend.module.user.dao.sql.mapper.UserEntityMapper._
import com.sample.scalabackend.module.user.domain.{UserDetailsEntity, UserCreateEntity}
import com.sample.scalabackend.core.{Asserts, GeneratedId}
import com.sample.scalabackend.core.Asserts
import org.springframework.stereotype.Repository
import play.api.db.DB
import play.api.Play.current
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.jdbc.StaticQuery._

@Repository
class UserRepositoryImpl() extends UserRepository
{
  private lazy val db = Database.forDataSource(DB.getDataSource())

  override def insert(item: UserCreateEntity): GeneratedId =
  {
    Asserts.argumentIsNotNull(item)

    db.withTransaction {
      implicit session =>

        val idCandidate = UserRepositoryQuery.INSERT_QUERY(item).as[Long].firstOption
        Asserts.argumentIsTrue(idCandidate.isDefined)

        GeneratedId(idCandidate.get)
    }
  }

  override def findById(id: Long): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsTrue(id > 0)

    db.withSession {
      implicit session =>

        UserDetailsTableDescriptor.query.filter(_.id === id).list.headOption
    }
  }

  override def findByUsername(username: String): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsNotNull(username)

    db.withSession {
      implicit session =>

        UserDetailsTableDescriptor.query.filter(_.username === username).list.headOption
    }
  }

  override def findByEmail(email: String): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsNotNullNorEmpty(email)

    db.withSession {
      implicit session =>

        UserDetailsTableDescriptor.query.filter(_.email === email).list.headOption
    }
  }

  override def findAll: List[UserDetailsEntity] =
  {
    db.withSession {
      implicit session =>

        UserDetailsTableDescriptor.query.list
    }
  }
}

private object UserRepositoryQuery
{
  def INSERT_QUERY(item: UserCreateEntity) = {
    sql"""
        INSERT INTO users(first_name, last_name, username, email, password)
        VALUES (${item.firstName}, ${item.lastName}, ${item.username}, ${item.email}, ${item.password})
        RETURNING id
    """
  }
}
