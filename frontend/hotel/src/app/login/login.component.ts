import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {LoginService} from "../_service/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  failureMessage: boolean = false

  loginForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    remember: new FormControl(''),
  });

  constructor(
    private loginService: LoginService,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    const username = this.loginForm.value['username']?.toString()!
    const password = this.loginForm.value['password']?.toString()!

    this.loginService.login(username, password).subscribe(
      response => {
        localStorage.setItem('token', response.value)
        localStorage.setItem('validUntil', response.validUntil.toString())
        this.router.navigate(['/lists'])
      },
      _ => this.failureMessage = true
    )
  }
}
