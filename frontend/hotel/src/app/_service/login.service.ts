import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {LoginRequest} from "../_requests/login-request";
import {TokenResponse} from "../_models/token-response";
import {MessageResponse} from "../_models/message-response";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient) {
  }

  login(username: string, password: string) {
    return this.httpClient.post<TokenResponse>(
      environment.apiUrl + '/login', {
        "username": username,
        "password": password
      }
    )
  }

  register(username: string, password: string) {
    return this.httpClient.post<MessageResponse>(
      environment.apiUrl + '/register', {
        "username": username,
        "password": password
      }
    )
  }

  logout() {
    return this.httpClient.get(environment.apiUrl + '/logout', {withCredentials: true})
  }
}
