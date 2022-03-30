import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IMovie, defaultValue } from 'app/shared/model/movie.model';

const initialState: EntityState<IMovie> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/movies';
const uploadApi = 'api/media/upload';

// Actions

export const getEntities = createAsyncThunk('movie/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
  return axios.get<IMovie[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'movie/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IMovie>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'movie/create_entity',
  async (entity: IMovie, thunkAPI) => {
    const result = await axios.post<IMovie>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'movie/update_entity',
  async (entity: IMovie, thunkAPI) => {
    const result = await axios.put<IMovie>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'movie/partial_update_entity',
  async (entity: IMovie, thunkAPI) => {
    const result = await axios.patch<IMovie>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'movie/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IMovie>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const uploadPoster = createAsyncThunk(
  'movie/upload-poster-image',
  async (files: File[] | null, thunkAPI) => {
    const formData = new FormData();
    formData.append("file", files[0])

    const result = await axios.post(`${uploadApi}/image`, formData);
    return result;
  },
  { serializeError: serializeAxiosError }
)


export const uploadBackground = createAsyncThunk(
  'movie/upload-background-image',
  async (files: File[] | null, thunkAPI) => {
    const formData = new FormData();
    formData.append("file", files[0])

    const result = await axios.post(`${uploadApi}/image`, formData);
    return result;
  },
  { serializeError: serializeAxiosError }
)

export const uploadMovie = createAsyncThunk(
  'movie/upload-movie',
  async (files: File[] | null, thunkAPI) => {
    const formData = new FormData();
    formData.append("file", files[0])

    const result = await axios.post(`${uploadApi}/video`, formData);
    return result;
  },
  { serializeError: serializeAxiosError }
)



// slice
export const MovieSlice = createEntitySlice({
  name: 'movie',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        return {
          ...state,
          loading: false,
          entities: action.payload.data,
          totalItems: parseInt(action.payload.headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      })
      .addMatcher(isPending(uploadPoster), (state, action) => {
        state.errorMessage = null;
        state.updateSuccess = false;
      })
      .addMatcher(isFulfilled(uploadPoster), (state, action) => {
        state.entity.posterUrl = action.payload.data;
      })
      .addMatcher(isPending(uploadBackground), (state, action) => {
        state.errorMessage = null;
        state.updateSuccess = false;
      })
      .addMatcher(isFulfilled(uploadBackground), (state, action) => {
        state.entity.backgroundUrl = action.payload.data;
      })
      .addMatcher(isPending(uploadMovie), (state, action) => {
        state.errorMessage = null;
        state.loading = true;
        state.updateSuccess = false;
      })
      .addMatcher(isFulfilled(uploadMovie), (state, action) => {
        state.entity.movieUrl = action.payload.data;
        state.loading = false;
      })
  },
});

export const { reset } = MovieSlice.actions;

// Reducer
export default MovieSlice.reducer;
