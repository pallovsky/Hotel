import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Offer} from "../_models/offer";
import {MessageResponse} from "../_models/message-response";

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  constructor(
    private httpClient: HttpClient
  ) {
  }

  getOffers(token: string, gameId: string): Observable<Offer[]> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.get<Offer[]>(environment.apiUrl + '/games/' + gameId + '/offers', {
      'headers': headers
    })
  }

  updateOffers(token: string, gameId: string, offerId: string): Observable<MessageResponse> {
    const headers = new HttpHeaders().set('Authorization', token);

    return this.httpClient.put<MessageResponse>(environment.apiUrl + '/games/' + gameId + '/offers/' + offerId, {}, {
      'headers': headers
    })
  }
}
