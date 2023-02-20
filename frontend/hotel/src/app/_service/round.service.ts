import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Game} from "../_models/game";
import {environment} from "../../environments/environment";
import {MessageResponse} from "../_models/message-response";

@Injectable({
  providedIn: 'root'
})
export class RoundService {

  constructor(
    private httpClient: HttpClient
  ) {
  }

  moveGameToNextRound(token: string, gameId: string): Observable<MessageResponse> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.post<MessageResponse>(environment.apiUrl + '/games/' + gameId + '/nextRound', {}, {
      'headers': headers
    })
  }

  moveUserToNextRound(token: string, gameId: string, userId: string): Observable<MessageResponse> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.post<MessageResponse>(environment.apiUrl + '/users/' + userId + '/games/' + gameId + '/nextRound', {}, {
      'headers': headers
    })
  }
}
