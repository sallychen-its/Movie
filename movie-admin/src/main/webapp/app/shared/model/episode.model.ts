import dayjs from 'dayjs';
import { IMovie } from 'app/shared/model/movie.model';

export interface IEpisode {
  id?: number;
  name?: string;
  createdDate?: string;
  movie?: IMovie | null;
}

export const defaultValue: Readonly<IEpisode> = {};
