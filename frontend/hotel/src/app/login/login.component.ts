import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {AuthService} from "../_service/auth.service";
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
    private authService: AuthService,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    const username = this.loginForm.value['username']?.toString()!
    const password = this.loginForm.value['password']?.toString()!

    this.authService.login(username, password).subscribe(
      _ => this.router.navigate(['/games']),
      _ => this.failureMessage = true
    )
  }
}
