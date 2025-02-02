import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { User } from './user';
import { MessageService } from './message.service';
import { Need } from './need';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private current!: User;

  constructor() { }

  setCurrentUser(Set: User) {
    this.current = Set;
    console.log(this.current);
  }

  getCurrentUser(): User {
    return this.current;
  }
}