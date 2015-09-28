package com.sample.scalabackend.module.organizationstructure.domain

import com.sample.scalabackend.core.Asserts
import com.sample.scalabackend.core.json.customfomatters.CustomFormatter
import CustomFormatter.Enum
import com.sample.scalabackend.core.messages.MessageKey
import play.api.libs.json.Json

case class OrganizationStructureCreateEntity
(
  name              : String,
  entityType        : OrganizationStructureType,
  parentId          : Option[Long],
  description       : String,
  shortDescription  : String
)
{
  Asserts.argumentIsNotNull(name)
  Asserts.argumentIsNotNull(entityType)
  Asserts.argumentIsNotNull(parentId)
  Asserts.argumentIsNotNull(description)
  Asserts.argumentIsNotNull(shortDescription)
}

object OrganizationStructureCreateEntity
{
  implicit val organizationStructureTypeWrites= Enum.enumWritesByName[OrganizationStructureType]
  implicit val organizationStructureTypeReads = Enum.enumReadsByName[OrganizationStructureType]

  implicit val jsonFormat = Json.format[OrganizationStructureCreateEntity]

  val NAME_FORM_ID              = MessageKey("name")
  val ENTITY_TYPE_FORM_ID       = MessageKey("entityType")
  val PARENT_ID_FORM_ID         = MessageKey("parentId")
  val DESCRIPTION_FORM_ID       = MessageKey("description")
  val SHORT_DESCRIPTION_FORM_ID = MessageKey("shortDescription")
}
