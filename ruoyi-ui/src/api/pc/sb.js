import request from '@/utils/request'

// 查询设备列表
export function listSb(query) {
  return request({
    url: '/pc/sb/list',
    method: 'get',
    params: query
  })
}

// 查询设备详细
export function getSb(id) {
  return request({
    url: '/pc/sb/' + id,
    method: 'get'
  })
}

// 新增设备
export function addSb(data) {
  return request({
    url: '/pc/sb',
    method: 'post',
    data: data
  })
}

// 修改设备
export function updateSb(data) {
  return request({
    url: '/pc/sb',
    method: 'put',
    data: data
  })
}

// 删除设备
export function delSb(id) {
  return request({
    url: '/pc/sb/' + id,
    method: 'delete'
  })
}
