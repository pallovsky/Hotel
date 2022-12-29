import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {LoginService} from "../_service/login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    remember: new FormControl(''),
  });

  constructor(
    private loginService: LoginService
  ) { }

  ngOnInit(): void {
  }

  onSubmit() {
    console.warn(this.loginForm.value);
    this.loginService.login(
      this.loginForm.value['username'],
      this.loginForm.value['password'],
      this.loginForm.value['remember'],
    ).subscribe()
  }
}
