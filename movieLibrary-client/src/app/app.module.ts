import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MoviesComponent } from './modules/movie/components/movies.component';
import { HomeComponent } from './core/home/home.component';
import { HeaderComponent } from './core/header/header.component';
import { MovieListComponent } from './modules/movie/components/movie-list/movie-list.component';
import { MovieItemComponent } from './modules/movie/components/movie-item/movie-item.component';
import { HttpClientModule } from '@angular/common/http';
import { MovieFormComponent } from './modules/movie/components/movie-form/movie-form.component';
import { UserProfileComponent } from './auth/user-profile/user-profile.component';
import { AuthComponent } from './auth/auth.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CheckboxModule } from 'primeng/checkbox';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { MultiSelectModule } from 'primeng/multiselect';
import { DropdownModule } from 'primeng/dropdown';
import { MenubarModule } from 'primeng/menubar';
import { CalendarModule } from 'primeng/calendar';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputNumberModule } from 'primeng/inputnumber';
import { RatingModule } from 'primeng/rating';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ConvertDurationPipe } from './shared/pipes/convert-duration.pipe';
import { PageNotFoundComponent } from './shared/components/page-not-found/page-not-found.component';
import { AuthModule, AuthService } from '@auth0/auth0-angular';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    MoviesComponent,
    MovieListComponent,
    MovieItemComponent,
    MovieFormComponent,
    ConvertDurationPipe,
    PageNotFoundComponent,
    AuthComponent,
    UserProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    CheckboxModule,
    ButtonModule,
    TableModule,
    MultiSelectModule,
    DropdownModule,
    MenubarModule,
    CalendarModule,
    RatingModule,
    InputTextModule,
    InputTextareaModule,
    InputNumberModule,
    BrowserAnimationsModule,
    ToastModule,
    ConfirmDialogModule,
    AuthModule.forRoot({
      domain: 'dev-u4xbdsylxpabjcjo.us.auth0.com',
      clientId: 'NRUrcgcItkQPigGhfCoolJIVQs1BKjVy',
      authorizationParams: {
        redirect_uri: window.location.origin
      }
    })
  ],
  providers: [AuthService, MessageService, ConfirmationService],
  bootstrap: [AppComponent]
})
export class AppModule { }
