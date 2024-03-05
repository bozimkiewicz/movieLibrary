import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CustomValidators } from 'src/app/shared/validators/custom-validators.validator';
import { MovieService } from '../../services/movie.service';
import { MovieDetailsService } from '../../services/movie-details.service';
import { DirectorService } from 'src/app/modules/director/services/director.service';
import { ActorService } from 'src/app/modules/actors/services/actors.service';
import { Observable, Subscription, forkJoin } from 'rxjs';
import { MovieDetails } from '../../models/movie-details.model';
import { Director } from 'src/app/modules/director/models/director.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Actor } from 'src/app/modules/actors/models/actor.model';
import { MovieForm } from './models/movie.form.dto';
import { DirectorForm } from './models/director.form.dto';
import { ActorForm } from './models/actor.form.dto';
import { MovieDetailsForm } from './models/movie-details.form.dto';

@Component({
  selector: 'app-movie-form',
  templateUrl: './movie-form.component.html',
  styleUrls: ['./movie-form.component.scss']
})
export class MovieFormComponent implements OnInit {
  public movieForm: FormGroup = {} as FormGroup;
  public movieSubscription!: Subscription;
  public editMovieSub!: Subscription;
  public dataSub!: Subscription;
  public authSub!: Subscription;
  public isEditMode: boolean = false;
  private movie: MovieForm = {} as MovieForm;
  private movieDetails: MovieDetailsForm = {} as MovieDetailsForm;
  private director: DirectorForm = {} as DirectorForm;
  private actors: ActorForm[] = [];
  protected loading: boolean = true;

  public constructor(
    private fb: FormBuilder,
    private movieService: MovieService,
    private movieDetailsService: MovieDetailsService,
    private directorService: DirectorService,
    private actorService: ActorService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  public ngOnInit(): void {
    this.initForm();
    if (this.route.snapshot.params['id']) {
      this.isEditMode = true;
      this.editMovieSub = this.movieService.getMovie(this.route.snapshot.params['id']).subscribe((movie: MovieForm) => {
        this.movie = movie;
        this.dataSub = forkJoin([
          this.movieDetailsService.getMovieDetails(movie.movieDetails),
          this.directorService.getDirector(movie.director),
          forkJoin(movie.actors.map((actorId: string) => this.actorService.getActor(actorId)))
        ]).subscribe(([movieDetails, director, actors]: [MovieDetails, Director, Actor[]]) => {
          this.movieDetails = movieDetails;
          this.director = director;
          this.actors = actors.map((actor: Actor) => {
            return {
              firstName: actor.firstName,
              lastName: actor.lastName
            };
          });
          this.loading = false;
          this.movieForm.patchValue({
            title: this.movie.title,
            watched: this.movie.watched,
            movieDetails: {...this.movieDetails, releaseDate: new Date(this.movieDetails.releaseDate)},
            director: this.director,
            actors: this.actors
          });
        });
      });
    } else {
      this.loading = false;
    }
  }

  public initForm(): void {
    const movieDetailsFg: FormGroup = this.fb.group({
      description: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(200)]],
      duration: ['', [Validators.required, Validators.min(1)]],
      releaseDate: [new Date(), [Validators.required]],
      genre: ['', [Validators.required]],
      country: ['', [Validators.required]],
      rating: [0, [Validators.required, Validators.min(1), Validators.max(10)]],
    });

    const directorFg: FormGroup = this.fb.group({
      firstName: ['', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(30), 
        CustomValidators.noSpaceAllowed]],
      lastName: ['', [
        Validators.required, 
        Validators.minLength(2),
        Validators.maxLength(30),
        CustomValidators.noSpaceAllowed]],
    });

    this.movieForm = this.fb.group({
      title: ['', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(100),
        Validators.pattern(/^[a-zA-Z0-9\s\-.,!:'"?&()]+$/)
      ]],
      watched: false,
      movieDetails: movieDetailsFg,
      director: directorFg,
      actors: this.fb.array([
        this.fb.group({
          firstName: ['', [
            Validators.required, 
            Validators.minLength(2),
            Validators.maxLength(30),
            CustomValidators.noSpaceAllowed]],
          lastName: ['', [
            Validators.required, 
            Validators.minLength(2),
            Validators.maxLength(30),
            CustomValidators.noSpaceAllowed]],
        }),
      ]),
    });
  }

  public get actorForms(): FormArray {
    return this.movieForm.get('actors') as FormArray;
  }

  public addActor(): void {
    const actorFg: FormGroup = this.fb.group({
      firstName: ['', [
        Validators.required, 
        Validators.minLength(2),
        Validators.maxLength(30),
        CustomValidators.noSpaceAllowed]],
      lastName: ['', [
        Validators.required, 
        Validators.minLength(2),
        Validators.maxLength(30),
        CustomValidators.noSpaceAllowed]],
    });

    this.actorForms.push(actorFg);
  }

  public deleteActor(index: number): void {
    this.actorForms.removeAt(index);
  }

  public submit(): void {
    const actorCreationObservables: Observable<Actor>[] = [];

    if (this.isEditMode) {
      this.movieForm.value.actors.forEach((actor: Actor) => {
        actorCreationObservables.push(this.actorService.createActor(actor));
      });
      
      this.movieSubscription = forkJoin([
        this.movieDetailsService.updateMovieDetails({...this.movieForm.value.movieDetails, id: this.movie.movieDetails}),
        this.directorService.createDirector(this.movieForm.value.director),
        forkJoin(actorCreationObservables)
      ]).subscribe(([movieDetails, director, actors]: [MovieDetails, Director, Actor[]]) => {
        const actorsIds: string[] = [];
        
        actors.forEach((actor: Actor) => {
          actorsIds.push(actor.id);
        });
        this.movieService.updateMovie(
          { 
            id: this.route.snapshot.params['id'],
            title: this.movieForm.value.title,
            watched: this.movieForm.value.watched, 
            movieDetails: movieDetails.id, 
            director: director.id,
            actors: actorsIds 
          }
        ).subscribe(() => {
          this.router.navigate(['/movies']);
        });
      });
    } else {
      this.movieForm.value.actors.forEach((actor: Actor) => {
        actorCreationObservables.push(this.actorService.createActor(actor));
      });
      
      this.movieSubscription = forkJoin([
        this.movieDetailsService.createMovieDetails(this.movieForm.value.movieDetails),
        this.directorService.createDirector(this.movieForm.value.director),
        forkJoin(actorCreationObservables)
      ]).subscribe(([movieDetails, director, actors]: [MovieDetails, Director, Actor[]]) => {
        const actorsIds: string[] = [];
        
        actors.forEach((actor: Actor) => {
          actorsIds.push(actor.id);
        });
        this.movieService.createMovie(
          { 
            title: this.movieForm.value.title,
            watched: this.movieForm.value.watched, 
            movieDetails: movieDetails.id, 
            director: director.id,
            actors: actorsIds 
          }
        ).subscribe(() => {
          this.router.navigate(['/movies']);
        });
      });
    }

  }
}
