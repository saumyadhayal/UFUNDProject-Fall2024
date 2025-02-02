// src/app/app-routing.module.ts

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NeedDetailComponent } from './need-detail/need-detail.component';
import { NeedsComponent } from './needs/needs.component';
import { LoginComponent } from './login/login.component'; // Ensure you import your LoginComponent
import { AuthGuard } from './auth.guard'; // Import the AuthGuard
import { FormsModule } from '@angular/forms';
import { ManagerInventoryComponent } from './manager-inventory/manager-inventory.component';
import { NeedSearchComponent } from './need-search/need-search.component';
import { CartComponent } from './cart/cart.component';
import { SignupComponent } from './signup/signup.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { PurchasedComponent } from './purchased/purchased.component';
import { LeaderboardComponent } from './leaderboard/leaderboard.component';



const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent }, // Route to LoginComponent
  { path: 'admin-dashboard', component:  NeedsComponent}, // Admin Dashboard Route
  { path: 'user-dashboard', component: DashboardComponent }, // User Dashboard Route
  { path: 'detail/:id', component: NeedDetailComponent },
  { path: 'cart', component: CartComponent},
  { path: 'editNeed/:id', component: ManagerInventoryComponent}, 
  { path: 'signup', component: SignupComponent},
  { path: 'checkout', component: CheckoutComponent },
  { path: 'editNeed/:id', component: ManagerInventoryComponent},
  { path: 'purchased', component: PurchasedComponent},
  { path: 'needs', component: NeedsComponent},
  { path: 'leaderboard', component: LeaderboardComponent}


];


@NgModule({
  imports: [RouterModule.forRoot(routes), FormsModule],
  exports: [RouterModule],
})
export class AppRoutingModule {}
