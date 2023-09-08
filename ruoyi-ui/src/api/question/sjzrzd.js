import request from '@/utils/request'

// 查询升级责任人列表
export function listSjzrzd(query) {
  return request({
    url: '/system/sjzrzd/list',
    method: 'get',
    params: query
  })
}

// 查询升级责任人详细
export function getSjzrzd(xh) {
  return request({
    url: '/system/sjzrzd/' + xh,
    method: 'get'
  })
}

// 新增升级责任人
export function addSjzrzd(data) {
  return request({
    url: '/system/sjzrzd',
    method: 'post',
    data: data
  })
}

// 修改升级责任人
export function updateSjzrzd(data) {
  return request({
    url: '/system/sjzrzd',
    method: 'put',
    data: data
  })
}

// 删除升级责任人
export function delSjzrzd(xh) {
  return request({
    url: '/system/sjzrzd/' + xh,
    method: 'delete'
  })
}

//通过升级人id查询升级人
export function getSjzrzdByZrrid(zrrid) {
  return request({
    url: '/system/sjzrzd/getSjzrzdByZrrid/' + zrrid,
    method: 'get'
  })
}

//问题升级责任人
export function wtsjOneZrr(data) {
  return request({
    url: '/system/sjzrzd/wtsjOneZrr',
    method: 'post',
    data: data
  })
}

