import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrl: './leaderboard.component.css'
})

export class LeaderboardComponent implements OnInit {
  users: User[] = [];
  allUsers: User[] = [];

  constructor(
    private userService: UserService
   ) {}


  ngOnInit(): void {
    this.getAllUsers();
  }

  getAllUsers(): void {
    this.userService.getUsers()
      .subscribe(users => {
        this.allUsers = users.sort((a, b) => b.totalSpent - a.totalSpent);
      });
  }
}
