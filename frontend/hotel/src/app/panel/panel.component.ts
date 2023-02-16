import {Component, OnInit} from '@angular/core';
import {User} from "../_models/user";
import {UserService} from "../_service/user.service";

@Component({
  selector: 'app-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.scss']
})
export class PanelComponent implements OnInit {

  users: User[] = [];

  constructor(
    private userService: UserService
  ) {
    this.userService.getUsers().subscribe(
      response => this.users = response
    )
  }

  ngOnInit(): void {

  }

}
