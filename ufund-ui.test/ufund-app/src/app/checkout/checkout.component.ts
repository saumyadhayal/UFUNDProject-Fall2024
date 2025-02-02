import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { UserService } from '../user.service';
import { NeedService } from '../need.service';
import { LoginService } from '../login.service';
import { User } from '../user';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  checkedOutItems: Need[] = [];
  totalCost: number = 0; 
  cardName: string = '';
  cardNumber: string = '';
  expirationDate: string = '';
  cvc: string = '';
  purchaseComplete: boolean = false;
  user: User | undefined;
  errorMessage: string = "";
  userId: number = 0;

  constructor(private userService: UserService,
              private loginService: LoginService,
              private needService: NeedService,
              private router: Router,
              private location: Location) {}

  ngOnInit(): void {
    this.getUser();

  }

  getTotalPrice(cart: Need[]): number {
    return cart.reduce((total, item) => total + (item.price * item.quantity), 0);
  }

  getUser(): void {
    this.userId = this.loginService.getCurrentUser().id;
    this.userService.getUser(this.userId)
      .subscribe(user => {
        this.user = user;
      });
  }

  validateCardDetails(): boolean {
    // Validate card number (16 digits)
    const cardNumberValid = /^[0-9]{16}$/.test(this.cardNumber);
    if (!cardNumberValid) {
      this.errorMessage = "Card number must be 16 digits.";
      return false;
    }

    // Validate expiration date (MM/YY format)
    const expiryDateValid = /^(0[1-9]|1[0-2])\/\d{2}$/.test(this.expirationDate);
    if (!expiryDateValid) {
      this.errorMessage = "Expiration date must be in MM/YY format.";
      return false;
    }

    // Validate CVC (3 digits)
    const cvcValid = /^[0-9]{3}$/.test(this.cvc);
    if (!cvcValid) {
      this.errorMessage = "CVC must be exactly 3 digits.";
      return false;
    }

    // Validate card holder name (non-empty)
    if (!this.cardName.trim()) {
      this.errorMessage = "Card holder's name is required.";
      return false;
    }

    this.errorMessage = ""; // Clear any previous errors if validation passed
    return true;
  }

  onSubmit(): void {
    if (!this.user || !this.user.cart || this.user.cart.length === 0) {
      this.errorMessage = "You can't purchase an empty cart.";
      return;
    }

    // Validate credit card details
    if (!this.validateCardDetails()) {
      return;
    }

    this.totalCost = this.getTotalPrice(this.user.cart);

    // Process payment if validations pass
    this.purchaseComplete = true;

    this.userService.getUser(this.userId).subscribe((currentUser: User) => {
      const shoppingCart: Need[] = currentUser.cart;

      shoppingCart.forEach((userNeed: Need) => {
        this.needService.getNeed(userNeed.id).subscribe((need: Need) => {
          need.quantity -= userNeed.quantity;
          if (need.quantity <= 0) {
            this.needService.deleteNeed(need.id).subscribe(() => {});
          }
          this.needService.updateNeed(need).subscribe(() => {});
        });
      });
    });

    this.user.totalSpent = (this.user.totalSpent || 0) + this.totalCost;

    // Clear user's cart and navigate to purchase confirmation
    this.user.cart = [];
    this.userService.updateUser(this.user).subscribe(() => {
      this.router.navigate(['/purchased']);
    });
  }

  goBack(): void {
    this.location.back();
  }
}
