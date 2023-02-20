import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {LoginRequest} from "../_requests/login-request";
import {TokenResponse} from "../_models/token-response";
import {MessageResponse} from "../_models/message-response";
import {BehaviorSubject, Observable, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedIn = new BehaviorSubject<boolean>(false);
  private admin = new BehaviorSubject<boolean>(false);

  constructor(private httpClient: HttpClient) {
    const token = localStorage.getItem('token');
    const validUntil = localStorage.getItem('validUntil');
    const role = localStorage.getItem('role');

    if (token != null && validUntil != null) {
      if (new Date(validUntil).getTime() > Date.now()) {
        this.loggedIn.next(true)

        if (role == 'ADMIN') {
          this.admin.next(true);
        }
      }
    }
  }

  login(username: string, password: string): Observable<TokenResponse> {
    return this.httpClient.post<TokenResponse>(
      environment.apiUrl + '/login', {
        "username": username,
        "password": password
      }
    ).pipe(
      tap(response => {
        localStorage.setItem('token', response.value)
        localStorage.setItem('validUntil', response.validUntil.toString())
        localStorage.setItem('role', response.role)

        this.loggedIn.next(true)
        if (response.role == 'ADMIN') {
          console.log('xdddddddddd')
          this.admin.next(true)
        }
      })
    )
  }

  register(username: string, password: string): Observable<MessageResponse> {
    return this.httpClient.post<MessageResponse>(
      environment.apiUrl + '/register', {
        "username": username,
        "password": password
      }
    )
  }

  logout() {
    localStorage.clear()
    this.loggedIn.next(false);
    this.admin.next(false);
  }

  get isLoggedIn() {
    return this.loggedIn.asObservable();
  }

  get isAdmin() {
    return this.admin.asObservable();
  }
}
