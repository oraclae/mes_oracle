import request from '@/utils/request'

//根据待办或者待关闭等来查询表数据
export function updateSfdzfk(id) {
  return request({
    url: '/myquestion/updateSfdzfk/'+id,
    method: 'post'
  })
}

//根据待办或者待关闭等来查询表数据
export function selectWtxxData(data) {
  return request({
    url: '/myquestion/selectWtxxData',
    method: 'get',
    params:data
  })
}

//创建问题或者提交问题
export function createQuestion(data) {
  return request({
    url: '/myquestion/createQuestion',
    method: 'post',
    data:data
  })
}

//批量删除数据
export function deleteQuestions(data) {
  return request({
    url: '/myquestion/deleteQuestions',
    method: 'post',
    data:data
  })
}

//批量删除问题交互数据
export function deleteProblemInteraction(data) {
  return request({
    url: '/myquestion/deleteProblemInteraction',
    method: 'post',
    data:data
  })
}

//批量修改问题状态的数据
export function updateQuestionsStatus(data) {
  return request({
    url: '/myquestion/updateQuestionsStatus',
    method: 'post',
    data:data
  })
}

//批量修改我的问题的数据
export function updateQuestions(data) {
  return request({
    url: '/myquestion/updateQuestions',
    method: 'post',
    data:data
  })
}


//批量修改问题交互的数据
export function updateProblemInteraction(data) {
  return request({
    url: '/myquestion/updateProblemInteraction',
    method: 'post',
    data:data
  })
}

//根据待办或者待关闭等来查询表数据
export function getQuestionList(data) {
  return request({
    url: '/myquestion/getQuestionList',
    method: 'get',
    params:data
  })
}

//根据查询问题交互的表数据
export function getProblemInteractionList(data) {
  return request({
    url: '/myquestion/getProblemInteractionList',
    method: 'get',
    params:data
  })
}

//批量修改我的待办页面的责任人状态的数据
export function updateMyDoListStatus(data) {
  return request({
    url: '/myquestion/updateMyDoListStatus',
    method: 'post',
    data:data
  })
}

//查询领导批示的数据
export function getLeaderQuestion(data) {
  return request({
    url: '/myquestion/getLeaderQuestion',
    method: 'get',
    params:data
  })
}

//查询问题交互的数据
export function getQuestionInteraction(data) {
  return request({
    url: '/myquestion/getQuestionInteraction',
    method: 'get',
    params:data
  })
}

//获得问题来源的数据
export function getwtlyMethod(data) {
  return request({
    url: '/myquestion/getwtlyMethod',
    method: 'get',
    params:data
  })
}

//获得问题类别的数据
export function getwtlbMethod(data) {
  return request({
    url: '/myquestion/getwtlbMethod',
    method: 'get',
    params:data
  })
}

//获得问题细类的数据
export function getwtxlMethod(data) {
  return request({
    url: '/myquestion/getwtxlMethod',
    method: 'get',
    params:data
  })
}

//获得紧急程度的数据
export function getjjcdMethod(data) {
  return request({
    url: '/myquestion/getjjcdMethod',
    method: 'get',
    params:data
  })
}

//获得主责任人的数据
export function getzcrrMethod(data) {
  return request({
    url: '/myquestion/getzcrrMethod',
    method: 'get',
    params:data
  })
}

//获得阅知人的数据
export function getyzrMethod(data) {
  return request({
    url: '/myquestion/getyzrMethod',
    method: 'get',
    params:data
  })
}

//获得问题编号的数据
export function getwtbhMethod(data) {
  return request({
    url: '/myquestion/getwtbhMethod',
    method: 'get',
    params:data
  })
}






//回复预览页面的操作js
export function getzerData(data) {
  return request({
    url: '/myquestion/getzerData',
    method: 'get',
    params:data
  })
}

