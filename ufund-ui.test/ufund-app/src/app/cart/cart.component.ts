import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart.service';
import { Need } from '../need';
import { User } from '../user';
import { UserService } from '../user.service';
import { LoginService } from '../login.service';
import { Router } from '@angular/router';
import { NeedService } from '../need.service';
import { Observable, forkJoin } from 'rxjs';
import { error } from 'console';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItems: Need[] = [];
  user: User | undefined;
  errorMessage: String = "";

  constructor(private cartService: CartService,
    private loginservice: LoginService,
    private userservice: UserService,
    private router: Router,
    private needservice: NeedService
  ) {}

  ngOnInit(): void {
    this.getUser();
  }
  getUser(): void {
    const userId = this.loginservice.getCurrentUser().id;
    this.userservice.getUser(userId)
      .subscribe(user => {
        this.user = user;
      });
  }
  
  removeProduct(id: number): void {
    if(!this.user) {
      return;
    }
    for(let i = 0; i < this.user.cart.length; i++) {
      if(this.user.cart[i].id == id) {
        this.user.cart.splice(i, 1);
        this.userservice.updateUser(this.user).subscribe(() => {});
      }
    }
  }

  calculateTotal(): number {
    if (!this.user || !this.user.cart) {
      return 0;
    }
    return this.user.cart.reduce((total, item) => total + item.price * item.quantity, 0);
  }

  updateProduct(need: Need, quantityInput: HTMLInputElement): void {
    if(!this.user) {
      return;
    }
    var quantity = parseInt(quantityInput.value);
    if(isNaN(quantity)) {
      return;
    }
    
    let observableProduct: Observable<Need> = this.needservice.getNeed(need.id);
    observableProduct.subscribe((product: Need) => {
      if(quantity > product.quantity) {
        this.errorMessage = "Not enough of this product in stock."
      } else {
        if(this.user) {
          for(let i = 0; i < this.user.cart.length; i++) {
            if(this.user.cart[i].id == need.id) {
              this.user.cart[i].quantity = quantity;
              this.userservice.updateUser(this.user).subscribe(() => {});
            }
          }
        }
      }
    });
  }
}
