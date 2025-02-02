import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'] // Corrected from styleUrl to styleUrls
})

export class AppComponent {
  title = 'Toys for Tots'; // Replace with your app title
  showLogoutButton: boolean = false; // Flag to control logout button visibility
  showveiwcartButton: boolean = false; 
  showleaderboardButton: boolean = false; 

  constructor(public authService: AuthService, private router: Router, private route: ActivatedRoute) { } // Inject AuthService and Routera

  ngOnInit(): void {
    // Check the current URL when the component initializes
    this.router.events.subscribe(() => {
      const currentRoute = this.router.url;
      // Define conditions to show the logout button (adjust routes as needed)
      if (currentRoute === '/user-dashboard'){
        this.showveiwcartButton = true;
        this.showleaderboardButton = true;
      } else {
        this.showveiwcartButton = false;
        this.showleaderboardButton = false;
      }
      if (currentRoute !== '/login' && currentRoute !== '/signup') {
        this.showLogoutButton = true;
      } else {
        this.showLogoutButton = false;
      }
    });
  }

  logout(): void {
    this.authService.logout(); // Call logout method from AuthService
    this.router.navigate(['/login']); // Navigate to the login page after logout
  }

  Viewcart(): void {
    this.router.navigate(['/cart']); 
  }

  Leaderboard(): void {
    this.router.navigate(['/leaderboard']); 
  }
  
}
