import dayjs from 'dayjs';
import { IMovie } from 'app/shared/model/movie.model';

export interface ISession {
  id?: number;
  name?: string;
  description?: string;
  createdDate?: string;
  movies?: IMovie[] | null;
}

export const defaultValue: Readonly<ISession> = {};
