export interface Movie {
  readonly id: string;
  readonly title: string;
  readonly watched: boolean;
  readonly movieDetails: string;
  readonly director: string;
  readonly actors: string[];
}