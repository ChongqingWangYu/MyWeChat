package com.wangyu.wx.common;

/**
 * @ClassName MessageType
 * @Description 接收到的信息类型
 * @Author ChongqingWangYu
 * @DateTime 2019/2/28 12:37
 * @GitHub https://github.com/ChongqingWangYu
 */
public enum MessageType {
    /*文本消息*/
    TEXT,
    /*图片消息*/
    IMAGE,
    /*语音消息*/
    VOICE,
    /*视频消息*/
    VIDEO,
    /*小视频消息*/
    SHORTVIDEO,
    /*地理位置消息*/
    LOCATION,
    /*链接消息*/
    LINK,
    /*事件消息*/
    EVENT
}
