import { Injectable } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {
  public constructor(
    private authService: AuthService,
  ) {}

  public canActivate(): Observable<boolean> {
    return this.authService.isAuthenticated$.pipe(
      map((isAuth: boolean) => {
        if (!isAuth) {
          this.authService.loginWithRedirect();

          return false;
        }

        return true;
      })
    );
  }
}