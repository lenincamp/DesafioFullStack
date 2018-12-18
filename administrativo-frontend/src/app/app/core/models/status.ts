import {CatalogValue} from './catalog-value';
import {User} from './user';
import {Conclusion} from './conclusion';
import {Observation} from './observation';
import {Objective} from './objective';

export class Status {
  id: number;
  finishDate: Date;
  catalogValueId: number;
  catalogValue: CatalogValue;
  users: User[];
  conclusions: Conclusion[];
  observations: Observation[];
  objectives: Objective[];
  executionId: number;
}
