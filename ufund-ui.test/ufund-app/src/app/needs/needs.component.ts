import { Component, OnInit } from '@angular/core';

import { Need } from '../need';
import { NeedService } from '../need.service';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-needs',
  templateUrl: './needs.component.html',
  styleUrl: './needs.component.css',
})
export class NeedsComponent implements OnInit {

  needs: Need[] = [];

  constructor(private needService: NeedService, private router: Router) { }

  ngOnInit(): void {
    this.getNeeds();
  }

  getNeeds(): void {
    this.needService.getNeeds()
      .subscribe(needs => this.needs = needs);
  }

  add(name: string, price: number, quantity: number, type: string): void {
    name = name.trim();
    price = price;
    quantity = quantity;
    type = type.trim();
    
    if (!name || !price || !quantity || !type) { return; }

    this.needService.addNeed({name, price, quantity, type } as Need)
      .subscribe(need => {
        this.needs.push(need);
      });
  }

  delete(need: Need): void {
    this.needs = this.needs.filter(h => h !== need);
    this.needService.deleteNeed(need.id).subscribe();
  }
  logout(): void {
    this.router.navigate(['/login']);
  }

}
