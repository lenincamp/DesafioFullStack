import {Workflow} from './workflow';
import {Execution} from './execution';
import {CatalogValue} from './catalog-value';

export class Process {
  id: string;
  name: string;
  description: string;
  typeProcess: string;
  enabled: boolean = true;
  workFlows: Workflow[];
  executions: Execution[];
  catalogValueVO: CatalogValue;
}
