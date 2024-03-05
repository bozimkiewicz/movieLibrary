export interface MovieDetailsInMovie {
  readonly id: string;
  readonly title: string;
  readonly watched: boolean;
  readonly duration: number;
  readonly releaseDate: Date;
  readonly genre: string;
  readonly country: string;
  readonly rating: number;
}