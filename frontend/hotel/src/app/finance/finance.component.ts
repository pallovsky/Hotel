import {Component, OnInit} from '@angular/core';
import {CompanyService} from "../_service/company.service";
import {Funds} from "../_models/funds";
import {Router} from "@angular/router";

@Component({
  selector: 'app-finance',
  templateUrl: './finance.component.html',
  styleUrls: ['./finance.component.scss']
})
export class FinanceComponent implements OnInit {

  gameId: string = ''
  funds: Funds = Funds.emptyFunds()

  constructor(
    private router: Router,
    private companyService: CompanyService
  ) { }

  ngOnInit(): void {
    // @ts-ignore
    this.gameId = /((\w{4,12}-?)){5}/.exec(this.router.url)[0]
    this.companyService.getFunds(localStorage.getItem('token')!, this.gameId).subscribe(
      response => this.funds = response
    )
  }

}
