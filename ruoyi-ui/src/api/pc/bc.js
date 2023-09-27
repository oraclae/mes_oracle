import request from '@/utils/request'

// 查询班次列表
export function listBc(query) {
  return request({
    url: '/pc/bc/list',
    method: 'get',
    params: query
  })
}

// 查询班次详细
export function getBc(id) {
  return request({
    url: '/pc/bc/' + id,
    method: 'get'
  })
}

// 新增班次
export function addBc(data) {
  return request({
    url: '/pc/bc',
    method: 'post',
    data: data
  })
}

// 修改班次
export function updateBc(data) {
  return request({
    url: '/pc/bc',
    method: 'put',
    data: data
  })
}

// 删除班次
export function delBc(id) {
  return request({
    url: '/pc/bc/' + id,
    method: 'delete'
  })
}
