import request from '@/utils/request'

export function info(username) {
  return request({
    url: '/profile/info/' + username,
    method: 'get'
  })
}
