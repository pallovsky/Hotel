import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
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
          this.admin.next(true)
        }
      })
    )
  }

  register(token: string, username: string, password: string, role: string): Observable<MessageResponse> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.post<MessageResponse>(
      environment.apiUrl + '/users', {
        "username": username,
        "password": password,
        "role": role
      }, {
        'headers': headers
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
