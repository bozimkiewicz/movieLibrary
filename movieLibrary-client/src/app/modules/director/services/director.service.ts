import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Director } from '../models/director.model';
import { DirectorForm } from '../../movie/components/movie-form/models/director.form.dto';

@Injectable({
  providedIn: 'root'
})
export class DirectorService {
  private apiUrl: string = 'http://localhost:8080/api/directors';

  public constructor(private http: HttpClient) { }

  public getDirectors(): Observable<Director[]> {
    return this.http.get<Director[]>(this.apiUrl);
  }

  public getDirector(id: string): Observable<Director> {
    return this.http.get<Director>(`${this.apiUrl}/${id}`);
  }

  public createDirector(director: DirectorForm): Observable<Director> {
    return this.http.post<Director>(this.apiUrl, director);
  }

  public updateDirector(director: Director): Observable<Director> {
    return this.http.put<Director>(`${this.apiUrl}/${director.id}`, director);
  }

  public deleteDirector(id: string): Observable<Director> {
    return this.http.delete<Director>(`${this.apiUrl}/${id}`);
  }
}
