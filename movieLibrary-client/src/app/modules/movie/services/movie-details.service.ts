import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MovieDetails } from '../models/movie-details.model';
import { Observable } from 'rxjs';
import { MovieDetailsForm } from '../components/movie-form/models/movie-details.form.dto';

@Injectable({
  providedIn: 'root'
})
export class MovieDetailsService {
  private apiUrl: string = 'http://localhost:8080/api/movie-details';

  public constructor(private http: HttpClient) { }

  public getAllMovieDetails(): Observable<MovieDetails[]> {
    return this.http.get<MovieDetails[]>(this.apiUrl);
  }

  public getMovieDetails(id: string): Observable<MovieDetails> {
    return this.http.get<MovieDetails>(`${this.apiUrl}/${id}`);
  }

  public createMovieDetails(movieDetails: MovieDetailsForm): Observable<MovieDetails> {
    return this.http.post<MovieDetails>(this.apiUrl, movieDetails);
  }

  public updateMovieDetails(movieDetails: MovieDetails): Observable<MovieDetails> {
    return this.http.put<MovieDetails>(`${this.apiUrl}/${movieDetails.id}`, movieDetails);
  }

  public deleteMovieDetails(id: string): Observable<MovieDetails> {
    return this.http.delete<MovieDetails>(`${this.apiUrl}/${id}`);
  }
}
