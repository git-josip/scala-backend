package com.sample.scalabackend.module.organizationstructure.validation

import com.sample.scalabackend.core.messages.Messages
import com.sample.scalabackend.core.utils.ValidateUtils
import com.sample.scalabackend.core.{ValidationResult, Asserts, Validator}
import com.sample.scalabackend.module.organizationstructure.domain.{OrganizationStructureType, OrganizationStructureCreateEntity}
import com.sample.scalabackend.module.organizationstructure.service.domain.OrganizationStructureDomainService
import com.sample.scalabackend.core.utils.ValidateUtils
import com.sample.scalabackend.core.{ValidationResult, Asserts, Validator}
import com.sample.scalabackend.module.organizationstructure.domain.OrganizationStructureCreateEntity
import com.sample.scalabackend.module.organizationstructure.service.domain.OrganizationStructureDomainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrganizationStructureCreateValidator @Autowired
(
  private val entityDomainService: OrganizationStructureDomainService
) extends Validator[OrganizationStructureCreateEntity]
{
  Asserts.argumentIsNotNull(entityDomainService)

  override def validate(item: OrganizationStructureCreateEntity): ValidationResult[OrganizationStructureCreateEntity] =
  {
    Asserts.argumentIsNotNull(item)

    val validationMessages = Messages.of

    validateName    (item, validationMessages)
    validateParentId(item, validationMessages)

    ValidationResult(
      validatedItem = item,
      messages      = validationMessages
    )
  }

  private def validateName(item: OrganizationStructureCreateEntity, validationMessages: Messages)
  {
    val localMessages = Messages.of(validationMessages)

    val fieldValue = item.name
    val formId = OrganizationStructureCreateEntity.NAME_FORM_ID.value

    ValidateUtils.validateLengthIsLessThanOrEqual(
      fieldValue,
      80,
      localMessages,
      formId,
      "must be less than or equal to 80 character"
    )

    val doesExistWithEmail = this.entityDomainService.doesExistByName(fieldValue)
    ValidateUtils.isFalse(
      doesExistWithEmail,
      localMessages,
      formId,
      "OrganizationStructure already exists with this name"
    )
  }

  private def validateParentId(item: OrganizationStructureCreateEntity, validationMessages: Messages)
  {
    val localMessages = Messages.of(validationMessages)

    val fieldValue = item.parentId
    val formId = OrganizationStructureCreateEntity.PARENT_ID_FORM_ID.value

    if(item.entityType.isCountry) {
      ValidateUtils.isTrue(
        !fieldValue.isDefined,
        localMessages,
        formId,
        "Parent is not allowed for 'COUNTRY' type."
      )
    } else {
      ValidateUtils.isTrue(
        fieldValue.isDefined,
        localMessages,
        formId,
        "Parent must be defined for non 'COUNTRY' types."
      )
    }

    if(!localMessages.hasErrors && fieldValue.isDefined) {
      val parentCandidate = this.entityDomainService.tryGetById(fieldValue.get)

      ValidateUtils.isTrue(
        parentCandidate.isDefined,
        localMessages,
        formId,
        "Parent do not exists with this id value."
      )

      if(!localMessages.hasErrors()) {
        ValidateUtils.isTrue(
          item.entityType.parentType.get == parentCandidate.get.entityType,
          localMessages,
          formId,
          s"Allowed parent type is: '${item.entityType.parentType.get.name}'. Received parent has type: '${parentCandidate.get.entityType.name}'"
        )
      }
    }
  }
}
