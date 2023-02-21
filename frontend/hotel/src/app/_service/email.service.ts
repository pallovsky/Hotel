import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Email} from "../_models/email";

@Injectable({
  providedIn: 'root'
})
export class EmailService {

  constructor(
    private httpClient: HttpClient
  ) {
  }

  getEmails(token: string, gameId: string): Observable<Email[]> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.get<Email[]>(environment.apiUrl + '/games/' + gameId + '/emails', {
      'headers': headers
    })
  }
}
