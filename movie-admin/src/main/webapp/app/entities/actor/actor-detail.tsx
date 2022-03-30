import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './actor.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ActorDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const actorEntity = useAppSelector(state => state.actor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="actorDetailsHeading">
          <Translate contentKey="movieAdminApp.actor.detail.title">Actor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{actorEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="movieAdminApp.actor.name">Name</Translate>
            </span>
          </dt>
          <dd>{actorEntity.name}</dd>
          <dt>
            <span id="job">
              <Translate contentKey="movieAdminApp.actor.job">Job</Translate>
            </span>
          </dt>
          <dd>{actorEntity.job}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="movieAdminApp.actor.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{actorEntity.gender}</dd>
          <dt>
            <span id="dateOfBirth">
              <Translate contentKey="movieAdminApp.actor.dateOfBirth">Date Of Birth</Translate>
            </span>
          </dt>
          <dd>
            {actorEntity.dateOfBirth ? <TextFormat value={actorEntity.dateOfBirth} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="homeTown">
              <Translate contentKey="movieAdminApp.actor.homeTown">Home Town</Translate>
            </span>
          </dt>
          <dd>{actorEntity.homeTown}</dd>
          <dt>
            <span id="story">
              <Translate contentKey="movieAdminApp.actor.story">Story</Translate>
            </span>
          </dt>
          <dd>{actorEntity.story}</dd>
          <dt>
            <span id="imageUrl">
              <Translate contentKey="movieAdminApp.actor.imageUrl">Image Url</Translate>
            </span>
          </dt>
          <dd>{actorEntity.imageUrl}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="movieAdminApp.actor.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {actorEntity.createdDate ? <TextFormat value={actorEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/actor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/actor/${actorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActorDetail;
