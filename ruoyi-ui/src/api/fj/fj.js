import request from '@/utils/request'

export function selectFjListByIds(ids) {
  return request({
    url: '/system/fj/selectFjListByIds',
    method: 'post',
    data: ids
  })
}


// 查询附件列表
export function listFj(query) {
  return request({
    url: '/system/fj/list',
    method: 'get',
    params: query
  })
}

// 查询附件详细
export function getFj(xh) {
  return request({
    url: '/system/fj/' + xh,
    method: 'get'
  })
}

// 新增附件
export function addFj(data) {
  return request({
    url: '/system/fj',
    method: 'post',
    data: data
  })
}

// 修改附件
export function updateFj(data) {
  return request({
    url: '/system/fj',
    method: 'put',
    data: data
  })
}

// 删除附件
export function delFj(xh) {
  return request({
    url: '/system/fj/' + xh,
    method: 'delete'
  })
}

//根据id查询所有的附件
// 查询附件列表
export function listById(query) {
  return request({
    url: '/system/fj/listById',
    method: 'get',
    params: query
  })
}

//根据id查询所有的附件
// 查询附件列表
export function getFjByIds(data) {
  return request({
    url: '/system/fj/listByIds',
    method: 'post',
    data: data
  })
}
