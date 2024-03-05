import { FormControl } from "@angular/forms";

export class CustomValidators {
  public static noSpaceAllowed = (control: FormControl): {[key: string]: boolean} | null => {
    if (control.value != null && control.value.indexOf(' ') != -1) {
      return { noSpaceAllowed: true };
    }
  
    return null;
  };
}