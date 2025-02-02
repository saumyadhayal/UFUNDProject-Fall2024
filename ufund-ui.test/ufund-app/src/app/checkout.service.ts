import { Injectable } from '@angular/core';
import { Need } from './need';
import { Observable } from 'rxjs';
import { CartService } from './cart.service';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {
  constructor(private cartService: CartService) {}

 
  performCheckout(): Observable<Need[]> {
    return this.cartService.checkout();
  }


}
