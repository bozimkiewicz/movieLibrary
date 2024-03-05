import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Actor } from '../models/actor.model';
import { ActorForm } from '../../movie/components/movie-form/models/actor.form.dto';

@Injectable({
  providedIn: 'root'
})
export class ActorService {
  private apiUrl: string = 'http://localhost:8080/api/actors';

  public constructor(private http: HttpClient) { }

  public getActors(): Observable<Actor[]> {
    return this.http.get<Actor[]>(this.apiUrl);
  }

  public getActor(id: string): Observable<Actor> {
    return this.http.get<Actor>(`${this.apiUrl}/${id}`);
  }

  public createActor(actor: ActorForm): Observable<Actor> {
    return this.http.post<Actor>(this.apiUrl, actor);
  }

  public updateActor(actor: Actor): Observable<Actor> {
    return this.http.put<Actor>(`${this.apiUrl}/${actor.id}`, actor);
  }

  public deleteActor(id: string): Observable<Actor> {
    return this.http.delete<Actor>(`${this.apiUrl}/${id}`);
  }
}
