package com.sample.scalabackend.module.user.validation

import com.sample.scalabackend.core.{ValidationResult, Validator, Asserts}
import com.sample.scalabackend.core.messages.Messages
import com.sample.scalabackend.module.user.domain.UserCreateEntity
import com.sample.scalabackend.module.user.service.domain.UserDomainService
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import com.sample.scalabackend.core.utils.ValidateUtils

@Component
class UserCreateValidator @Autowired
(
  private val entityDomainService: UserDomainService
) extends Validator[UserCreateEntity] {
  Asserts.argumentIsNotNull(entityDomainService)

  private val MAX_ALLOWED_LENGTH = 80
  private val MAX_ALLOWED_CHARACTER_ERROR = s"must be less than or equal to $MAX_ALLOWED_LENGTH character"

  override def validate(item: UserCreateEntity): ValidationResult[UserCreateEntity] =
  {
    Asserts.argumentIsNotNull(item)

    val validationMessages = Messages.of

    validateFirstName (item, validationMessages)
    validateLastName  (item, validationMessages)
    validateEmail     (item, validationMessages)
    validateUserName  (item, validationMessages)
    validatePassword  (item, validationMessages)

    ValidationResult(
      validatedItem = item,
      messages      = validationMessages
    )
  }

  private def validateFirstName(item: UserCreateEntity, validationMessages: Messages) {
    val localMessages = Messages.of(validationMessages)

    val fieldValue = item.firstName

    ValidateUtils.validateLengthIsLessThanOrEqual(
      fieldValue,
      MAX_ALLOWED_LENGTH,
      localMessages,
      UserCreateEntity.FIRST_NAME_FORM_ID.value,
      MAX_ALLOWED_CHARACTER_ERROR
    )
  }

  private def validateLastName(item: UserCreateEntity, validationMessages: Messages) {
    val localMessages = Messages.of(validationMessages)

    val fieldValue = item.lastName

    ValidateUtils.validateLengthIsLessThanOrEqual(
      fieldValue,
      MAX_ALLOWED_LENGTH,
      localMessages,
      UserCreateEntity.LAST_NAME_FORM_ID.value,
      MAX_ALLOWED_CHARACTER_ERROR
    )
  }

  private def validateEmail(item: UserCreateEntity, validationMessages: Messages) {
    val localMessages = Messages.of(validationMessages)

    val fieldValue = item.email

    ValidateUtils.validateEmail(
      fieldValue,
      UserCreateEntity.EMAIL_FORM_ID,
      localMessages
    )

    ValidateUtils.validateLengthIsLessThanOrEqual(
      fieldValue,
      MAX_ALLOWED_LENGTH,
      localMessages,
      UserCreateEntity.EMAIL_FORM_ID.value,
      MAX_ALLOWED_CHARACTER_ERROR
    )

    if(!localMessages.hasErrors()) {
      val doesExistWithEmail = this.entityDomainService.doesExistByByEmail(fieldValue)
      ValidateUtils.isFalse(
        doesExistWithEmail,
        localMessages,
        UserCreateEntity.EMAIL_FORM_ID.value,
        "User already exists with this email"
      )
    }
  }

  private def validateUserName(item: UserCreateEntity, validationMessages: Messages) {
    val localMessages = Messages.of(validationMessages)

    val fieldValue = item.username

    ValidateUtils.validateLengthIsLessThanOrEqual(
      fieldValue,
      MAX_ALLOWED_LENGTH,
      localMessages,
      UserCreateEntity.USERNAME_FORM_ID.value,
      MAX_ALLOWED_CHARACTER_ERROR
    )

    if(!localMessages.hasErrors()) {
      val doesExistWithUsername = this.entityDomainService.doesExistByUsername(fieldValue)
      ValidateUtils.isFalse(
        doesExistWithUsername,
        localMessages,
        UserCreateEntity.USERNAME_FORM_ID.value,
        "User already exists with this username"
      )
    }
  }

  private def validatePassword(item: UserCreateEntity, validationMessages: Messages) {
    val localMessages = Messages.of(validationMessages)

    val fieldValue = item.password

    ValidateUtils.validateLengthIsLessThanOrEqual(
      fieldValue,
      MAX_ALLOWED_LENGTH,
      localMessages,
      UserCreateEntity.PASSWORD_FORM_ID.value,
      MAX_ALLOWED_CHARACTER_ERROR
    )
  }
}