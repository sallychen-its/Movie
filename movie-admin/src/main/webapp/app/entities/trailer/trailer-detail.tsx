import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './trailer.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TrailerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const trailerEntity = useAppSelector(state => state.trailer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trailerDetailsHeading">
          <Translate contentKey="movieAdminApp.trailer.detail.title">Trailer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{trailerEntity.id}</dd>
          <dt>
            <span id="url">
              <Translate contentKey="movieAdminApp.trailer.url">Url</Translate>
            </span>
          </dt>
          <dd>{trailerEntity.url}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="movieAdminApp.trailer.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {trailerEntity.createdDate ? <TextFormat value={trailerEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="movieAdminApp.trailer.movie">Movie</Translate>
          </dt>
          <dd>{trailerEntity.movie ? trailerEntity.movie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/trailer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/trailer/${trailerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrailerDetail;
