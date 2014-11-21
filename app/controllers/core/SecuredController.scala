package controllers.core

import com.laplacian.scalabackendsampling.core.{ValidationResult, Validator, Asserts}
import com.laplacian.scalabackendsampling.core.response.ResponseTools
import com.laplacian.scalabackendsampling.module.authentication.service.AuthenticationService
import com.laplacian.scalabackendsampling.module.user.domain.UserDetailsEntity
import play.api.libs.json.{Format, Writes, JsValue}
import play.api.mvc._

import scala.concurrent.Future

abstract class SecuredController
(
  implicit private val authenticationService : AuthenticationService
) extends Controller
{
  Asserts.argumentIsNotNull(authenticationService)

  private final val INVALID_TOKEN_ERROR = "Invalid authentication token"
  private final val MISSING_TOKEN_ERROR = "Missing authentication token"
  private final val AUTH_TOKEN_NOT_FOUND_ERROR = "Authorization token not found in secured endpoint"

  def AuthenticatedAction(block: Request[AnyContent] => Future[Result]): Action[AnyContent] =
  {
    AuthenticatedAction(parse.anyContent)(block)
  }

  def AuthenticatedAction[A](bodyParser: BodyParser[A])(block: Request[A] => Future[Result]): Action[A] = {
    Action.async(bodyParser) {
      request =>
        request.headers.get(AUTHORIZATION).map(token => {
          val validationResult = authenticationService.validateToken(token)
          if (!validationResult)
            Future.successful(
              Unauthorized(ResponseTools.errorToRestResponse(INVALID_TOKEN_ERROR).json)
            )
          else block(request)
        }).getOrElse(
            Future.successful(
              Unauthorized(ResponseTools.errorToRestResponse(MISSING_TOKEN_ERROR).json)
          ))
    }
  }

  def MutateJsonAction[T: Format: Manifest](validator: Validator[T])(mutateBlock: (Request[JsValue], ValidationResult[T]) => Future[Result]): Action[JsValue] = {
    Action.async(parse.json) {
      request =>
        request.body.validate[T].map {
          case item if item.getClass == manifest.runtimeClass =>
            val validationResult = validator.validate(item)
            if(validationResult.isValid) {
              mutateBlock(request, validationResult)
            } else {
              Future.successful(BadRequest(validationResult.errorsRestResponse.json))
            }
        }.recoverTotal {
          error =>
            Future.successful(
              BadRequest(ResponseTools.jsErrorToRestResponse[T](error).json)
            )
        }
    }
  }

  def MutateJsonAuthenticatedAction[T: Format: Manifest](validator: Validator[T])(mutateBlock: (Request[JsValue], ValidationResult[T], UserDetailsEntity) => Future[Result]): Action[JsValue] = {
    AuthenticatedAction(parse.json) {
      request =>
        val requestUser = userFromSecuredRequest(request)
        request.body.validate[T].map {
          case item if item.getClass == manifest.runtimeClass =>
            val validationResult = validator.validate(item)
            if(validationResult.isValid) {
              mutateBlock(request, validationResult, requestUser)
            } else {
              Future.successful(BadRequest(validationResult.errorsRestResponse.json))
            }
        }.recoverTotal {
          error =>
            Future.successful(
              BadRequest(ResponseTools.jsErrorToRestResponse[T](error).json)
            )
        }
    }
  }

  implicit def userFromSecuredRequest(implicit request : Request[_]) : UserDetailsEntity =
  {
    Asserts.argumentIsNotNull(request)

    val token = request.headers.get(AUTHORIZATION)
      .getOrElse(throw new IllegalStateException(AUTH_TOKEN_NOT_FOUND_ERROR))

    this.authenticationService.getUserFromToken(token)
  }
}
