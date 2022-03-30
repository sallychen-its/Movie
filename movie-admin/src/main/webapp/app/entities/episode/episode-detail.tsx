import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './episode.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EpisodeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const episodeEntity = useAppSelector(state => state.episode.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="episodeDetailsHeading">
          <Translate contentKey="movieAdminApp.episode.detail.title">Episode</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{episodeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="movieAdminApp.episode.name">Name</Translate>
            </span>
          </dt>
          <dd>{episodeEntity.name}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="movieAdminApp.episode.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {episodeEntity.createdDate ? <TextFormat value={episodeEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="movieAdminApp.episode.movie">Movie</Translate>
          </dt>
          <dd>{episodeEntity.movie ? episodeEntity.movie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/episode" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/episode/${episodeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EpisodeDetail;
