import { Component, OnInit, ViewChild } from '@angular/core';
import { Movie } from '../../models/movie.model';
import { Subscription, forkJoin } from 'rxjs';
import { MovieService } from '../../services/movie.service';
import { MovieDetails } from '../../models/movie-details.model';
import { MovieDetailsInMovie } from '../../models/movie-details-in-movie.model';
import { MovieDetailsService } from '../../services/movie-details.service';
import { Table } from 'primeng/table';
import { Router } from '@angular/router';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.scss']
})
export class MovieListComponent implements OnInit {
  @ViewChild('dt') public dt!: Table;
  private movieSubscription!: Subscription;
  public movies: Movie[] = [];
  public movieDetails: MovieDetails[] = [];
  public displayedColumns: string[] = [];
  public dataSource: MovieDetailsInMovie[] = [];
  public totalRecords: number = 0;
  public genres: string[] = [];
  public countries: string[] = [];
  public selectedMovie!: MovieDetailsInMovie;
  
  
  public constructor(
    private movieService: MovieService, 
    private movieDetailsService: MovieDetailsService,
    private router: Router
  ) { }
  
  public ngOnInit(): void {
    this.displayedColumns = ['title', 'watched', 'genre', 'duration', 'rating', 'releaseDate', 'country'];
    this.movieSubscription = forkJoin([this.movieService.getMovies(), this.movieDetailsService.getAllMovieDetails()])
      .subscribe(([movies, movieDetails]: [Movie[], MovieDetails[]]) => {
        this.movies = movies;
        this.movieDetails = movieDetails;
        this.mapDataSource();
        this.totalRecords = this.dataSource.length;
        this.dataSource.forEach((movie: MovieDetailsInMovie) => {
          if (!this.genres.includes(movie.genre)) {
            this.genres.push(movie.genre);
          }
        });
        this.dataSource.forEach((movie: MovieDetailsInMovie) => {
          if (!this.countries.includes(movie.country)) {
            this.countries.push(movie.country);
          }
        });
        this.dataSource = this.dataSource.map((movie: MovieDetailsInMovie) => {
          return {
            ...movie,
            releaseDate: new Date(movie.releaseDate),
          };
        });
      });
  }

  public applyFilterGlobal($event: Event, stringVal: string): void {
    this.dt.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  public mapDataSource(): void {
    this.movies.forEach((movie: Movie) => {
      this.movieDetails.forEach((details: MovieDetails) => {
        
        if (movie.movieDetails === details.id) {
          this.dataSource.push({
            id: movie.id,
            title: movie.title,
            watched: movie.watched,
            genre: details.genre,
            duration: details.duration,
            rating: details.rating,
            releaseDate: details.releaseDate,
            country: details.country,
          });
        }
      });
    });
  }

  public onRowSelect(): void {
    this.router.navigate(['/movies/', this.selectedMovie.id]);
  }
}
