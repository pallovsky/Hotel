import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Offer} from "../_models/offer";
import {environment} from "../../environments/environment";
import {MessageResponse} from "../_models/message-response";
import {Investment} from "../_models/investment";

@Injectable({
  providedIn: 'root'
})
export class InvestmentService {

  constructor(
    private httpClient: HttpClient
  ) {
  }

  getInvestments(token: string, gameId: string): Observable<Investment[]> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.get<Investment[]>(environment.apiUrl + '/games/' + gameId + '/investments', {
      'headers': headers
    })
  }

  updateInvestments(token: string, gameId: string, invetmentId: string): Observable<MessageResponse> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.put<MessageResponse>(environment.apiUrl + '/games/' + gameId + '/investments/' + invetmentId, {}, {
      'headers': headers
    })
  }
}
