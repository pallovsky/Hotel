import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {AuthService} from "../../_service/auth.service";
import {Router} from "@angular/router";
import {UserService} from "../../_service/user.service";
import {GameService} from "../../_service/game.service";
import {User} from "../../_models/user";

@Component({
  selector: 'app-add-game',
  templateUrl: './add-game.component.html',
  styleUrls: ['./add-game.component.scss']
})
export class AddGameComponent implements OnInit {
  token: string = ''

  addGameForm = new FormGroup({
    name: new FormControl(''),
    type: new FormControl(''),
    users: new FormControl(''),
  });
  users: User[] = [];

  constructor(
    private authService: AuthService,
    private gameService: GameService,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const localToken = localStorage.getItem('token')

    if (localToken == null) {
      this.router.navigate(['/login'])
    } else {
      this.token = localToken
    }

    this.userService.getUsers(this.token).subscribe(
      response => this.users = response
    )
  }

  onSubmit() {
    const name = this.addGameForm.value['name']?.toString()
    const type = this.addGameForm.value['type']?.toString()
    const users = this.addGameForm.value['users']

    this.gameService.createGame(this.token, name, type, users).subscribe(
      _ => this.router.navigate(['games'])
    )
  }
}

