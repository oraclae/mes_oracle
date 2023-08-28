import request from '@/utils/request'

// 查询关键字字典列表
export function listGjzzd(query) {
  return request({
    url: '/system/gjzzd/list',
    method: 'get',
    params: query
  })
}

// 查询关键字字典详细
export function getGjzzd(xh) {
  return request({
    url: '/system/gjzzd/' + xh,
    method: 'get'
  })
}

// 新增关键字字典
export function addGjzzd(data) {
  return request({
    url: '/system/gjzzd',
    method: 'post',
    data: data
  })
}

// 修改关键字字典
export function updateGjzzd(data) {
  return request({
    url: '/system/gjzzd',
    method: 'put',
    data: data
  })
}

// 删除关键字字典
export function delGjzzd(xh) {
  return request({
    url: '/system/gjzzd/' + xh,
    method: 'delete'
  })
}
