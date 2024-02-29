// filter.pipe.ts
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  /**
   * Filtro que ignora mayúsculas y minúsculas
   * @param items 
   * @param field 
   * @param value 
   * @returns 
   */
  transform(items: any[], field: string, value: string): any[] {
    if (!items) return [];
    if (!field || !value) return items;

    return items.filter(singleItem =>
        singleItem[field].toLowerCase().includes(value.toLowerCase())
    );
  }

}