//回复预览页面的回复信息保存操作js
export function saveJhjlList(data) {
  return request({
    url: '/myquestion/saveJhjlList',
    method: 'post',
    data:data
  })
}

//我的待办待办数量
export function updateQuestionWddb(data) {
  return request({
    url: '/myquestion/updateQuestionWddb',
    method: 'post',
    data:data
  })
}

//我的问题待办数量
export function updateQuestionWdwt(data) {
  return request({
    url: '/myquestion/updateQuestionWdwt',
    method: 'post',
    data:data
  })
}

//将我的我的问题待办数据变成0
export function updateQuestionWTDBToZero(data) {
  return request({
    url: '/myquestion/updateQuestionWTDBToZero',
    method: 'post',
    data:data
  })
}

//我的待办待办数据变成0
export function updateQuestionWDWTToZero(data) {
  return request({
    url: '/myquestion/updateQuestionWDWTToZero',
    method: 'post',
    data:data
  })
}


//关注问题的调用后台方法js
export function savegzwtList(data) {
  return request({
    url: '/myquestion/savegzwtList',
    method: 'post',
    data:data
  })
}

//获取关注页面的问题
export function getMyProblemList(data) {
  return request({
    url: '/myquestion/getMyProblemList',
    method: 'get',
    params:data
  })
}

//取消关注的方法
export function deletegzwtList(data) {
  return request({
    url: '/myquestion/deletegzwtList',
    method: 'post',
    data:data
  })
}

//获得所有的回复数据（交互记录的数据）
export function getJhjl(data) {
  return request({
    url: '/myquestion/getJhjl',
    method: 'get',
    params:data
  })
}


export function getJhjlByWtid(data) {
  return request({
    url: '/myquestion/getJhjlByWtid',
    method: 'get',
    params:data
  })
}

//得到领导批示的数据
export function getLDPS(data) {
  return request({
    url: '/myquestion/getLDPS',
    method: 'get',
    params:data
  })
}


//责任人闭环按钮的方法
export function closedLoop(data) {
  return request({
    url: '/myquestion/closedLoop',
    method: 'post',
    data:data
  })
}

//责任人激活按钮的方法
export function activate(data) {
  return request({
    url: '/myquestion/activate',
    method: 'post',
    data:data
  })
}

//修改问题的状态
export function updateWtztEnd(data) {
  return request({
    url: '/myquestion/updateWtztEnd',
    method: 'post',
    data:data
  })
}

//删除回复数据

export function deleteJhjlByXh(data) {
  return request({
    url: '/myquestion/deleteJhjlByXh',
    method: 'post',
    data:data
  })
}

//查询阅知人的数据
export function getQuestionYzrList(data) {
  return request({
    url: '/myquestion/getQuestionYzrList',
    method: 'get',
    params:data
  })
}

//查询当前问题id的交互数据
export function selectJhjlList(data) {
  return request({
    url: '/myquestion/selectJhjlList',
    method: 'get',
    params:data
  })
}


//选择答案并且关闭的js方法
export function answerSelectOff(data) {
  return request({
    url: '/myquestion/answerSelectOff',
    method: 'post',
    data:data
  })
}


export function updateQuestionStatus(data) {
  return request({
    url: '/myquestion/updateQuestionStatus',
    method: 'post',
    data:data
  })
}

//根据id查询问题数据
export function getYwcj(data) {
  return request({
    url: '/myquestion/getYwcj',
    method: 'get',
    params:data
  })
}

//根据id修改问题
export function updateQuestion(data) {
  return request({
    url: '/myquestion/updateQuestion',
    method: 'post',
    data:data
  })
}

//根据id查询交互数据
export function getJHJL(data) {
  return request({
    url: '/myquestion/getJHJL',
    method: 'get',
    params:data
  })
}
//根据id修改交互数据
export function updateJHJL(data) {
  return request({
    url: '/myquestion/updateJHJL',
    method: 'post',
    data:data
  })
}
