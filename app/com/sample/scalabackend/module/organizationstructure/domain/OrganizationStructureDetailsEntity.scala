package com.sample.scalabackend.module.organizationstructure.domain

import com.sample.scalabackend.core.Asserts
import com.sample.scalabackend.core.json.customfomatters.CustomFormatter
import CustomFormatter.Enum
import play.api.libs.json._

case class OrganizationStructureDetailsEntity
(
  id                : Long,
  name              : String,
  entityType        : OrganizationStructureType,
  parentId          : Option[Long],
  treePath          : String,
  description       : String,
  shortDescription  : String
)
{
  Asserts.argumentIsNotNull(name)
  Asserts.argumentIsNotNull(entityType)
  Asserts.argumentIsNotNull(parentId)
  Asserts.argumentIsNotNull(treePath)
  Asserts.argumentIsNotNull(description)
  Asserts.argumentIsNotNull(shortDescription)
}

object OrganizationStructureDetailsEntity
{
  implicit val organizationStructureTypeWrites= Enum.enumWritesByName[OrganizationStructureType]
  implicit val organizationStructureTypeReads = Enum.enumReadsByName[OrganizationStructureType]

  implicit val jsonFormat = Json.format[OrganizationStructureDetailsEntity]
}
