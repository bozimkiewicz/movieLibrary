export interface MovieDetailsForm {
  readonly description: string;
  readonly duration: number;
  readonly releaseDate: Date | string;
  readonly genre: string;
  readonly country: string;
  readonly rating: number;
}