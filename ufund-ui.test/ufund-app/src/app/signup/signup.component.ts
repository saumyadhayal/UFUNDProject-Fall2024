import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { LoginService } from '../login.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent implements OnInit {
  providedUsername: string | null | undefined;
  users: User[] | undefined;
  errorMessage: string | undefined;

  constructor(
    private userService: UserService,
    private loginService: LoginService,
    private router: Router,
    private route: ActivatedRoute 
  ) {}

  ngOnInit() {
    this.userService.getUsers().subscribe({
      next: (users) => {this.users = users;},
      error: (e) => {
        console.error("Check ufund-api: "+e);
      }
    });
    this.providedUsername = this.route.snapshot.paramMap.get('username');
  }

  checkSignup(username: string, password: string, Admin: boolean): void {
    if (this.users == undefined) {return;}
    if (!this.validUsername(username)) {return;}
    
    let newUser:User = {
      id: 0,
      name: username,
      cart: [],
      isAdmin: Admin,
      password: password,
      totalSpent: 0,
    }
    this.userService.addUser(newUser)
      .subscribe(user => {
        this.loginService.setCurrentUser(user);
      });
      if (newUser.isAdmin){
        this.router.navigate(['/needs'])
      }else{
      this.router.navigate(['/user-dashboard']);}
  }

  validUsername(username: string): boolean {
    this.errorMessage = "";
    if (this.users == undefined) {return false;}
    if (username == "") {
      this.errorMessage = "Please enter a username.";
      return false;
    }
    for(let i = 0; i < this.users.length; i++) {
      if(this.users[i].name == username) {
        this.errorMessage = "Username is already taken. Please choose a different username.";
        return false;
      }
    }
    return true;
  }

}