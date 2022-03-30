import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './actor.reducer';
import { IActor } from 'app/shared/model/actor.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Actor = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const actorList = useAppSelector(state => state.actor.entities);
  const loading = useAppSelector(state => state.actor.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="actor-heading" data-cy="ActorHeading">
        <Translate contentKey="movieAdminApp.actor.home.title">Actors</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="movieAdminApp.actor.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="movieAdminApp.actor.home.createLabel">Create new Actor</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {actorList && actorList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="movieAdminApp.actor.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.actor.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.actor.job">Job</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.actor.gender">Gender</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.actor.dateOfBirth">Date Of Birth</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.actor.homeTown">Home Town</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.actor.story">Story</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.actor.imageUrl">Image Url</Translate>
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.actor.createdDate">Created Date</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {actorList.map((actor, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${actor.id}`} color="link" size="sm">
                      {actor.id}
                    </Button>
                  </td>
                  <td>{actor.name}</td>
                  <td>{actor.job}</td>
                  <td>{actor.gender}</td>
                  <td>{actor.dateOfBirth ? <TextFormat type="date" value={actor.dateOfBirth} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{actor.homeTown}</td>
                  <td>{actor.story}</td>
                  <td>{actor.imageUrl}</td>
                  <td>{actor.createdDate ? <TextFormat type="date" value={actor.createdDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${actor.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${actor.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${actor.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="movieAdminApp.actor.home.notFound">No Actors found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Actor;
