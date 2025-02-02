import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { User } from '../user';
import { UserService } from '../user.service';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  users: User[] | undefined;
  user: User | undefined;
  userId: number =0;
  errorMessage: string | undefined;

  constructor( private userService: UserService, private loginService: LoginService, private router: Router ) { }

ngOnInit(): void {
  this.userService.getUsers().subscribe({
    next: (users) => {
      this.users = users;
      console.log("Fetched users:", this.users);
    },
    error: (e) => {
      console.error("Error fetching users:", e);
    }
  });
  this.getUser();
}
getUser(): void {
  this.userId = this.loginService.getCurrentUser().id;
this.userService.getUser(this.userId)
  .subscribe(user => {
    this.user = user;
  });
}

  
checkLogin(username: string, password: string): void {
  // Fetch users from the backend
  this.userService.getUsers().subscribe({
    next: (users) => {
      this.users = users;

      if (!this.users) {
        return;
      }

      if (username === "") {
        this.errorMessage = "Please enter your username.";
        return;
      }

      // Find the user by username
      this.user = this.users.find(user => user.name === username);

      if (!this.user) {
        const toUrl = "signup/" + username;
        this.router.navigate([toUrl]);
        return;
      }

      // Check if the entered password is correct
      if (this.user.password === password) {
        this.loginService.setCurrentUser(this.user);
        
        // Navigate based on the admin checkbox and user role
        if (this.user.isAdmin) {
          this.router.navigate(['/needs']); // Admin path
        } else if (!this.user.isAdmin) {
          this.router.navigate(['/user-dashboard']); // Regular user path
        } else {
          this.errorMessage = "Admin status does not match.";
        }
      } else {
        this.errorMessage = "Please check your password.";
      }
    },
    error: (e) => {
      console.error("Error fetching users:", e);
      this.errorMessage = "Unable to fetch users. Please try again later.";
    }
  });
}
}