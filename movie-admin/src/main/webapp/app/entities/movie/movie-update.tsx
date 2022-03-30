import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { IActor } from 'app/shared/model/actor.model';
import { getEntities as getActors } from 'app/entities/actor/actor.reducer';
import { ISession } from 'app/shared/model/session.model';
import { getEntities as getSessions } from 'app/entities/session/session.reducer';
import {
  getEntity,
  updateEntity,
  createEntity,
  reset,
  uploadPoster,
  uploadBackground,
  uploadMovie
} from './movie.reducer';
import { IMovie } from 'app/shared/model/movie.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import Dropzone from 'app/shared/components/dropzone/dropzone';
import UploadVideo from 'app/shared/components/dropzone/videodropzone';


export const MovieUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const [poster, setPoster] = useState<any[]>([]);
  const [background, setBackground] = useState<any[]>([]);
  const [movie, setMovie] = useState<any[]>([]);

  const domain = window.location.protocol + "//" + window.location.host;

  const categories = useAppSelector(state => state.category.entities);
  const actors = useAppSelector(state => state.actor.entities);
  const sessions = useAppSelector(state => state.session.entities);
  const movieEntity = useAppSelector(state => state.movie.entity);
  const loading = useAppSelector(state => state.movie.loading);
  const updating = useAppSelector(state => state.movie.updating);
  const updateSuccess = useAppSelector(state => state.movie.updateSuccess);

  const handleClose = () => {
    props.history.push('/movie' + props.location.search);
  };

  const onDropPoster = acceptedFiles => {
    setPoster(acceptedFiles.map(file => Object.assign(file, {
      preview: URL.createObjectURL(file)
    })));
    dispatch(uploadPoster(acceptedFiles))
}


const onDropBackground = acceptedFiles => {
    setBackground(acceptedFiles.map(file => Object.assign(file, {
      preview: URL.createObjectURL(file)
    })));
    dispatch(uploadBackground(acceptedFiles))
}

const onDropMovie = acceptedFiles => {
  setMovie(acceptedFiles.map(file => Object.assign(file, {
    preview: URL.createObjectURL(file)
  })));
  dispatch(uploadMovie(acceptedFiles))
}


  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCategories({}));
    dispatch(getActors({}));
    dispatch(getSessions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  useEffect(() => {
    if (!isNew) {

      if (movieEntity.posterUrl) {
        setPoster([{
          preview : domain + movieEntity.posterUrl,
          type : "image"
        }]);
      }

      if (movieEntity.backgroundUrl) {
        setBackground([{
          preview : domain + movieEntity.backgroundUrl,
          type : "image"
        }])
      }

      if (movieEntity.movieUrl) {
        setMovie([{
          preview : domain + movieEntity.movieUrl,
          type : "video"
        }])
      }
    }
  }, [movieEntity])


  const saveEntity = values => {
    const entity = {
      ...movieEntity,
      ...values,
      categories: mapIdList(values.categories),
      actors: mapIdList(values.actors),
      session: sessions.find(it => it.id.toString() === values.sessionId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...movieEntity,
          categories: movieEntity?.categories?.map(e => e.id.toString()),
          actors: movieEntity?.actors?.map(e => e.id.toString()),
          sessionId: movieEntity?.session?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="movieAdminApp.movie.home.createOrEditLabel" data-cy="MovieCreateUpdateHeading">
            <Translate contentKey="movieAdminApp.movie.home.createOrEditLabel">Create or edit a Movie</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="movie-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('movieAdminApp.movie.posterUrl')}
                id="movie-posterUrl"
                name="posterUrl"
                data-cy="posterUrl"
                type="text"
                readOnly
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Dropzone onDrop={onDropPoster} files={poster} key={1} />
              <ValidatedField
                label={translate('movieAdminApp.movie.backgroundUrl')}
                id="movie-backgroundUrl"
                name="backgroundUrl"
                data-cy="backgroundUrl"
                type="text"
                readOnly
                posterUrl
              />
              <Dropzone onDrop={onDropBackground} files={background} key={2} />
              <ValidatedField
                label={translate('movieAdminApp.movie.movieUrl')}
                id="movie-movieUrl"
                name="movieUrl"
                data-cy="movieUrl"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Dropzone onDrop={onDropMovie} files={movie} key={3} />
              <ValidatedField
                label={translate('movieAdminApp.movie.name')}
                id="movie-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                label={translate('movieAdminApp.movie.nameInVietNamese')}
                id="movie-nameInVietNamese"
                name="nameInVietNamese"
                data-cy="nameInVietNamese"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                label={translate('movieAdminApp.movie.description')}
                id="movie-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('movieAdminApp.movie.time')} id="movie-time" name="time" data-cy="time" type="text" />
              <ValidatedField label={translate('movieAdminApp.movie.iMDB')} id="movie-iMDB" name="iMDB" data-cy="iMDB" type="text" />
              <ValidatedField
                label={translate('movieAdminApp.movie.director')}
                id="movie-director"
                name="director"
                data-cy="director"
                type="text"
              />
              <ValidatedField
                label={translate('movieAdminApp.movie.script')}
                id="movie-script"
                name="script"
                data-cy="script"
                type="text"
              />
              <ValidatedField
                label={translate('movieAdminApp.movie.coutry')}
                id="movie-coutry"
                name="coutry"
                data-cy="coutry"
                type="text"
              />
              <ValidatedField
                label={translate('movieAdminApp.movie.premiere')}
                id="movie-premiere"
                name="premiere"
                data-cy="premiere"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('movieAdminApp.movie.isActive')}
                id="movie-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('movieAdminApp.movie.updatedDate')}
                id="movie-updatedDate"
                name="updatedDate"
                data-cy="updatedDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('movieAdminApp.movie.createdDate')}
                id="movie-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('movieAdminApp.movie.category')}
                id="movie-category"
                data-cy="category"
                type="select"
                multiple
                name="categories"
              >
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('movieAdminApp.movie.actor')}
                id="movie-actor"
                data-cy="actor"
                type="select"
                multiple
                name="actors"
              >
                <option value="" key="0" />
                {actors
                  ? actors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="movie-session"
                name="sessionId"
                data-cy="session"
                label={translate('movieAdminApp.movie.session')}
                type="select"
              >
                <option value="" key="0" />
                {sessions
                  ? sessions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/movie" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MovieUpdate;
