import request from '@/utils/request'

// 查询关键字字典列表
export function getSL(query) {
  return request({
    url: '/question/sy/getsl',
    method: 'get',
    params: query
  })
}

