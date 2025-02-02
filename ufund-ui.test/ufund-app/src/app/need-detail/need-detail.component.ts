import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

import { Need } from '../need';
import { NeedService } from '../need.service';
import { CartService } from '../cart.service';
import { UserService } from '../user.service';
import { LoginService } from '../login.service';
import { User } from '../user';

@Component({
  selector: 'app-need-detail',
  templateUrl: './need-detail.component.html',
  styleUrls: ['./need-detail.component.css']
})
export class NeedDetailComponent implements OnInit {

  need: Need | undefined;
  user: User | undefined;

  constructor(
    private route: ActivatedRoute,
    private needService: NeedService,
    private cartService: CartService,
    private location: Location,
    private userS: UserService,
    private loginservice: LoginService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getNeed();
    this.getUser();
  }
  
  getNeed(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.needService.getNeed(id)
      .subscribe(need => this.need = need);
  }
  getUser(): void {
    const userId = this.loginservice.getCurrentUser().id;
    this.userS.getUser(userId)
      .subscribe(user => {
        this.user = user;
      });
  }

  addToCart(need: Need): void {
    if(!this.user || need.quantity == 0) {
      return;
    }
    for(let i = 0; i < this.user.cart.length; i++) {
      if(this.user.cart[i].id == need.id) {
        // need already in cart
        return;
      }
    }
    let Thisneed = need;
    Thisneed.quantity = 1;
    this.user.cart.push(Thisneed);
    this.userS.updateUser(this.user).subscribe(() => {this.router.navigate(['../cart']);});
  }

  save(): void {
    if (this.need) {
      this.needService.updateNeed(this.need)
        .subscribe(() => this.goBack());
    }
  }

  goBack(): void {
    this.location.back();
  }
}
