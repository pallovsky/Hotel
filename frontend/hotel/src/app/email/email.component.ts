import {Component, OnInit} from '@angular/core';
import {Email} from "../_models/email";
import {EmailService} from "../_service/email.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.scss']
})
export class EmailComponent implements OnInit {
  gameId: string = ''
  emails: Email[] = [];

  constructor(
    private router: Router,
    private emailService: EmailService
  ) { }

  ngOnInit(): void {
    // @ts-ignore
    this.gameId = /((\w{4,12}-?)){5}/.exec(this.router.url)[0]
    this.emailService.getEmails(localStorage.getItem('token')!, this.gameId).subscribe(
      response => this.emails = response,
      _ => this.router.navigate(['login'])
    )
  }

}
