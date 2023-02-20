import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {MessageResponse} from "../_models/message-response";
import {Game} from "../_models/game";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(
    private httpClient: HttpClient
  ) { }

  getGames(token: string): Observable<Game[]> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.get<Game[]>(environment.apiUrl + '/games', {
      'headers': headers
    })
  }

  deleteGame(id: string, token: string): Observable<MessageResponse> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.delete<MessageResponse>(environment.apiUrl + '/games/' + id, {
      'headers': headers
    })
  }
}
