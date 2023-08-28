import request from '@/utils/request'


// 获取登陆人信息
export function getUserInfo() {
  return request({
    url: '/question/wtlbry/getUserInfo',
    method: 'get'
  })
}

// 查询问题类别人员对照列表
export function listWtlbry(query) {
  return request({
    url: '/question/wtlbry/list',
    method: 'get',
    params: query
  })
}

// 查询问题类别人员对照详细
export function getWtlbry(xh) {
  return request({
    url: '/question/wtlbry/' + xh,
    method: 'get'
  })
}

// 新增问题类别人员对照
export function addWtlbry(data) {
  return request({
    url: '/question/wtlbry',
    method: 'post',
    data: data
  })
}

// 修改问题类别人员对照
export function updateWtlbry(data) {
  return request({
    url: '/question/wtlbry',
    method: 'put',
    data: data
  })
}

// 删除问题类别人员对照
export function delWtlbry(xh) {
  return request({
    url: '/question/wtlbry/' + xh,
    method: 'delete'
  })
}
