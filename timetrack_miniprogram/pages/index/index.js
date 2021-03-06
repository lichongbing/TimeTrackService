//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    //console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
    wx.request({
      url: 'http://localhost:8080/auth/wx/info',
      method: "POST",
      header:{'content-type': 'application/json'},
      data:{    
        rawData:e.detail.rawData,
        encryptedData:e.detail.encryptedData,
        signature:e.detail.signature,
        iv:e.detail.iv,
      },
      success: res => {
        console.log(res);
      }
    })
    wx.setStorageSync('rawData', e.detail.rawData);
    wx.setStorageSync('signature', e.detail.signature);
    wx.setStorageSync('encryptedData',e.detail.encryptedData);
    //console.log(wx.getStorageSync('signature'));  
  },

  getPhoneNumber: function (e) {
    var that = this;
    console.log(e.detail.errMsg == "getPhoneNumber:ok");
    console.log(e);
    if (e.detail.errMsg == "getPhoneNumber:ok") {
      wx.request({
        url: 'http://localhost:8080/auth/wx/phone',
        header:{'content-type': 'application/json'},
        data: {
          encryptedData: e.detail.encryptedData,
          iv: e.detail.iv,
          rawData: wx.getStorageSync('rawData'),
          signature: wx.getStorageSync('signature'),
          
        },
        
        method: "POST",
        success: function (res) {
          console.log(res);
        }
      })
    }
  }
})
