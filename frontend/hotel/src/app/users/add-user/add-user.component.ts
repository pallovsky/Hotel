import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {AuthService} from "../../_service/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {

  token: string = ''

  addUserForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    role: new FormControl(''),
  });

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const localToken = localStorage.getItem('token')

    if (localToken == null) {
      this.router.navigate(['/login'])
    } else {
      this.token = localToken
    }
  }

  onSubmit() {
    const username = this.addUserForm.value['username']?.toString()
    const password = this.addUserForm.value['password']?.toString()
    const role = this.addUserForm.value['role']?.toString()

    this.authService.register(this.token, username, password, role).subscribe(
      _ => this.router.navigate(['users'])
    )
  }
}
