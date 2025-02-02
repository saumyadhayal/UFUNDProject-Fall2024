import { Component } from '@angular/core';
import { User } from '../user';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';
import { UserService } from '../user.service';
import { NeedService } from '../need.service';

@Component({
  selector: 'app-purchased',
  templateUrl: './purchased.component.html',
  styleUrl: './purchased.component.css'
})
export class PurchasedComponent {
  user: User | undefined;

  constructor(private userService: UserService,
              private loginService: LoginService,
              private router: Router) { }

  ngOnInit(): void {
    this.getUser();
  }

  getUser(): void {
    const userId = this.loginService.getCurrentUser().id;
    this.userService.getUser(userId)
      .subscribe(user => {
        this.user = user;
      });
  }

  logout(): void {
    this.router.navigate(['/login']);
  }

  goToDashboard(): void {
    this.router.navigate(['/user-dashboard']);
  }
}
