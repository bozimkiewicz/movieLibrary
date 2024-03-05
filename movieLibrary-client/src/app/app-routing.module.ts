import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './core/home/home.component';
import { MoviesComponent } from './modules/movie/components/movies.component';
import { MovieFormComponent } from './modules/movie/components/movie-form/movie-form.component';
import { MovieItemComponent } from './modules/movie/components/movie-item/movie-item.component';
import { PageNotFoundComponent } from './shared/components/page-not-found/page-not-found.component';
import { UserProfileComponent } from './auth/user-profile/user-profile.component';
import { AuthGuard } from './auth/auth.guard';
const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: HomeComponent
  },
  {
    path: 'movies',
    component: MoviesComponent,
  },
  {
    path: 'movies/:id',
    component: MovieItemComponent
  },
  {
    path: 'movies/:id/edit',
    component: MovieFormComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'add-movie',
    component: MovieFormComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'profile',
    component: UserProfileComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'not-found',
    component: PageNotFoundComponent
  },
  {
    path: '**',
    redirectTo: 'not-found'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
