import dayjs from 'dayjs';
import { ITrailer } from 'app/shared/model/trailer.model';
import { IEpisode } from 'app/shared/model/episode.model';
import { ICategory } from 'app/shared/model/category.model';
import { IActor } from 'app/shared/model/actor.model';
import { ISession } from 'app/shared/model/session.model';

export interface IMovie {
  id?: number;
  name?: string;
  nameInVietNamese?: string;
  description?: string | null;
  time?: number | null;
  iMDB?: number | null;
  director?: string | null;
  script?: string | null;
  coutry?: string | null;
  premiere?: string;
  posterUrl?: string;
  backgroundUrl?: string | null;
  movieUrl?: string;
  isActive?: boolean;
  updatedDate?: string;
  createdDate?: string;
  trailers?: ITrailer[] | null;
  episodes?: IEpisode[] | null;
  categories?: ICategory[] | null;
  actors?: IActor[] | null;
  session?: ISession | null;
}

export const defaultValue: Readonly<IMovie> = {
  isActive: false,
};
