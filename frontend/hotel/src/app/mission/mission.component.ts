import { Component, OnInit } from '@angular/core';
import {Company} from "../_models/company";
import {FormControl, FormGroup} from "@angular/forms";
import {CompanyService} from "../_service/company.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-mission',
  templateUrl: './mission.component.html',
  styleUrls: ['./mission.component.scss']
})
export class MissionComponent implements OnInit {

  gameId: string = ''
  company: Company = Company.emptyCompany()
  missionForm = new FormGroup({
    mission: new FormControl(''),
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
    const mission = this.missionForm.value['mission']?.toString()!

    this.companyService.updateCompany(localStorage.getItem('token')!, this.gameId, this.company.name, mission).subscribe(
      _ => window.location.reload(),
      _ => this.router.navigate(['login'])
    )
  }
}

