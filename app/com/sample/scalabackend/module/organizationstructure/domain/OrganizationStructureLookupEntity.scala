package com.sample.scalabackend.module.organizationstructure.domain

import com.sample.scalabackend.core.json.customfomatters.CustomFormatter
import CustomFormatter.Enum
import com.sample.scalabackend.core.Asserts
import com.sample.scalabackend.core.json.customfomatters.CustomFormatter
import play.api.libs.json.Json

case class OrganizationStructureLookupEntity
(
  id          : Long,
  name        : String,
  entityType  : OrganizationStructureType
)
{
  Asserts.argumentIsNotNull(id)
  Asserts.argumentIsNotNull(name)
  Asserts.argumentIsNotNull(entityType)
}

object OrganizationStructureLookupEntity
{
  implicit val organizationStructureTypeWrites= CustomFormatter.Enum.enumWritesByName[OrganizationStructureType]
  implicit val organizationStructureTypeReads = CustomFormatter.Enum.enumReadsByName[OrganizationStructureType]

  implicit val jsonFormat = Json.format[OrganizationStructureLookupEntity]
}
