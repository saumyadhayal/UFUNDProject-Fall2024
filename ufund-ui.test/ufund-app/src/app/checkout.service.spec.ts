import { TestBed } from '@angular/core/testing';
import { CheckoutService } from './checkout.service';
import { CartService } from './cart.service';
import { Need } from './need';
import { of } from 'rxjs';

describe('CheckoutService', () => {
  let checkoutService: CheckoutService;
  let cartServiceSpy: jasmine.SpyObj<CartService>;

  beforeEach(() => {
    const cartSpy = jasmine.createSpyObj('CartService', ['checkout']);

    TestBed.configureTestingModule({
      providers: [
        CheckoutService,
        { provide: CartService, useValue: cartSpy }
      ]
    });

    checkoutService = TestBed.inject(CheckoutService);
    cartServiceSpy = TestBed.inject(CartService) as jasmine.SpyObj<CartService>;
  });

  it('should be created', () => {
    expect(checkoutService).toBeTruthy();
  });

  it('should perform checkout and return cart items', (done) => {
    const mockCartItems: Need[] = [
      { id: 1, name: 'Shoes', price: 50, quantity: 1, type: 'apparel' },
      { id: 2, name: 'Food', price: 20, quantity: 3, type: 'essentials' }
    ];

    // Mock the checkout method to return our mockCartItems
    cartServiceSpy.checkout.and.returnValue(of(mockCartItems));

    checkoutService.performCheckout().subscribe(items => {
      expect(items).toEqual(mockCartItems); // Verify the returned items match
      expect(cartServiceSpy.checkout).toHaveBeenCalled(); // Verify checkout was called on CartService
      done();
    });
  });
});

