import { Component, OnInit } from '@angular/core';
import {CompanyService} from "../_service/company.service";
import {Company} from "../_models/company";
import {Router} from "@angular/router";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-company-name',
  templateUrl: './company-name.component.html',
  styleUrls: ['./company-name.component.scss']
})
export class CompanyNameComponent implements OnInit {

  gameId: string = ''
  company: Company = Company.emptyCompany()
  companyNameForm = new FormGroup({
    name: new FormControl(''),
  });


  constructor(
    private companyService: CompanyService,
    private router: Router
  ) { }

  ngOnInit(
  ): void {
    // @ts-ignore
    this.gameId = /((\w{4,12}-?)){5}/.exec(this.router.url)[0]
    this.companyService.getCompany(localStorage.getItem('token')!, this.gameId).subscribe(
      response => this.company = response,
      _ => this.router.navigate(['login'])
    )
  }

  onSave() {
    const name = this.companyNameForm.value['name']?.toString()!

    this.companyService.updateCompany(localStorage.getItem('token')!, this.gameId, name, this.company.mission).subscribe(
      _ => window.location.reload(),
      _ => this.router.navigate(['login'])
    )
  }
}
