import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {User} from "../_models/user";
import {MessageResponse} from "../_models/message-response";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) {
  }

  getUsers(token: string): Observable<User[]> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.get<User[]>(environment.apiUrl + '/users', {
      'headers': headers
    })
  }

  deleteUser(id: string, token: string) {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.delete<MessageResponse>(environment.apiUrl + '/users/' + id, {
      'headers': headers
    })
  }
}
