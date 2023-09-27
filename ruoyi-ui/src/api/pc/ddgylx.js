import request from '@/utils/request'

// 查询订单工艺路线列表
export function listDdgylx(query) {
  return request({
    url: '/pc/ddgylx/list',
    method: 'get',
    params: query
  })
}

// 查询订单工艺路线详细
export function getDdgylx(id) {
  return request({
    url: '/pc/ddgylx/' + id,
    method: 'get'
  })
}

// 新增订单工艺路线
export function addDdgylx(data) {
  return request({
    url: '/pc/ddgylx',
    method: 'post',
    data: data
  })
}

// 修改订单工艺路线
export function updateDdgylx(data) {
  return request({
    url: '/pc/ddgylx',
    method: 'put',
    data: data
  })
}

// 删除订单工艺路线
export function delDdgylx(id) {
  return request({
    url: '/pc/ddgylx/' + id,
    method: 'delete'
  })
}
