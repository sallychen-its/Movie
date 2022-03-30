import dayjs from 'dayjs';
import { IMovie } from 'app/shared/model/movie.model';

export interface ITrailer {
  id?: number;
  url?: string;
  createdDate?: string;
  movie?: IMovie | null;
}

export const defaultValue: Readonly<ITrailer> = {};
