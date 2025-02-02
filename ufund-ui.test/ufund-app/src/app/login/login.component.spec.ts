// src/app/login/login.component.ts

import { Component, NgModule } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service'; // Import AuthService
import { FormsModule, NgModel } from '@angular/forms';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {
errorMessage: any;
checkLogin(arg0: string,arg1: string) {
throw new Error('Method not implemented.');
}
  userRole: string = ''; // Define userRole property to hold user input

  constructor(private router: Router, private authService: AuthService) { } // Inject Router and AuthService

  login(): void {
    if (this.authService.login(this.userRole)) { // Call login method from AuthService
      // Redirect based on user role
      if (this.userRole === 'admin') {
        this.router.navigate(['/admin-dashboard']); // Navigate to Admin Dashboard
      } else if (this.userRole === 'user') {
        this.router.navigate(['/user-dashboard']); // Navigate to User Dashboard
      }
    } else {
      alert('Please enter a valid role: admin or user'); // Alert for invalid role
    }
  }
}

