import request from '@/utils/request'

// 查询零组件生产订单列表
export function listLzjscdd(query) {
  return request({
    url: '/pc/lzjscdd/list',
    method: 'get',
    params: query
  })
}

// 查询零组件生产订单详细
export function getLzjscdd(id) {
  return request({
    url: '/pc/lzjscdd/' + id,
    method: 'get'
  })
}

// 新增零组件生产订单
export function addLzjscdd(data) {
  return request({
    url: '/pc/lzjscdd',
    method: 'post',
    data: data
  })
}

// 修改零组件生产订单
export function updateLzjscdd(data) {
  return request({
    url: '/pc/lzjscdd',
    method: 'put',
    data: data
  })
}

// 删除零组件生产订单
export function delLzjscdd(id) {
  return request({
    url: '/pc/lzjscdd/' + id,
    method: 'delete'
  })
}
