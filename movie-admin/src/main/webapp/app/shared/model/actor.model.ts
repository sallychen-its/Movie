import dayjs from 'dayjs';
import { IMovie } from 'app/shared/model/movie.model';

export interface IActor {
  id?: number;
  name?: string;
  job?: string | null;
  gender?: string | null;
  dateOfBirth?: string | null;
  homeTown?: string | null;
  story?: string | null;
  imageUrl?: string | null;
  createdDate?: string;
  movies?: IMovie[] | null;
}

export const defaultValue: Readonly<IActor> = {};
