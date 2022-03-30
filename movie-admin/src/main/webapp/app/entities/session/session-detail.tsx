import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './session.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SessionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const sessionEntity = useAppSelector(state => state.session.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sessionDetailsHeading">
          <Translate contentKey="movieAdminApp.session.detail.title">Session</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sessionEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="movieAdminApp.session.name">Name</Translate>
            </span>
          </dt>
          <dd>{sessionEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="movieAdminApp.session.description">Description</Translate>
            </span>
          </dt>
          <dd>{sessionEntity.description}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="movieAdminApp.session.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {sessionEntity.createdDate ? <TextFormat value={sessionEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/session" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/session/${sessionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SessionDetail;
