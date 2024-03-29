import {Component, OnInit} from '@angular/core';
import {User} from "../_models/user";
import {UserService} from "../_service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  token: string = ''
  users: User[] = [];

  constructor(
    private userService: UserService,
    private router: Router
  ) {
    const localToken = localStorage.getItem('token')

    if (localToken == null) {
      this.router.navigate(['/login'])
    } else {
      this.token = localToken
    }

    this.userService.getUsers(this.token).subscribe(
      response => this.users = response,
      _ => this.router.navigate(['/login'])
    )
  }

  ngOnInit(): void {

  }

  deleteUser(id: string) {
    this.userService.deleteUser(id, this.token).subscribe(
      _ => window.location. reload()
    )
  }
}
