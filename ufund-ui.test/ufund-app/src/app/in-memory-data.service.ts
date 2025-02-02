import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Need } from './need';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const needs = [
      { id: 1, name: 'Food Supplies', price: 100, quantity: 200, type: 'Essential' },
      { id: 2, name: 'Clothing', price: 50, quantity: 150, type: 'Essential' },
      { id: 3, name: 'Books', price: 25, quantity: 100, type: 'Educational' },
      { id: 4, name: 'Toys', price: 15, quantity: 50, type: 'Non-Essential' },
      { id: 5, name: 'School Supplies', price: 20, quantity: 75, type: 'Educational' },
      { id: 6, name: 'Medical Supplies', price: 200, quantity: 30, type: 'Essential' },
      { id: 7, name: 'Furniture', price: 500, quantity: 10, type: 'Non-Essential' },
      { id: 8, name: 'Hygiene Kits', price: 40, quantity: 80, type: 'Essential' }
    ];
    return {needs};
  }

  // Overrides the genId method to ensure that a need always has an id.
  // If the needs array is empty,
  // the method below returns the initial number (11).
  // if the needs array is not empty, the method below returns the highest
  // need id + 1.
  genId(needs: Need[]): number {
    return needs.length > 0 ? Math.max(...needs.map(need => need.id)) + 1 : 11;
  }
}
