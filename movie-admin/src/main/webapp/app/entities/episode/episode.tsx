import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './episode.reducer';
import { IEpisode } from 'app/shared/model/episode.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Episode = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const episodeList = useAppSelector(state => state.episode.entities);
  const loading = useAppSelector(state => state.episode.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="episode-heading" data-cy="EpisodeHeading">
        <Translate contentKey="movieAdminApp.episode.home.title">Episodes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="movieAdminApp.episode.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="movieAdminApp.episode.home.createLabel">Create new Episode</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {episodeList && episodeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="movieAdminApp.episode.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.episode.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.episode.createdDate">Created Date</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.episode.movie">Movie</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {episodeList.map((episode, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${episode.id}`} color="link" size="sm">
                      {episode.id}
                    </Button>
                  </td>
                  <td>{episode.name}</td>
                  <td>
                    {episode.createdDate ? <TextFormat type="date" value={episode.createdDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{episode.movie ? <Link to={`movie/${episode.movie.id}`}>{episode.movie.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${episode.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${episode.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${episode.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="movieAdminApp.episode.home.notFound">No Episodes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Episode;
