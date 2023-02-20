import { Component, OnInit } from '@angular/core';
import {AuthService} from "../_service/auth.service";
import {Router} from "@angular/router";
import {BehaviorSubject, Observable} from "rxjs";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  isLoggedIn$: Observable<boolean> = new BehaviorSubject<boolean>(false);
  isAdmin$: Observable<boolean> = new BehaviorSubject<boolean>(false);

  constructor(
    private authService: AuthService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.isLoggedIn$ = this.authService.isLoggedIn;
    this.isAdmin$ = this.authService.isAdmin;
  }

  logout() {
    this.authService.logout()
    this.router.navigate(['login'])
  }
}
