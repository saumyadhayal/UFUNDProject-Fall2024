import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-manager-inventory',
  templateUrl: './manager-inventory.component.html',
  styleUrl: './manager-inventory.component.css'
})
export class ManagerInventoryComponent {
  need: Need | undefined;

  constructor(
    private route: ActivatedRoute,
    private needService: NeedService,
    private router: Router,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getNeed();
  }

  getNeed(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.needService.getNeed(id)
      .subscribe(need => {
        this.need = need;
      });
  }

  editName(name: string): void {
    if(this.need) {
      this.need.name = name;
      this.needService.updateNeed(this.need).subscribe();
      this.router.navigate(['../needs']);
    }
  }

  editPrice(price: number): void {
    if(this.need && !isNaN(price) && price >= 0) {
      this.need.price = price;
      this.needService.updateNeed(this.need).subscribe();
      this.router.navigate(['../needs']);
    }
  }

  editQuantity(quantity: number): void {
    if(this.need && !isNaN(quantity) && quantity >= 0) {
      this.need.quantity = quantity;
      this.needService.updateNeed(this.need).subscribe();
      this.router.navigate(['../needs']);
    }
  }
  goBack(): void {
    this.location.back();
  }
}