import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { CartService } from '../cart.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  needs: Need[] = [];
  cartItems: Need[] = [];
  allNeeds: Need[] = []; // New array to store all needs
  filteredNeedsType: Need[] = [];    // Filtered list of needs by type
  filteredNeedsPrice: Need[] = [];    // Filtered list of needs by price
  combinedFilteredNeeds: Need[] = [];
  uniqueTypes: string[] = [];   // Unique list of types for buttons
  uniquePrices: number[] = [];   // Unique list of prices for buttons
  activeFilterType: string | null = null; // Track the active filter
  activeFilterPrice: number | null = null; // Track the active filter
  TypedropdownOpen = false;              // Track if dropdown is open
  PricedropdownOpen = false;              // Track if dropdown is open

  constructor(
    private needService: NeedService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.getNeeds();
    this.getCartItems();
    this.getAllNeeds(); // Fetch all needs
  }

  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => {
      this.needs = needs;
      this.filteredNeedsType = needs; // Initially show all needs
      this.filteredNeedsPrice = needs; // Initially show all needs
      this.combinedFilteredNeeds = needs; // Set combinedFilteredNeeds to show all needs initially
      this.updateUniqueTypes(); // Extract unique categories for filter buttons
    });
  }

  applyCombinedFilters(): void {
    this.combinedFilteredNeeds = this.needs.filter(need =>
      (!this.activeFilterType || need.type === this.activeFilterType) && (!this.activeFilterPrice || need.price <= this.activeFilterPrice)
    );
    this.TypedropdownOpen = false; // Close dropdown after selection
  }

  filterNeedsType(value: string): void {
    if (this.activeFilterType === value) {
      // If the same filter is clicked again, clear the filter
      this.activeFilterType = null;
    } else {
      // Apply the new filter
      this.activeFilterType = value;
    }
    this.applyCombinedFilters();
    this.TypedropdownOpen = false; // Close dropdown after selection
  }

  filterNeedsPrice(value: number): void {
    if (this.activeFilterPrice === value) {
      // If the same filter is clicked again, clear the filter
      this.activeFilterPrice = null;
    } else {
      // Apply the new filter
      this.activeFilterPrice = value;
    }
    this.applyCombinedFilters();
    this.PricedropdownOpen = false; // Close dropdown after selection
  }

  // Updates the list of unique categories dynamically
  updateUniqueTypes(): void {
    const typesSet = new Set(this.needs.map(need => need.type));
    this.uniqueTypes = Array.from(typesSet);
  }

  toggleDropdownType(): void {
    this.TypedropdownOpen = !this.TypedropdownOpen;
  }

  toggleDropdownPrice(): void {
    this.PricedropdownOpen = !this.PricedropdownOpen;
  }

  clearFilterType(): void {
    this.activeFilterType = null;
    this.applyCombinedFilters();
  }

  // Method to clear the active price filter
  clearFilterPrice(): void {
    this.activeFilterPrice = null;
    this.applyCombinedFilters();
  }

  // Method to clear all filters
  clearAllFilters(): void {
    this.activeFilterType = null;
    this.activeFilterPrice = null;
    this.applyCombinedFilters();
  }

  getCartItems(): void {
    this.cartService.getCartItems()
      .subscribe(cartItems => this.cartItems = cartItems);
  }
  getAllNeeds(): void {
    this.needService.getNeeds() // Fetch all needs
      .subscribe(needs => this.allNeeds = needs); // Store all needs
  }

  removeFromCart(id: number): void {
    this.cartService.removeFromCart(id);
    this.getCartItems(); // Refresh the cart items after removin
  }
}
