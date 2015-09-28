package controllers.api.v1

import akka.actor.ActorRef
import com.sample.scalabackend.configuration.actor.ActorFactory
import com.sample.scalabackend.core.Asserts
import com.sample.scalabackend.core.response.ResponseTools
import com.sample.scalabackend.core.utils.HashUtils
import com.sample.scalabackend.module.authentication.service.AuthenticationService
import com.sample.scalabackend.module.log.action.actor.ActionLogCreateMsg
import com.sample.scalabackend.module.log.action.domain.{ActionDomainType, ActionLogEntity, ActionType}
import com.sample.scalabackend.module.user.domain.{UserCreateEntity, UserDetailsEntity}
import com.sample.scalabackend.module.user.service.domain.UserDomainService
import com.sample.scalabackend.module.user.validation.UserCreateValidator
import controllers.core.SecuredController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@stereotype.Controller
class UserController @Autowired
(
  private val userDomainService     : UserDomainService,
  private val userCreateValidator   : UserCreateValidator
)
(
  implicit private val authenticationService: AuthenticationService
) extends SecuredController
{
  Asserts.argumentIsNotNull(userDomainService)
  Asserts.argumentIsNotNull(userCreateValidator)
  Asserts.argumentIsNotNull(authenticationService)

  def read(id: Long) = AuthenticatedAction {
    request =>
      val userCandidate = this.userDomainService.tryGetById(id)
      if(!userCandidate.isDefined) {
        Future(NotFound(ResponseTools.errorToRestResponse("User with this id does not exist.").json))
      }
      else {
        val userForResponse = userCandidate.get.withoutPassword
        Future(Ok(ResponseTools.data(userForResponse).json))
      }
  }

  def create = MutateJsonAction[UserCreateEntity](userCreateValidator) {
    (request, validationResult) =>
      val userCreateEntity = validationResult.validatedItem
      val generatedId = this.userDomainService.create(userCreateEntity.copy(password = HashUtils.sha1(userCreateEntity.password)))
      val createdUser = this.userDomainService.getById(generatedId.id)

      val userCreatedAction = ActionLogEntity.of[UserDetailsEntity, UserDetailsEntity](
          userId      = createdUser.id,
          domainType  = ActionDomainType.USER,
          domainId    = createdUser.id,
          actionType  = ActionType.CREATED,
          before      = None,
          after       = Some(createdUser)
      )
      ActorFactory.actionLogActorRouter.tell(ActionLogCreateMsg(userCreatedAction), ActorRef.noSender)

      val responseItem = createdUser.withoutPassword
      Future.successful(Ok(ResponseTools.of(responseItem, Some(validationResult.messages)).json))
  }
}
