import request from '@/utils/request'

// 查询排产设备特例列表
export function listSbtl(query) {
  return request({
    url: '/pc/sbtl/list',
    method: 'get',
    params: query
  })
}

// 查询排产设备特例详细
export function getSbtl(id) {
  return request({
    url: '/pc/sbtl/' + id,
    method: 'get'
  })
}

// 新增排产设备特例
export function addSbtl(data) {
  return request({
    url: '/pc/sbtl',
    method: 'post',
    data: data
  })
}

// 修改排产设备特例
export function updateSbtl(data) {
  return request({
    url: '/pc/sbtl',
    method: 'put',
    data: data
  })
}

// 删除排产设备特例
export function delSbtl(id) {
  return request({
    url: '/pc/sbtl/' + id,
    method: 'delete'
  })
}
