import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './trailer.reducer';
import { ITrailer } from 'app/shared/model/trailer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Trailer = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const trailerList = useAppSelector(state => state.trailer.entities);
  const loading = useAppSelector(state => state.trailer.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="trailer-heading" data-cy="TrailerHeading">
        <Translate contentKey="movieAdminApp.trailer.home.title">Trailers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="movieAdminApp.trailer.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="movieAdminApp.trailer.home.createLabel">Create new Trailer</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {trailerList && trailerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="movieAdminApp.trailer.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.trailer.url">Url</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.trailer.createdDate">Created Date</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.trailer.movie">Movie</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {trailerList.map((trailer, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${trailer.id}`} color="link" size="sm">
                      {trailer.id}
                    </Button>
                  </td>
                  <td>{trailer.url}</td>
                  <td>
                    {trailer.createdDate ? <TextFormat type="date" value={trailer.createdDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{trailer.movie ? <Link to={`movie/${trailer.movie.id}`}>{trailer.movie.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${trailer.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${trailer.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${trailer.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="movieAdminApp.trailer.home.notFound">No Trailers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Trailer;
