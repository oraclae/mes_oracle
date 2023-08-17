import request from '@/utils/request'

// 查询提出问题列表
export function selectDataByNotId(data) {
  return request({
    url: '/zhineng/selectDataByNotId',
    method: 'post',
    data: data
  })
}
