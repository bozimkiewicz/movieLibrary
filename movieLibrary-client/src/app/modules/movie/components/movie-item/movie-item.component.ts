import { Component, OnInit } from '@angular/core';
import { MovieDetailsService } from '../../services/movie-details.service';
import { Subscription, forkJoin } from 'rxjs';
import { MovieDetails } from '../../models/movie-details.model';
import { MovieService } from '../../services/movie.service';
import { DirectorService } from 'src/app/modules/director/services/director.service';
import { ActorService } from 'src/app/modules/actors/services/actors.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Movie } from '../../models/movie.model';
import { Director } from 'src/app/modules/director/models/director.model';
import { Actor } from 'src/app/modules/actors/models/actor.model';
import { AuthService } from '@auth0/auth0-angular';
import { ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-movie-item',
  templateUrl: './movie-item.component.html',
  styleUrls: ['./movie-item.component.scss']
})
export class MovieItemComponent implements OnInit {
  private movieSubscription!: Subscription;
  private detailsSubscription!: Subscription;
  private deleteSubscription!: Subscription;
  public movieDetails: MovieDetails = {} as MovieDetails;
  public movie: Movie = {} as Movie;
  public director: Director = {} as Director;
  public actors: Actor[] = [];
  public isAuth: boolean = false;

  public constructor(
    private movieService: MovieService,
    private movieDetailsService: MovieDetailsService,
    private directorService: DirectorService,
    private actorService: ActorService,
    private route: ActivatedRoute,
    private router: Router,
    private confirmationService: ConfirmationService,
    public auth: AuthService,
  ) { }

  public ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.movieSubscription = this.movieService.getMovie(params['id']).subscribe((movie: Movie) => {
        this.movie = movie;
        this.detailsSubscription = forkJoin([
          this.movieDetailsService.getMovieDetails(movie.movieDetails),
          this.directorService.getDirector(movie.director),
          forkJoin(movie.actors.map((actorId: string) => this.actorService.getActor(actorId)))
        ]).subscribe(([movieDetails, director, actors]: [MovieDetails, Director, Actor[]]) => {
          this.movieDetails = movieDetails;
          this.director = director;
          this.actors = actors;
        });
      });
    });

    this.auth.isAuthenticated$.subscribe((isAuth: boolean) => {
      this.isAuth = isAuth;
    });
  }

  public confirm(): void {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to delete this movie?',
      header: 'Movie Deletion',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.deleteMovie();
      },
    });
  }
  public editMovie(): void {
    this.router.navigate(['movies', this.movie.id, 'edit']);
  }

  public deleteMovie(): void {
    this.deleteSubscription = forkJoin([
      this.movieDetailsService.deleteMovieDetails(this.movie.movieDetails),
      this.movieService.deleteMovie(this.movie.id),
    ]).subscribe(() => {
      this.router.navigate(['movies']);
    });
  }
}
