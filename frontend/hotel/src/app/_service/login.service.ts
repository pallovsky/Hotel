import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private httpClient: HttpClient
  ) {
  }

  login(username: string, password: string, remember: boolean) {
    return this.httpClient.post(
      'http://localhost:5000/login', {
        "username": username,
        "password": password,
        "remember": remember
      }
    )
  }
}
