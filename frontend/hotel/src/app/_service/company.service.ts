import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Game} from "../_models/game";
import {environment} from "../../environments/environment";
import {Company} from "../_models/company";
import {MessageResponse} from "../_models/message-response";
import {Funds} from "../_models/funds";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(
    private httpClient: HttpClient
  ) {
  }

  getCompany(token: string, gameId: string): Observable<Company> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.get<Company>(environment.apiUrl + '/games/' + gameId + '/company', {
      'headers': headers
    })
  }

  getFunds(token: string, gameId: string): Observable<Funds> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.get<Funds>(environment.apiUrl + '/games/' + gameId + '/company/funds', {
      'headers': headers
    })
  }

  updateCompany(token: string, gameId: string, name: string,  mission: string): Observable<MessageResponse> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.put<MessageResponse>(environment.apiUrl + '/games/' + gameId + '/company', {
      "mission": mission,
      "name": name
    }, {
      'headers': headers
    })
  }
}
