export interface MovieDetails {
  readonly id: string;
  readonly description: string;
  readonly duration: number;
  readonly releaseDate: Date;
  readonly genre: string;
  readonly country: string;
  readonly rating: number;
}