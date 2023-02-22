import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {OfferService} from "../_service/offer.service";
import {Offer} from "../_models/offer";

@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.scss']
})
export class OfferComponent implements OnInit {

  gameId = ''
  offers: Offer[] = []

  hotelOffers: Offer[] = []
  partyOffers: Offer[] = []
  foodOffers: Offer[] = []

  constructor(
    private router: Router,
    private offerService: OfferService
  ) { }

  ngOnInit(): void {
    // @ts-ignore
    this.gameId = /((\w{4,12}-?)){5}/.exec(this.router.url)[0]
    this.offerService.getOffers(localStorage.getItem('token')!, this.gameId).subscribe(
      response => {
        this.offers = response
        this.hotelOffers = response.filter(offer => offer.type == 'HOTEL')
        this.partyOffers = response.filter(offer => offer.type == 'PARTY')
        this.foodOffers = response.filter(offer => offer.type == 'FOOD')
        console.log(this.foodOffers)
      },
      _ => this.router.navigate(['login'])
    )
  }

  onChange(offerId: string) {
    this.offerService.updateOffers(localStorage.getItem('token')!, this.gameId, offerId).subscribe(
      _ => window.location.reload(),
      _ => this.router.navigate(['login'])
    )
  }
}
