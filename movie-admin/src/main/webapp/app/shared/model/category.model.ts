import dayjs from 'dayjs';
import { IMovie } from 'app/shared/model/movie.model';

export interface ICategory {
  id?: number;
  name?: string;
  description?: string | null;
  isActive?: boolean;
  createdDate?: string;
  movies?: IMovie[] | null;
}

export const defaultValue: Readonly<ICategory> = {
  isActive: false,
};
