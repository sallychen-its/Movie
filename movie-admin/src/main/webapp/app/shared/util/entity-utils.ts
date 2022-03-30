import pick from 'lodash/pick';
import { IPaginationBaseState } from 'react-jhipster';


export const cleanEntity = entity => {
  const keysToKeep = Object.keys(entity).filter(k => !(entity[k] instanceof Object) || (entity[k]['id'] !== '' && entity[k]['id'] !== -1));

  return pick(entity, keysToKeep);
};


export const mapIdList = (idList: ReadonlyArray<any>) => idList.filter((id: any) => id !== '').map((id: any) => ({ id }));

export const overridePaginationStateWithQueryParams = (paginationBaseState: IPaginationBaseState, locationSearch: string) => {
  const params = new URLSearchParams(locationSearch);
  const page = params.get('page');
  const sort = params.get('sort');
  if (page && sort) {
    const sortSplit = sort.split(',');
    paginationBaseState.activePage = +page;
    paginationBaseState.sort = sortSplit[0];
    paginationBaseState.order = sortSplit[1];
  }
  return paginationBaseState;
};
