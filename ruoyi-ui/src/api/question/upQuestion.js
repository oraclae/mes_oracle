import request from '@/utils/request'

// 查询提出问题列表
export function listCjls(query) {
  return request({
    url: '/myjob/cjls/list',
    method: 'get',
    params: query
  })
}

// 查询提出问题列表
export function downlistCjls(query) {
  return request({
    url: '/myjob/cjls/downlistCjls',
    method: 'get',
    params: query
  })
}

// 查询创建流水详细
export function getCjls(LSID) {
  return request({
    url: '/myjob/cjls/' + LSID,
    method: 'get'
  })
}

// 查询提出问题列表
export function speechListCjls() {
  return request({
    url: '/myjob/cjls/speechList',
    method: 'get',
  })
}

// 查询提出问题列表
export function ywcjList(query) {
  return request({
    url: '/myjob/cjls/ywcjlist',
    method: 'get',
    params: query
  })
}

// 新增提出问题
export function addCjls(data) {
  return request({
    url: '/myjob/cjls/add',
    method: 'post',
    data: data
  })
}

// 修改提出问题
export function updateCjls(data) {
  return request({
    url: '/myjob/cjls',
    method: 'put',
    data: data
  })
}

// 删除提出问题
export function delCjls(LSID) {
  return request({
    url: '/myjob/cjls/' + LSID,
    method: 'delete'
  })
}

// 改变问题状态
export function changewtzt(data, str) {
  return request({
    url: '/myjob/cjls/' + str + '/changewtzt',
    method: 'post',
    data: data
  })
}

// 问题接收
export function wtjsIt(selectedRows, whether) {
  return request({
    url: '/myjob/cjls/' + whether + '/wtjs',
    method: 'post',
    data: selectedRows
  })
}

// 修改解决方案
export function updatejjfa(id, str) {
  return request({
    url: '/myjob/cjls/' + id + '/updatejjfa',
    method: 'post',
    data: str
  })
}

// 获取完成、未完成数量
export function getwcsl() {
  return request({
    url: '/myjob/cjls/getwcsl',
    method: 'get',
  })
}

// 获取部门科室数量
export function getcjksbmNumber() {
  return request({
    url: '/myjob/cjls/getcjksbmNumber',
    method: 'get',
  })
}

// 获取接收人列表
export function getjsrBywtlb(query) {
  return request({
    url: '/myjob/cjls/getjsrBywtlb',
    method: 'get',
    params: query
  })
}


















// 查询答案信息列表
export function listDacc(query) {
  return request({
    url: '/myjob/cjls/listDacc',
    method: 'get',
    params: query
  })
}


// 查询答案信息列表
export function listDaccToZN(query) {
  return request({
    url: '/myjob/cjls/listDaccToZN',
    method: 'get',
    params: query
  })
}

// 查询答案信息详细
export function getDacc(DaxxID) {
  return request({
    url: '/myjob/cjls/getDacc/' + DaxxID,
    method: 'get'
  })
}

// 新增答案信息
export function addDacc(data) {
  return request({
    url: '/myjob/cjls/addDacc',
    method: 'post',
    data: data
  })
}

// 修改答案信息
export function updateDacc(data) {
  return request({
    url: '/myjob/cjls/updateDacc',
    method: 'put',
    data: data
  })
}


//查询的答案热度加1
export function updateDaccToRd(data) {
  return request({
    url: '/myjob/cjls/updateDaccToRd',
    method: 'put',
    data: data
  })
}

// 删除答案信息
export function delDacc(daccIds) {
  return request({
    url: '/myjob/cjls/delDacc/' + daccIds,
    method: 'delete'
  })
}




// 查询业务字典列表
export function listYwzd(query) {
  return request({
    url: '/myjob/cjls/listYwzd',
    method: 'get',
    params: query
  })
}

// 查询业务字典详细
export function getYwzd(XH) {
  return request({
    url: '/myjob/cjls/getYwzd/' + XH,
    method: 'get'
  })
}

// 新增业务字典
export function addYwzd(data) {
  return request({
    url: '/myjob/cjls/addYwzd',
    method: 'post',
    data: data
  })
}

// 修改业务字典
export function updateYwzd(data) {
  return request({
    url: '/myjob/cjls/updateYwzd',
    method: 'put',
    data: data
  })
}

// 删除业务字典
export function delYwzd(xhs) {
  return request({
    url: '/myjob/cjls/delYwzd/' + xhs,
    method: 'delete'
  })
}

// 查询提出问题按钮列表
export function getUpButtons() {
  return request({
    url: '/myjob/cjls/getUpButtons',
    method: 'get'
  })
}

// 查询提出问题按钮详情
export function getUpButton(xh) {
  return request({
    url: '/myjob/cjls/getUpButton/' + xh,
    method: 'get'
  })
}

// 修改提出问题按钮
export function updateUpButton(data) {
  return request({
    url: '/myjob/cjls/updateUpButton',
    method: 'put',
    data: data
  })
}

// 新增业务字典
export function addUpButton(data) {
  return request({
    url: '/myjob/cjls/addUpButton',
    method: 'post',
    data: data
  })
}

// 删除业务字典
export function delangl(xhs) {
  return request({
    url: '/myjob/cjls/delangl/' + xhs,
    method: 'delete'
  })
}


