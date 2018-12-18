import {Status} from './status';

export class Execution {
  id: number;
  alias: string;
  finishDate: Date;
  processId: number;
  statuses: Status[];
  createAt: Date;
}
