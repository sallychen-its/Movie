import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IMovie } from 'app/shared/model/movie.model';
import { getEntities as getMovies } from 'app/entities/movie/movie.reducer';
import { getEntity, updateEntity, createEntity, reset } from './actor.reducer';
import { IActor } from 'app/shared/model/actor.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ActorUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const movies = useAppSelector(state => state.movie.entities);
  const actorEntity = useAppSelector(state => state.actor.entity);
  const loading = useAppSelector(state => state.actor.loading);
  const updating = useAppSelector(state => state.actor.updating);
  const updateSuccess = useAppSelector(state => state.actor.updateSuccess);

  const handleClose = () => {
    props.history.push('/actor');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getMovies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...actorEntity,
      ...values,
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
          ...actorEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="movieAdminApp.actor.home.createOrEditLabel" data-cy="ActorCreateUpdateHeading">
            <Translate contentKey="movieAdminApp.actor.home.createOrEditLabel">Create or edit a Actor</Translate>
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
                  id="actor-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('movieAdminApp.actor.name')}
                id="actor-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                label={translate('movieAdminApp.actor.job')}
                id="actor-job"
                name="job"
                data-cy="job"
                type="text"
                validate={{
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('movieAdminApp.actor.gender')}
                id="actor-gender"
                name="gender"
                data-cy="gender"
                type="text"
                validate={{
                  maxLength: { value: 10, message: translate('entity.validation.maxlength', { max: 10 }) },
                }}
              />
              <ValidatedField
                label={translate('movieAdminApp.actor.dateOfBirth')}
                id="actor-dateOfBirth"
                name="dateOfBirth"
                data-cy="dateOfBirth"
                type="date"
              />
              <ValidatedField
                label={translate('movieAdminApp.actor.homeTown')}
                id="actor-homeTown"
                name="homeTown"
                data-cy="homeTown"
                type="text"
              />
              <ValidatedField label={translate('movieAdminApp.actor.story')} id="actor-story" name="story" data-cy="story" type="text" />
              <ValidatedField
                label={translate('movieAdminApp.actor.imageUrl')}
                id="actor-imageUrl"
                name="imageUrl"
                data-cy="imageUrl"
                type="text"
              />
              <ValidatedField
                label={translate('movieAdminApp.actor.createdDate')}
                id="actor-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/actor" replace color="info">
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

export default ActorUpdate;
