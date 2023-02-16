import { Component, OnInit } from '@angular/core';
import {LoginService} from "../_service/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(
    private loginService: LoginService,
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  logout() {
    this.loginService.logout().subscribe(
      _ => this.router.navigate(['login'])
    )
  }
}
