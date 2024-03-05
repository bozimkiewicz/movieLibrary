import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'convertDuration'
})
export class ConvertDurationPipe implements PipeTransform {
  public transform(value: string): string {
    const minutes: number = parseInt(value, 10);
    const hours: number = Math.floor(minutes / 60);
    const remainingMinutes: number = minutes % 60;

    return `${hours}h ${remainingMinutes}m`;
  }
}
