import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './movie.reducer';
import { IMovie } from 'app/shared/model/movie.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Movie = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const movieList = useAppSelector(state => state.movie.entities);
  const loading = useAppSelector(state => state.movie.loading);
  const totalItems = useAppSelector(state => state.movie.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="movie-heading" data-cy="MovieHeading">
        <Translate contentKey="movieAdminApp.movie.home.title">Movies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="movieAdminApp.movie.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="movieAdminApp.movie.home.createLabel">Create new Movie</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {movieList && movieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="movieAdminApp.movie.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="movieAdminApp.movie.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nameInVietNamese')}>
                  <Translate contentKey="movieAdminApp.movie.nameInVietNamese">Name In Viet Namese</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('time')}>
                  <Translate contentKey="movieAdminApp.movie.time">Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('iMDB')}>
                  <Translate contentKey="movieAdminApp.movie.iMDB">I MDB</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('director')}>
                  <Translate contentKey="movieAdminApp.movie.director">Director</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('script')}>
                  <Translate contentKey="movieAdminApp.movie.script">Script</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('coutry')}>
                  <Translate contentKey="movieAdminApp.movie.coutry">Coutry</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('premiere')}>
                  <Translate contentKey="movieAdminApp.movie.premiere">Premiere</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('posterUrl')}>
                  <Translate contentKey="movieAdminApp.movie.posterUrl">Poster Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('backgroundUrl')}>
                  <Translate contentKey="movieAdminApp.movie.backgroundUrl">Background Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('movieUrl')}>
                  <Translate contentKey="movieAdminApp.movie.movieUrl">Movie Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isActive')}>
                  <Translate contentKey="movieAdminApp.movie.isActive">Is Active</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedDate')}>
                  <Translate contentKey="movieAdminApp.movie.updatedDate">Updated Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="movieAdminApp.movie.createdDate">Created Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="movieAdminApp.movie.session">Session</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {movieList.map((movie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${movie.id}`} color="link" size="sm">
                      {movie.id}
                    </Button>
                  </td>
                  <td>{movie.name}</td>
                  <td>{movie.time}</td>
                  <td>{movie.iMDB}</td>
                  <td>{movie.director}</td>
                  <td>{movie.script}</td>
                  <td>{movie.coutry}</td>
                  <td>{movie.premiere ? <TextFormat type="date" value={movie.premiere} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{movie.posterUrl}</td>
                  <td>{movie.backgroundUrl}</td>
                  <td>{movie.movieUrl}</td>
                  <td>{movie.isActive ? 'true' : 'false'}</td>
                  <td>{movie.updatedDate ? <TextFormat type="date" value={movie.updatedDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{movie.createdDate ? <TextFormat type="date" value={movie.createdDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{movie.session ? <Link to={`session/${movie.session.id}`}>{movie.session.name}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${movie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${movie.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${movie.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="movieAdminApp.movie.home.notFound">No Movies found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={movieList && movieList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Movie;
