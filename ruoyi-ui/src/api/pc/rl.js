import request from '@/utils/request'

// 查询日期列表
export function listRl(query) {
  return request({
    url: '/pc/rl/list',
    method: 'get',
    params: query
  })
}

// 查询日期详细
export function getRl(id) {
  return request({
    url: '/pc/rl/' + id,
    method: 'get'
  })
}

// 新增日期
export function addRl(data) {
  return request({
    url: '/pc/rl',
    method: 'post',
    data: data
  })
}

// 修改日期
export function updateRl(data) {
  return request({
    url: '/pc/rl',
    method: 'put',
    data: data
  })
}

// 删除日期
export function delRl(id) {
  return request({
    url: '/pc/rl/' + id,
    method: 'delete'
  })
}

//生成日期
export function addGenerationDate(query) {
  return request({
    url: '/pc/rl/addGenerationDate',
    method: 'get',
    params: query
  })
}
