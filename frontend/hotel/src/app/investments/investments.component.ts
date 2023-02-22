import { Component, OnInit } from '@angular/core';
import {Investment} from "../_models/investment";
import {InvestmentService} from "../_service/investment.service";
import {Route, Router} from "@angular/router";

@Component({
  selector: 'app-investments',
  templateUrl: './investments.component.html',
  styleUrls: ['./investments.component.scss']
})
export class InvestmentsComponent implements OnInit {
  gameId = ''
  investments: Investment[] = [];

  constructor(
    private router: Router,
    private investmentService: InvestmentService
  ) { }

  ngOnInit(): void {
    // @ts-ignore
    this.gameId = /((\w{4,12}-?)){5}/.exec(this.router.url)[0]
    this.investmentService.getInvestments(localStorage.getItem('token')!, this.gameId).subscribe(
      response => this.investments = response,
      _ => this.router.navigate(['login'])
    )
  }

  onInvest(id: string) {
    this.investmentService.updateInvestments(localStorage.getItem('token')!, this.gameId, id).subscribe(
      _ => window.location.reload(),
      _ => this.router.navigate(['login'])
    )
  }
}
