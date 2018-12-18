import {CatalogValue} from './catalog-value';

export class Observation {
  id: number;
  description: string;
  statusId: number;
  catalogValueId: number;
  userId: number;
  catalogValueVO: CatalogValue;
}
