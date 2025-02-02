// src/app/auth.service.ts

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
getUserRole() {
throw new Error('Method not implemented.');
}
  private isAuthenticated = false;
  private userRole: string = '';

  login(role: string): boolean {
    if (role.toLowerCase() === 'admin' || role.toLowerCase() === 'user') {
      this.isAuthenticated = true; // Set authenticated state to true
      this.userRole = role;
      return true; // Successful login
    }
    return false; // Failed login
  }

  logout(): void {
    this.isAuthenticated = false; // Clear authentication status
    this.userRole = '';
  }

  isUserAuthenticated(): boolean {
    return this.isAuthenticated; // Check if the user is authenticated
  }
}
