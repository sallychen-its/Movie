import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './movie.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const MovieDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const movieEntity = useAppSelector(state => state.movie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="movieDetailsHeading">
          <Translate contentKey="movieAdminApp.movie.detail.title">Movie</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{movieEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="movieAdminApp.movie.name">Name</Translate>
            </span>
          </dt>
          <dd>{movieEntity.name}</dd>
          <dt>
            <span id="nameInVietNamese">
              <Translate contentKey="movieAdminApp.movie.nameInVietNamese">Name In Viet Namese</Translate>
            </span>
          </dt>
          <dd>{movieEntity.nameInVietNamese}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="movieAdminApp.movie.description">Description</Translate>
            </span>
          </dt>
          <dd>{movieEntity.description}</dd>
          <dt>
            <span id="time">
              <Translate contentKey="movieAdminApp.movie.time">Time</Translate>
            </span>
          </dt>
          <dd>{movieEntity.time}</dd>
          <dt>
            <span id="iMDB">
              <Translate contentKey="movieAdminApp.movie.iMDB">I MDB</Translate>
            </span>
          </dt>
          <dd>{movieEntity.iMDB}</dd>
          <dt>
            <span id="director">
              <Translate contentKey="movieAdminApp.movie.director">Director</Translate>
            </span>
          </dt>
          <dd>{movieEntity.director}</dd>
          <dt>
            <span id="script">
              <Translate contentKey="movieAdminApp.movie.script">Script</Translate>
            </span>
          </dt>
          <dd>{movieEntity.script}</dd>
          <dt>
            <span id="coutry">
              <Translate contentKey="movieAdminApp.movie.coutry">Coutry</Translate>
            </span>
          </dt>
          <dd>{movieEntity.coutry}</dd>
          <dt>
            <span id="premiere">
              <Translate contentKey="movieAdminApp.movie.premiere">Premiere</Translate>
            </span>
          </dt>
          <dd>{movieEntity.premiere ? <TextFormat value={movieEntity.premiere} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="posterUrl">
              <Translate contentKey="movieAdminApp.movie.posterUrl">Poster Url</Translate>
            </span>
          </dt>
          <dd>{movieEntity.posterUrl}</dd>
          <dt>
            <span id="backgroundUrl">
              <Translate contentKey="movieAdminApp.movie.backgroundUrl">Background Url</Translate>
            </span>
          </dt>
          <dd>{movieEntity.backgroundUrl}</dd>
          <dt>
            <span id="movieUrl">
              <Translate contentKey="movieAdminApp.movie.movieUrl">Movie Url</Translate>
            </span>
          </dt>
          <dd>{movieEntity.movieUrl}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="movieAdminApp.movie.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{movieEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="movieAdminApp.movie.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {movieEntity.updatedDate ? <TextFormat value={movieEntity.updatedDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="movieAdminApp.movie.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {movieEntity.createdDate ? <TextFormat value={movieEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="movieAdminApp.movie.category">Category</Translate>
          </dt>
          <dd>
            {movieEntity.categories
              ? movieEntity.categories.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {movieEntity.categories && i === movieEntity.categories.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="movieAdminApp.movie.actor">Actor</Translate>
          </dt>
          <dd>
            {movieEntity.actors
              ? movieEntity.actors.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {movieEntity.actors && i === movieEntity.actors.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="movieAdminApp.movie.session">Session</Translate>
          </dt>
          <dd>{movieEntity.session ? movieEntity.session.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/movie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/movie/${movieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MovieDetail;
