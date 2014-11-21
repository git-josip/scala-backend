package controllers

import com.laplacian.scalabackendsampling.core.response.{ResponseTools, RestResponse}
import org.springframework.stereotype
import org.springframework.beans.factory.annotation.Autowired
import com.laplacian.scalabackendsampling.module.user.service.domain.UserDomainService
import com.laplacian.scalabackendsampling.module.authentication.service.AuthenticationService
import com.laplacian.scalabackendsampling.core.Asserts
import play.api.libs.json.Json
import play.api.mvc.{Controller, Action}
import com.laplacian.scalabackendsampling.core.authentication.Credentials
import com.laplacian.scalabackendsampling.core.jwt.ResponseToken

@stereotype.Controller
class AuthenticationController @Autowired
(
  private val userDomainService     : UserDomainService,
  private val authenticationService : AuthenticationService
) extends Controller
{
  Asserts.argumentIsNotNull(userDomainService)
  Asserts.argumentIsNotNull(authenticationService)

  private final val BAD_USERNAME_OR_PASSWORD_ERROR = "Bad username or password"

  def authenticate = Action(parse.json)
  {
    implicit request =>
      request.body.validate[Credentials].map {
        case credentials: Credentials =>
          authenticationService.authenticate(credentials.username, credentials.password).map{ token =>
            Ok(Json.toJson(ResponseTools.data(token)))
          }.getOrElse(
              Unauthorized(ResponseTools.errorToRestResponse(BAD_USERNAME_OR_PASSWORD_ERROR).json))
      }.recoverTotal {
        error =>
          BadRequest(ResponseTools.errorToRestResponse(error.errors.map(_._2).flatten.map(_.message).head).json)
      }
  }

  def refreshToken = Action(parse.json)
  {
    implicit request =>
      request.body.validate[ResponseToken].map {
        case authenticationToken: ResponseToken =>
          authenticationService.refreshToken(authenticationToken.token).map{ token =>
            Ok(Json.toJson(ResponseTools.data(token)))
          }.getOrElse(
              Unauthorized(ResponseTools.errorToRestResponse(BAD_USERNAME_OR_PASSWORD_ERROR).json))
      }.recoverTotal {
        error => BadRequest(ResponseTools.errorToRestResponse(error.errors.map(_._2).flatten.map(_.message).head).json)
      }
  }
}
