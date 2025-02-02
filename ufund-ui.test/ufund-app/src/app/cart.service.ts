import { Injectable } from '@angular/core';
import { Need } from './need';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cart: Need[] = [];

  constructor() {}

  addToCart(need: Need): void {
    this.cart.push(need);
  }

  removeFromCart(needId: number): void {
    this.cart = this.cart.filter(item => item.id !== needId);
  }

  getCartItems(): Observable<Need[]> {
    return of(this.cart);
  }

  checkout(): Observable<Need[]> {
    const itemsToCheckout = [...this.cart]; // clone the cart items
    this.cart = []; // clear the cart after checkout
    return of(itemsToCheckout); // return the items as an observable
  }
  
}
