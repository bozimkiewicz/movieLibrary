import { Component, OnInit } from '@angular/core';
import { AuthService, User } from '@auth0/auth0-angular';
import { MenuItem } from 'primeng/api';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  public items: MenuItem[] = [];
  public itemsUser: MenuItem[] = [];
  public isAuth: boolean = false;
  public user: User | null | undefined = null;
  public isAuthSubscription!: Subscription;
  public userSubscription!: Subscription;

  public constructor(
    public auth: AuthService
  ) { }

  public ngOnInit(): void {
    this.isAuthSubscription = this.auth.isAuthenticated$.subscribe((isAuth: boolean) => {
      this.isAuth = isAuth;

      if (isAuth) {
        this.userSubscription = this.auth.user$.subscribe((user: User | null | undefined) => {
          this.user = user;

          this.itemsUser = [
            {
              label:this.user?.email,
              items: [
                {
                  label: 'Profile',
                  icon: 'pi pi-user',
                  routerLink: '/profile'
                },
                {
                  label: 'Logout',
                  icon: 'pi pi-sign-out',
                  command: () => this.logout()
                }
              ]}
          ];
        });
      }
    });
    
    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-fw pi-home',
        routerLink: '/'
      },
      {
        label: 'Movies',
        icon: 'pi pi-fw pi-video',
        routerLink: 'movies'
      },
      {
        label: 'Add Movie',
        icon: 'pi pi-fw pi-plus',
        routerLink: 'add-movie'
      },
    ];
  }

  public logout(): void {
    this.auth.logout();
  }

}
