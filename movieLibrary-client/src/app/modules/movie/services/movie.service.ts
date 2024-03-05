import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie } from '../models/movie.model';
import { MovieForm } from '../components/movie-form/models/movie.form.dto';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private apiUrl: string = 'http://localhost:8080/api/movies';

  public constructor(private http: HttpClient) { }

  public getMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(this.apiUrl);
  }

  public getMovie(id: string): Observable<Movie> {
    return this.http.get<Movie>(`${this.apiUrl}/${id}`);
  }

  public createMovie(movie: MovieForm): Observable<Movie> {
    return this.http.post<Movie>(this.apiUrl, movie);
  }

  public updateMovie(movie: Movie): Observable<Movie> {
    return this.http.put<Movie>(`${this.apiUrl}/${movie.id}`, movie);
  }

  public deleteMovie(id: string): Observable<Movie> {
    return this.http.delete<Movie>(`${this.apiUrl}/${id}`);
  }
}
